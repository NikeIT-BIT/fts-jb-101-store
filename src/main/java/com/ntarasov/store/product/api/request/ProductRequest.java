package com.ntarasov.store.product.api.request;

import com.ntarasov.store.product.model.NutritionFacts;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "ProductRequest", description = "Model product request")

public class ProductRequest {
        private ObjectId id;
        private ObjectId categoryId;
        private ObjectId photoId;
        private ObjectId cityId;
        private String  name;
        private String  description;
        private NutritionFacts  nutritionFacts = new NutritionFacts();
}
