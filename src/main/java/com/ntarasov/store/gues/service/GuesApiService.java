package com.ntarasov.store.gues.service;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.cart.repository.CartRepository;
import com.ntarasov.store.city.repository.CityRepository;
import com.ntarasov.store.gues.api.request.GuesRequest;
import com.ntarasov.store.gues.mapping.GuesMapping;
import com.ntarasov.store.gues.exception.GuesExistException;
import com.ntarasov.store.gues.exception.GuesNotExistException;
import com.ntarasov.store.gues.model.GuesDoc;
import com.ntarasov.store.gues.repository.GuesRepository;
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

public class GuesApiService {
    //<---------------------------------FINAL------------------------------------------------->
    private final GuesRepository guesRepository;
    private final MongoTemplate mongoTemplate;
    private final CartRepository cartRepository;

    //<---------------------------------ПОИСК ПО ID------------------------------------------------->
    public Optional<GuesDoc> findById(ObjectId id) {
        return guesRepository.findById(id);
    }

    //<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    public GuesDoc create(GuesRequest request) throws GuesExistException {

        GuesDoc guesDoc = GuesMapping.getInstance().getRequest().convert(request);
        guesRepository.save(guesDoc);

        return guesDoc;
    }

    //<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
    public SearchResponse<GuesDoc> search(SearchRequest request) {

        Criteria criteria = new Criteria();

        if (request.getQuery() != null && !Objects.equals(request.getQuery(), "")) {
            criteria = criteria.orOperator(
//                    TODO: Add criteria
//                    Criteria.where("firstName").regex(request.getQuery(),"i"),
//                    Criteria.where("lastName").regex(request.getQuery(),"i"),
//                    Criteria.where("email").regex(request.getQuery(),"i")
            );
        }

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, GuesDoc.class);
        query.limit(request.getSize());
        query.skip(request.getSkip());
        List<GuesDoc> guesDocs = mongoTemplate.find(query, GuesDoc.class);
        return SearchResponse.of(guesDocs, count);
    }

    //<---------------------------------ОБНОВЛЕНИЕ------------------------------------------------->
    public GuesDoc update(GuesRequest request) throws GuesNotExistException {
        Optional<GuesDoc> guesDocOptional = guesRepository.findById(request.getId());
        if (guesDocOptional.isEmpty()) throw new GuesNotExistException();
        GuesDoc guesDoc = GuesMapping.getInstance().getRequest().convert(request);
        guesDoc.setId(request.getId());
        guesRepository.save(guesDoc);
        return guesDoc;
    }

    //<---------------------------------УДАЛЕНИЕ------------------------------------------------->
    public void delete(ObjectId id) {

        guesRepository.deleteById(id);
    }
}
