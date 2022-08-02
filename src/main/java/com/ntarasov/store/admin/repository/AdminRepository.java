package com.ntarasov.store.admin.repository;

import com.ntarasov.store.admin.model.AdminDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AdminRepository extends MongoRepository<AdminDoc, ObjectId> {

}
