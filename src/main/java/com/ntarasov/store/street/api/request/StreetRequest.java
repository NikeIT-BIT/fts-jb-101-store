package com.ntarasov.store.street.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "StreetRequest", description = "Model street request")

public class StreetRequest {
        private ObjectId id;
        private ObjectId cityId;
        private String  name;
}
