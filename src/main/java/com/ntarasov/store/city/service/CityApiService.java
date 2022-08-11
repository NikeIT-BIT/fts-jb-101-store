package com.ntarasov.store.city.service;

import com.ntarasov.store.admin.exception.AdminExistException;
import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.city.api.request.CityRequest;
import com.ntarasov.store.city.mapping.CityMapping;
import com.ntarasov.store.city.exception.CityExistException;
import com.ntarasov.store.city.exception.CityNotExistException;
import com.ntarasov.store.city.model.CityDoc;
import com.ntarasov.store.city.repository.CityRepository;
import com.ntarasov.store.photo.api.request.PhotoSearchRequest;
import com.ntarasov.store.photo.model.PhotoDoc;
import com.ntarasov.store.price.api.request.PriceSearchRequest;
import com.ntarasov.store.price.model.PriceDoc;
import com.ntarasov.store.price.service.PriceApiService;
import com.ntarasov.store.street.api.request.StreetSearchRequest;
import com.ntarasov.store.street.model.StreetDoc;
import com.ntarasov.store.street.service.StreetApiService;
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

public class CityApiService {
//<---------------------------------FINAL------------------------------------------------->
    private final CityRepository cityRepository;
    private final MongoTemplate mongoTemplate;
    private final PriceApiService priceApiService;
    private final StreetApiService streetApiService;

//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    public Optional<CityDoc> findById(ObjectId id){
            return cityRepository.findById(id);
    }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    public CityDoc create(CityRequest request) throws CityExistException {
        if (cityRepository.findByName(request.getName()).isPresent()) {
            throw new CityExistException();
        }

        CityDoc cityDoc = CityMapping.getInstance().getRequest().convert(request);
        cityRepository.save(cityDoc);
        return cityDoc;
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
    public SearchResponse<CityDoc> search(SearchRequest request){

        Criteria criteria = new Criteria();

        if(request.getQuery()!= null && Objects.equals(request.getQuery(), "") == false){
            criteria = criteria.orOperator(
                    Criteria.where("name").regex(request.getQuery(),"i"),
                    Criteria.where("minTimeDelivery").regex(request.getQuery(),"i"),
                    Criteria.where("priceDelivery").regex(request.getQuery(),"i")
            );
        }

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, CityDoc.class);
        query.limit(request.getSize());
        query.skip(request.getSkip());
        List<CityDoc> cityDocs = mongoTemplate.find(query, CityDoc.class);
        return  SearchResponse.of(cityDocs, count);
    }

//<---------------------------------ОБНОВЛЕНИЕ------------------------------------------------->
    public CityDoc update(CityRequest request) throws CityNotExistException {
        Optional<CityDoc> cityDocOptional = cityRepository.findById(request.getId());
        if (cityDocOptional.isEmpty()) throw new CityNotExistException();

        CityDoc cityDoc = CityMapping.getInstance().getRequest().convert(request);
        cityDoc.setId(request.getId());
        cityDoc.setName(request.getName());
        cityDoc.setMinTimeDelivery(request.getMinTimeDelivery());
        cityDoc.setPriceDelivery(request.getPriceDelivery());
        cityRepository.save(cityDoc);
        return cityDoc;
    }

//<---------------------------------УДАЛЕНИЕ------------------------------------------------->
    public void delete(ObjectId id){
        List <PriceDoc> priceDocs = priceApiService.search(PriceSearchRequest.builder()
                .cityId(id)
                .size(100)
                .build()).getList();
        List <StreetDoc> streetDocs = streetApiService.search(StreetSearchRequest.builder()
                .cityId(id)
                .size(10000)
                .build()).getList();
        for(PriceDoc priceDoc : priceDocs) priceApiService.delete(priceDoc.getId());
        for(StreetDoc streetDoc : streetDocs) streetApiService.delete(streetDoc.getId());
        cityRepository.deleteById(id);
    }
}
