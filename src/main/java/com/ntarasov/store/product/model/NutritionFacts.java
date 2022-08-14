package com.ntarasov.store.product.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class NutritionFacts {
    private String protein;
    private String fats;
    private String carbohydrates;
    private String kcal;
}
