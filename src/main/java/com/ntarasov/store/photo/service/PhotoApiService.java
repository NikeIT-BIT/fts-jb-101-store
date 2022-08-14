package com.ntarasov.store.photo.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.photo.api.request.PhotoRequest;
import com.ntarasov.store.photo.api.request.PhotoSearchRequest;
import com.ntarasov.store.photo.api.request.PhotoUpdateRequest;
import com.ntarasov.store.photo.exception.PhotoNotExistException;
import com.ntarasov.store.photo.mapping.PhotoMapping;
import com.ntarasov.store.photo.model.PhotoDoc;
import com.ntarasov.store.photo.repository.PhotoRepository;
import com.ntarasov.store.product.exception.ProductNotExistException;
import com.ntarasov.store.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class PhotoApiService {
    //<---------------------------------FINAL------------------------------------------------->
    private final PhotoRepository photoRepository;
    private final MongoTemplate mongoTemplate;
    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;
    private final ProductRepository productRepository;

    //<---------------------------------ПОИСК ПО ID------------------------------------------------->
    public Optional<PhotoDoc> findById(ObjectId id) {
        return photoRepository.findById(id);
    }

    //<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    public PhotoDoc create(MultipartFile file, ObjectId productId) throws IOException, ProductNotExistException {
        if(productRepository.findById(productId).isEmpty()) throw new ProductNotExistException();

        DBObject metaData = new BasicDBObject();
        metaData.put("type", file.getContentType());
        metaData.put("title", file.getOriginalFilename());

        ObjectId id = gridFsTemplate.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType(),
                metaData
        );

        PhotoDoc photoDoc = PhotoDoc.builder()
                .id(id)
                .productId(productId)
                .name(file.getOriginalFilename())
                .contentType(file.getContentType())
                .build();

        photoRepository.save(photoDoc);
        return photoDoc;
    }
    public InputStream downloadById(ObjectId id) throws ChangeSetPersister.NotFoundException, IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        if (file == null) throw new ChangeSetPersister.NotFoundException();
        return gridFsOperations.getResource(file).getInputStream();
    }

    //<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
    public SearchResponse<PhotoDoc> search(PhotoSearchRequest request) {

        List<Criteria> list = new ArrayList<>();
        if(request.getProductId() != null)
            list.add(Criteria.where("productId").is(request.getProductId()));
        if(StringUtils.hasText(request.getQuery()))
            list.add(Criteria.where("name").regex(request.getQuery(), "i"));

        Criteria criteria = list.isEmpty()?
                new Criteria()
                :
                new Criteria().andOperator(list.toArray(Criteria[]::new));

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, PhotoDoc.class);
        query.limit(request.getSize());
        query.skip(request.getSkip());
        List<PhotoDoc> photoDocs = mongoTemplate.find(query, PhotoDoc.class);
        return SearchResponse.of(photoDocs, count);
    }

    //<---------------------------------ОБНОВЛЕНИЕ------------------------------------------------->
    public PhotoDoc update(PhotoUpdateRequest request) throws PhotoNotExistException {
        Optional<PhotoDoc> photoDocOptional = photoRepository.findById(request.getId());
        if (photoDocOptional.isEmpty()) throw new PhotoNotExistException();

        PhotoDoc oldDoc = photoDocOptional.get();

        PhotoDoc photoDoc = PhotoMapping.getInstance().getUpdateRequest().convert(request);
        photoDoc.setId(request.getId());
        photoDoc.setName(request.getName());
        photoDoc.setProductId(oldDoc.getProductId());
        photoDoc.setContentType(oldDoc.getContentType());
        photoRepository.save(photoDoc);
        return photoDoc;
    }

    //<---------------------------------УДАЛЕНИЕ------------------------------------------------->
    public void delete(ObjectId id) {
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
        photoRepository.deleteById(id);
    }
}
