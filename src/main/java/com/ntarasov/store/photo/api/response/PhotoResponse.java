package com.ntarasov.store.photo.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel(value = "PhotoResponse", description = "Model photoResponse")

public class PhotoResponse {
        protected String id;
        protected String  name;
        protected String contentType;
        protected String productId;
}
