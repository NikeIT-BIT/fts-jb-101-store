package com.ntarasov.store.cart.service;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.cart.api.request.CartRequest;
import com.ntarasov.store.cart.api.request.CartSearchRequest;
import com.ntarasov.store.cart.api.request.CartUpdateRequest;
import com.ntarasov.store.cart.mapping.CartMapping;
import com.ntarasov.store.cart.exception.CartExistException;
import com.ntarasov.store.cart.exception.CartNotExistException;
import com.ntarasov.store.cart.model.CartDoc;
import com.ntarasov.store.cart.repository.CartRepository;
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

public class CartApiService {
//<---------------------------------FINAL------------------------------------------------->
    private final CartRepository cartRepository;
    private final MongoTemplate mongoTemplate;

//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    public Optional<CartDoc> findById(ObjectId id){
            return cartRepository.findById(id);
    }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    public CartDoc create(CartRequest request) throws CartExistException {

        CartDoc cartDoc = CartMapping.getInstance().getRequest().convert(request);
        cartRepository.save(cartDoc);
        return cartDoc;
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
    public SearchResponse<CartDoc> search(CartSearchRequest request){

        List<Criteria> list = new ArrayList<>();
        if(request.getCityId() != null)
            list.add(Criteria.where("cityId").is(request.getCityId()));
//        if(StringUtils.hasText(request.getQuery()))
//            list.add(Criteria.where("price").regex(request.getQuery(), "i"));

        Criteria criteria = list.isEmpty()?
                new Criteria()
                :
                new Criteria().andOperator(list.toArray(Criteria[]::new));

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, CartDoc.class);
        query.limit(request.getSize());
        query.skip(request.getSkip());
        List<CartDoc> cartDocs = mongoTemplate.find(query, CartDoc.class);
        return  SearchResponse.of(cartDocs, count);
    }

//<---------------------------------ОБНОВЛЕНИЕ------------------------------------------------->
    public CartDoc update(CartUpdateRequest request) throws CartNotExistException {
        Optional<CartDoc> cartDocOptional = cartRepository.findById(request.getId());
        if (cartDocOptional.isEmpty()) throw new CartNotExistException();
        CartDoc oldDoc = cartDocOptional.get();
        CartDoc cartDoc = CartMapping.getInstance().getRequestUpdate().convert(request);
        cartDoc.setId(request.getId());
        cartDoc.setProducts(request.getProducts());
        cartDoc.setCityId(oldDoc.getCityId());
        cartDoc.setTotalCost(request.getTotalCost());
        cartRepository.save(cartDoc);
        return cartDoc;
    }

//<---------------------------------УДАЛЕНИЕ------------------------------------------------->
    public void delete(ObjectId id){
        cartRepository.deleteById(id);
    }
}
