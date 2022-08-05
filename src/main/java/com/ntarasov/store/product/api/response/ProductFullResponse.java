package com.ntarasov.store.product.api.response;

import com.ntarasov.store.product.model.NutritionFacts;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ApiModel(value = "ProductFullResponse", description = "Model productResponse")

public class ProductFullResponse extends ProductResponse{
    private NutritionFacts nutritionFacts;
}
