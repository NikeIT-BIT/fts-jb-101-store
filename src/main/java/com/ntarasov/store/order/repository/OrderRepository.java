package com.ntarasov.store.order.repository;

import com.ntarasov.store.order.model.OrderDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderRepository extends MongoRepository<OrderDoc, ObjectId> {

}
