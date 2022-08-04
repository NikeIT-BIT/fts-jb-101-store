package com.ntarasov.store.photo.repository;

import com.ntarasov.store.photo.model.PhotoDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PhotoRepository extends MongoRepository<PhotoDoc, ObjectId> {

}
