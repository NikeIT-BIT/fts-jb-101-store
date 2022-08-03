package com.ntarasov.store.category.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "CategoryRequest", description = "Model category request")

public class CategoryRequest {
        private ObjectId id;
        private String  name;
}
