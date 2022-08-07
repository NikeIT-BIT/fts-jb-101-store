package com.ntarasov.store.cart.repository;

import com.ntarasov.store.cart.model.CartDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface CartRepository extends MongoRepository<CartDoc, ObjectId> {

}
