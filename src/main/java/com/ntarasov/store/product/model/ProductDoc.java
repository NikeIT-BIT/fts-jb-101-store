package com.ntarasov.store.product.model;

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

public class ProductDoc {
    @Id
        private ObjectId id;
        private ObjectId categoryId;
        private ObjectId photoId;
        private ObjectId cityId;
        private String  name;
        private String  description;
        private NutritionFacts  nutritionFacts = new NutritionFacts();
}
