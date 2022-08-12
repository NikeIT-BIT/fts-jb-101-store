package com.ntarasov.store.gues.repository;

import com.ntarasov.store.gues.model.GuesDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface GuesRepository extends MongoRepository<GuesDoc, ObjectId> {
}
