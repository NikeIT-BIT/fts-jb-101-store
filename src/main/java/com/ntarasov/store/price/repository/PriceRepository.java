package com.ntarasov.store.price.repository;

import com.ntarasov.store.price.model.PriceDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface PriceRepository extends MongoRepository<PriceDoc, ObjectId> {
        Optional<PriceDoc> findByCityId(ObjectId cityId);
        Optional<PriceDoc> findByProductId(ObjectId productId);
}
