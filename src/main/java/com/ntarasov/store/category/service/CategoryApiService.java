package com.ntarasov.store.category.service;

import com.ntarasov.store.photo.exception.PhotoNotExistException;
import com.ntarasov.store.product.api.request.ProductSearchRequest;
import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.category.api.request.CategoryRequest;
import com.ntarasov.store.category.mapping.CategoryMapping;
import com.ntarasov.store.category.exception.CategoryExistException;
import com.ntarasov.store.category.exception.CategoryNotExistException;
import com.ntarasov.store.category.model.CategoryDoc;
import com.ntarasov.store.category.repository.CategoryRepository;
import com.ntarasov.store.product.model.ProductDoc;
import com.ntarasov.store.product.service.ProductApiService;
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

public class CategoryApiService {
    //<---------------------------------FINAL------------------------------------------------->
    private final CategoryRepository categoryRepository;
    private final MongoTemplate mongoTemplate;
    private final ProductApiService productApiService;

    //<---------------------------------ПОИСК ПО ID------------------------------------------------->
    public Optional<CategoryDoc> findById(ObjectId id) {
        return categoryRepository.findById(id);
    }

    //<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    public CategoryDoc create(CategoryRequest request) throws CategoryExistException {
        if (categoryRepository.findByName(request.getName()).isPresent()) {
            throw new CategoryExistException();
        }

        CategoryDoc categoryDoc = CategoryMapping.getInstance().getRequest().convert(request);
        categoryRepository.save(categoryDoc);
        return categoryDoc;
    }

    //<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
    public SearchResponse<CategoryDoc> search(SearchRequest request) {

        Criteria criteria = new Criteria();

        if (request.getQuery() != null && Objects.equals(request.getQuery(), "") == false) {
            criteria = criteria.orOperator(
                    Criteria.where("name").regex(request.getQuery(), "i")
            );
        }

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, CategoryDoc.class);
        query.limit(request.getSize());
        query.skip(request.getSkip());
        List<CategoryDoc> categoryDocs = mongoTemplate.find(query, CategoryDoc.class);
        return SearchResponse.of(categoryDocs, count);
    }

    //<---------------------------------ОБНОВЛЕНИЕ------------------------------------------------->
    public CategoryDoc update(CategoryRequest request) throws CategoryNotExistException {
        Optional<CategoryDoc> categoryDocOptional = categoryRepository.findById(request.getId());
        if (categoryDocOptional.isEmpty()) throw new CategoryNotExistException();
        CategoryDoc categoryDoc = CategoryMapping.getInstance().getRequest().convert(request);
        categoryDoc.setId(request.getId());
        categoryDoc.setName(request.getName());
        categoryRepository.save(categoryDoc);
        return categoryDoc;
    }

    //<---------------------------------УДАЛЕНИЕ------------------------------------------------->
    public void delete(ObjectId id) throws PhotoNotExistException {
        List<ProductDoc> productDocs = productApiService.search(ProductSearchRequest.builder()
                .categoryId(id)
                .size(1000)
                .build()).getList();

        for(ProductDoc productDoc : productDocs) productApiService.delete(productDoc.getId());
        categoryRepository.deleteById(id);
    }
}
