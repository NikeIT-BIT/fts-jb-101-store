package com.ntarasov.store.price.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "PriceRequest", description = "Model price request")

public class PriceRequest {
        private ObjectId id;
        private ObjectId cityId;
        private ObjectId productId;
        private Float price;
}
