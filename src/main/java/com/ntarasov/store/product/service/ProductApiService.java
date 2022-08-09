package com.ntarasov.store.product.service;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.photo.api.request.PhotoSearchRequest;
import com.ntarasov.store.photo.exception.PhotoNotExistException;
import com.ntarasov.store.photo.model.PhotoDoc;
import com.ntarasov.store.photo.service.PhotoApiService;
import com.ntarasov.store.price.api.request.PriceSearchRequest;
import com.ntarasov.store.price.model.PriceDoc;
import com.ntarasov.store.price.service.PriceApiService;
import com.ntarasov.store.product.api.request.ProductRequest;
import com.ntarasov.store.product.api.request.ProductSearchRequest;
import com.ntarasov.store.product.exception.ProductNotExistException;
import com.ntarasov.store.product.model.ProductDoc;
import com.ntarasov.store.product.repository.ProductRepository;
import com.ntarasov.store.photo.exception.PhotoExistException;
import com.ntarasov.store.product.mapping.ProductMapping;
import com.ntarasov.store.product.exception.ProductExistException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ProductApiService {
    //<---------------------------------FINAL------------------------------------------------->
    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;
    private final PhotoApiService photoApiService;
    private final PriceApiService priceApiService;

    //<---------------------------------ПОИСК ПО ID------------------------------------------------->
    public Optional<ProductDoc> findById(ObjectId id) {
        return productRepository.findById(id);
    }

    //<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    public ProductDoc create(ProductRequest request) throws ProductExistException, PhotoExistException {
        if (productRepository.findByName(request.getName()).isPresent()) throw new ProductExistException();

        ProductDoc productDoc = ProductMapping.getInstance().getRequest().convert(request);
        productRepository.save(productDoc);
        return productDoc;
    }

    //<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
    public SearchResponse<ProductDoc> search(ProductSearchRequest request) {

        Criteria criteria = new Criteria(); // Добавь сюда критерию по категории

        if (request.getQuery() != null && !Objects.equals(request.getQuery(), "")) {
            criteria = criteria.orOperator(
                    Criteria.where("name").regex(request.getQuery(), "i"),
                    Criteria.where("categoryId").regex(request.getQuery(), "i")
            );
        }

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, ProductDoc.class);
        query.limit(request.getSize());
        query.skip(request.getSkip());
        List<ProductDoc> productDocs = mongoTemplate.find(query, ProductDoc.class);
        return SearchResponse.of(productDocs, count);
    }

    //<---------------------------------ОБНОВЛЕНИЕ------------------------------------------------->
    public ProductDoc update(ProductRequest request) throws ProductNotExistException {
        Optional<ProductDoc> productDocOptional = productRepository.findById(request.getId());

        ProductDoc oldDoc = productDocOptional.get();

        if (productDocOptional.isEmpty()) throw new ProductNotExistException();
        ProductDoc productDoc = ProductMapping.getInstance().getRequest().convert(request);
        productDoc.setId(oldDoc.getId());
        productRepository.save(productDoc);
        return productDoc;
    }

    //<---------------------------------УДАЛЕНИЕ------------------------------------------------->
    public void delete(ObjectId id) throws PhotoNotExistException {
        List <PhotoDoc> photoDocs = photoApiService.search(PhotoSearchRequest.builder()
                .productId(id)
                .size(1)
                .build()).getList();
        List <PriceDoc> priceDocs = priceApiService.search(PriceSearchRequest.builder()
                .productId(id)
                .size(100)
                .build()).getList();

        for(PhotoDoc photoDoc : photoDocs) photoApiService.delete(photoDoc.getId());
        for(PriceDoc priceDoc : priceDocs) priceApiService.delete(priceDoc.getId());
        productRepository.deleteById(id);
    }
}
