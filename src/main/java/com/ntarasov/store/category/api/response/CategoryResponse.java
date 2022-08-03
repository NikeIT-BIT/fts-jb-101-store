package com.ntarasov.store.category.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel(value = "CategoryResponse", description = "Model categoryResponse")

public class CategoryResponse {
        protected String id;
        protected String  name;
}
