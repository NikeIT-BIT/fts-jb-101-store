package com.ntarasov.store.admin.service;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.admin.api.request.AdminRequest;
import com.ntarasov.store.admin.mapping.AdminMapping;
import com.ntarasov.store.admin.exception.AdminExistException;
import com.ntarasov.store.admin.exception.AdminNotExistException;
import com.ntarasov.store.admin.model.AdminDoc;
import com.ntarasov.store.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AdminApiService {
//<---------------------------------FINAL------------------------------------------------->
    private final AdminRepository adminRepository;
    private final MongoTemplate mongoTemplate;

//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    public Optional<AdminDoc> findById(ObjectId id){
            return adminRepository.findById(id);
    }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    public AdminDoc create(AdminRequest request) throws AdminExistException {
        AdminDoc adminDoc = AdminMapping.getInstance().getRequest().convert(request);
        adminRepository.save(adminDoc);
        return adminDoc;
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
    public SearchResponse<AdminDoc> search(SearchRequest request){

        Criteria criteria = new Criteria();

        if(request.getQuery()!= null && !Objects.equals(request.getQuery(), "")){
            criteria = criteria.orOperator(
//                    TODO: Add criteria
//                    Criteria.where("firstName").regex(request.getQuery(),"i"),
//                    Criteria.where("lastName").regex(request.getQuery(),"i"),
//                    Criteria.where("email").regex(request.getQuery(),"i")
            );
        }

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, AdminDoc.class);
        query.limit(request.getSize());
        query.skip(request.getSkip());
        List<AdminDoc> adminDocs = mongoTemplate.find(query, AdminDoc.class);
        return  SearchResponse.of(adminDocs, count);
    }

//<---------------------------------ОБНОВЛЕНИЕ------------------------------------------------->
    public AdminDoc update(AdminRequest request) throws AdminNotExistException {
        Optional<AdminDoc> adminDocOptional = adminRepository.findById(request.getId());
        if (adminDocOptional.isEmpty()) throw new AdminNotExistException();
        AdminDoc adminDoc = AdminMapping.getInstance().getRequest().convert(request);
        adminDoc.setId(request.getId());
        adminRepository.save(adminDoc);
        return adminDoc;
    }

//<---------------------------------УДАЛЕНИЕ------------------------------------------------->
    public void delete(ObjectId id){
        adminRepository.deleteById(id);
    }
}
