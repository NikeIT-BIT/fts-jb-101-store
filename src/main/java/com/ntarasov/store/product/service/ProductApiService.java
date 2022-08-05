package com.ntarasov.store.product.service;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.product.api.request.ProductRequest;
import com.ntarasov.store.product.mapping.ProductMapping;
import com.ntarasov.store.product.exception.ProductExistException;
import com.ntarasov.store.product.exception.ProductNotExistException;
import com.ntarasov.store.product.model.ProductDoc;
import com.ntarasov.store.product.repository.ProductRepository;
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

//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    public Optional<ProductDoc> findById(ObjectId id){
            return productRepository.findById(id);
    }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    public ProductDoc create(ProductRequest request) throws ProductExistException {
        if (productRepository.findByName(request.getName()).isPresent()) {
            throw new ProductExistException();
        }

        ProductDoc productDoc = ProductMapping.getInstance().getRequest().convert(request);
        productRepository.save(productDoc);
        return productDoc;
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
    public SearchResponse<ProductDoc> search(SearchRequest request){

        Criteria criteria = new Criteria();

        if(request.getQuery()!= null && !Objects.equals(request.getQuery(), "")){
            criteria = criteria.orOperator(
//                    TODO: Add criteria
//                    Criteria.where("firstName").regex(request.getQuery(),"i"),
//                    Criteria.where("lastName").regex(request.getQuery(),"i"),
//                    Criteria.where("email").regex(request.getQuery(),"i")
            );
        }

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, ProductDoc.class);
        query.limit(request.getSize());
        query.skip(request.getSkip());
        List<ProductDoc> productDocs = mongoTemplate.find(query, ProductDoc.class);
        return  SearchResponse.of(productDocs, count);
    }

//<---------------------------------ОБНОВЛЕНИЕ------------------------------------------------->
    public ProductDoc update(ProductRequest request) throws ProductNotExistException {
        Optional<ProductDoc> productDocOptional = productRepository.findById(request.getId());
        if (productDocOptional.isEmpty()) throw new ProductNotExistException();
        ProductDoc productDoc = ProductMapping.getInstance().getRequest().convert(request);
        productDoc.setId(request.getId());
        productRepository.save(productDoc);
        return productDoc;
    }

//<---------------------------------УДАЛЕНИЕ------------------------------------------------->
    public void delete(ObjectId id){
        productRepository.deleteById(id);
    }
}
