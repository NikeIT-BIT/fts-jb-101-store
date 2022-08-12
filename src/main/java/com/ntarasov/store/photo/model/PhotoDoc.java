package com.ntarasov.store.photo.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PhotoDoc {
    @Id
    private ObjectId id;
    private String name;
    private String contentType;
    private ObjectId productId;

}
