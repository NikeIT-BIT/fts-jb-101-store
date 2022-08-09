package com.ntarasov.store.product.api.response;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel(value = "ProductResponse", description = "Model productResponse")

public class ProductResponse {
    protected String id;
    protected String categoryId;
    protected String name;
    protected String description;

}
