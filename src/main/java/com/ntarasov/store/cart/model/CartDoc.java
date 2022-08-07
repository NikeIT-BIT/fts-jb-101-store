package com.ntarasov.store.cart.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CartDoc {
    @Id
    private ObjectId id;
    private ObjectId cityId;
    private Float totalCost;
    private Map<ObjectId, Integer> products = new HashMap<>();
}
