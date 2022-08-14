package com.ntarasov.store.gues.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Address{
    private String home;
    private String flat;
    private String entrance;
    private String floor;

}
