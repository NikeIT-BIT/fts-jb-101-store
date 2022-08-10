package com.ntarasov.store.price.service;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.city.exception.CityNotExistException;
import com.ntarasov.store.city.repository.CityRepository;
import com.ntarasov.store.price.api.request.PriceRequest;
import com.ntarasov.store.price.api.request.PriceSearchRequest;
import com.ntarasov.store.price.mapping.PriceMapping;
import com.ntarasov.store.price.exception.PriceExistException;
import com.ntarasov.store.price.exception.PriceNotExistException;
import com.ntarasov.store.price.model.PriceDoc;
import com.ntarasov.store.price.repository.PriceRepository;
import com.ntarasov.store.product.exception.ProductNotExistException;
import com.ntarasov.store.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class PriceApiService {
//<---------------------------------FINAL------------------------------------------------->
    private final PriceRepository priceRepository;
    private final MongoTemplate mongoTemplate;
    private final CityRepository cityRepository;
    private final ProductRepository productRepository;

//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    public Optional<PriceDoc> findById(ObjectId id){
            return priceRepository.findById(id);
    }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    public PriceDoc create(PriceRequest request) throws PriceExistException, CityNotExistException, ProductNotExistException {
        if (priceRepository.findByCityId(request.getCityId()).isPresent() && priceRepository.findByProductId(request.getProductId()).isPresent()) {
            throw new PriceExistException();
        }
        if (cityRepository.findById(request.getCityId()).isEmpty()) throw new CityNotExistException();
        if (productRepository.findById(request.getProductId()).isEmpty()) throw new ProductNotExistException();


        PriceDoc priceDoc = PriceMapping.getInstance().getRequest().convert(request);
        priceRepository.save(priceDoc);
        return priceDoc;
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
    public SearchResponse<PriceDoc> search(PriceSearchRequest request){

        List<Criteria> list = new ArrayList<>();
        if(request.getProductId() != null)
            list.add(Criteria.where("productId").is(request.getProductId()));
        if(request.getCityId() != null)
            list.add(Criteria.where("cityId").is(request.getCityId()));
        if(StringUtils.hasText(request.getQuery()))
            list.add(Criteria.where("price").regex(request.getQuery(), "i"));

        Criteria criteria = list.isEmpty()?
                new Criteria()
                :
                new Criteria().andOperator(list.toArray(Criteria[]::new));

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, PriceDoc.class);
        query.limit(request.getSize());
        query.skip(request.getSkip());
        List<PriceDoc> priceDocs = mongoTemplate.find(query, PriceDoc.class);
        return  SearchResponse.of(priceDocs, count);
    }

//<---------------------------------ОБНОВЛЕНИЕ------------------------------------------------->
    public PriceDoc update(PriceRequest request) throws PriceNotExistException {
        Optional<PriceDoc> priceDocOptional = priceRepository.findById(request.getId());
        if (priceDocOptional.isEmpty()) throw new PriceNotExistException();
        PriceDoc priceDoc = PriceMapping.getInstance().getRequest().convert(request);
        priceDoc.setId(request.getId());
        priceRepository.save(priceDoc);
        return priceDoc;
    }

//<---------------------------------УДАЛЕНИЕ------------------------------------------------->
    public void delete(ObjectId id){
        priceRepository.deleteById(id);
    }
}
