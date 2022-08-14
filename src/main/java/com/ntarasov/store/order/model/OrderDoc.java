package com.ntarasov.store.order.model;

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

public class OrderDoc {
    @Id
    private ObjectId id;
    private ObjectId guesId;
    private String numberOrder;
    private Status status;
}
