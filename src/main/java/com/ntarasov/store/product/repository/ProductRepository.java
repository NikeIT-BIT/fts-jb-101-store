package com.ntarasov.store.product.repository;

import com.ntarasov.store.product.model.ProductDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ProductRepository extends MongoRepository<ProductDoc, ObjectId> {
        Optional<ProductDoc> findByName(String name);
//        Optional<ProductDoc> findByPhotoId(ObjectId photoId);

}
