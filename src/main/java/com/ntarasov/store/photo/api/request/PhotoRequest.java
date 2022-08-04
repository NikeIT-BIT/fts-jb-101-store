package com.ntarasov.store.photo.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "PhotoRequest", description = "Model photo request")

public class PhotoRequest {
        private ObjectId id;
        private String  name;
        private String contentType;
}
