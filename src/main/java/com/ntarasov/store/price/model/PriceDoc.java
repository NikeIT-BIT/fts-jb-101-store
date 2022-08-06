package com.ntarasov.store.price.model;

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

public class PriceDoc {
    @Id
        private ObjectId id;
        private ObjectId cityId;
        private ObjectId productId;
        private Float price;
}
