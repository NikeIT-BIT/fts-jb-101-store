package com.ntarasov.store.order.service;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.cart.api.request.CartSearchRequest;
import com.ntarasov.store.order.api.request.OrderRequest;
import com.ntarasov.store.order.api.request.OrderSearchRequest;
import com.ntarasov.store.order.api.request.OrderUpdateRequest;
import com.ntarasov.store.order.mapping.OrderMapping;
import com.ntarasov.store.order.exception.OrderExistException;
import com.ntarasov.store.order.exception.OrderNotExistException;
import com.ntarasov.store.order.model.OrderDoc;
import com.ntarasov.store.order.repository.OrderRepository;
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

public class OrderApiService {
//<---------------------------------FINAL------------------------------------------------->
    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    public Optional<OrderDoc> findById(ObjectId id){
            return orderRepository.findById(id);
    }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    public OrderDoc create(OrderRequest request) throws OrderExistException {

        OrderDoc orderDoc = OrderMapping.getInstance().getRequest().convert(request);
        orderRepository.save(orderDoc);
        return orderDoc;
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
    public SearchResponse<OrderDoc> search(OrderSearchRequest request){

        List<Criteria> list = new ArrayList<>();
        if(request.getGuesId() != null)
            list.add(Criteria.where("cityId").is(request.getGuesId()));
        if(StringUtils.hasText(request.getQuery()))
            list.add(Criteria.where("status").regex(request.getQuery(), "i"));

        Criteria criteria = list.isEmpty()?
                new Criteria()
                :
                new Criteria().andOperator(list.toArray(Criteria[]::new));

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, OrderDoc.class);
        query.limit(request.getSize());
        query.skip(request.getSkip());
        List<OrderDoc> orderDocs = mongoTemplate.find(query, OrderDoc.class);
        return  SearchResponse.of(orderDocs, count);
    }

//<---------------------------------ОБНОВЛЕНИЕ------------------------------------------------->
    public OrderDoc update(OrderUpdateRequest request) throws OrderNotExistException {
        Optional<OrderDoc> orderDocOptional = orderRepository.findById(request.getId());
        if (orderDocOptional.isEmpty()) throw new OrderNotExistException();
        OrderDoc oldDoc = orderDocOptional.get();
        OrderDoc orderDoc = OrderMapping.getInstance().getUpdateRequest().convert(request);
        orderDoc.setId(request.getId());
        orderDoc.setNumberOrder(oldDoc.getNumberOrder());
        orderDoc.setGuesId(oldDoc.getGuesId());
        orderDoc.setStatus(request.getStatus());
        orderRepository.save(orderDoc);
        return orderDoc;
    }

////<---------------------------------УДАЛЕНИЕ------------------------------------------------->
//    public void delete(ObjectId id){
//        orderRepository.deleteById(id);
//    }
}
