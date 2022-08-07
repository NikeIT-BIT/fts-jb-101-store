package com.ntarasov.store.street.service;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.city.exception.CityNotExistException;
import com.ntarasov.store.city.repository.CityRepository;
import com.ntarasov.store.street.api.request.StreetRequest;
import com.ntarasov.store.street.mapping.StreetMapping;
import com.ntarasov.store.street.exception.StreetExistException;
import com.ntarasov.store.street.exception.StreetNotExistException;
import com.ntarasov.store.street.model.StreetDoc;
import com.ntarasov.store.street.repository.StreetRepository;
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

public class StreetApiService {
    //<---------------------------------FINAL------------------------------------------------->
    private final StreetRepository streetRepository;
    private final MongoTemplate mongoTemplate;
    private final CityRepository cityRepository;

    //<---------------------------------ПОИСК ПО ID------------------------------------------------->
    public Optional<StreetDoc> findById(ObjectId id) {
        return streetRepository.findById(id);
    }

    //<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    public StreetDoc create(StreetRequest request) throws StreetExistException, CityNotExistException {
        if (streetRepository.findByName(request.getName()).isPresent()) {
            throw new StreetExistException();
        }
        if (cityRepository.findById(request.getCityId()).isEmpty()) throw new CityNotExistException();

        StreetDoc streetDoc = StreetMapping.getInstance().getRequest().convert(request);
        streetRepository.save(streetDoc);
        return streetDoc;
    }

    //<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
    public SearchResponse<StreetDoc> search(SearchRequest request) {

        Criteria criteria = new Criteria();

        if (request.getQuery() != null && !Objects.equals(request.getQuery(), "")) {
            criteria = criteria.orOperator(

                    Criteria.where("name").regex(request.getQuery(), "i")
            );
        }

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, StreetDoc.class);
        query.limit(request.getSize());
        query.skip(request.getSkip());
        List<StreetDoc> streetDocs = mongoTemplate.find(query, StreetDoc.class);
        return SearchResponse.of(streetDocs, count);
    }

    //<---------------------------------ОБНОВЛЕНИЕ------------------------------------------------->
    public StreetDoc update(StreetRequest request) throws StreetNotExistException {
        Optional<StreetDoc> streetDocOptional = streetRepository.findById(request.getId());
        if (streetDocOptional.isEmpty()) throw new StreetNotExistException();
        StreetDoc streetDoc = StreetMapping.getInstance().getRequest().convert(request);
        streetDoc.setId(request.getId());
        streetRepository.save(streetDoc);
        return streetDoc;
    }

    //<---------------------------------УДАЛЕНИЕ------------------------------------------------->
    public void delete(ObjectId id) {
        streetRepository.deleteById(id);
    }
}
