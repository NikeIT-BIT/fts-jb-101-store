package com.ntarasov.store.city.repository;

import com.ntarasov.store.admin.model.AdminDoc;
import com.ntarasov.store.city.model.CityDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface CityRepository extends MongoRepository<CityDoc, ObjectId> {
    Optional<CityDoc> findByName(String name);
}
