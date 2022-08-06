package com.ntarasov.store.price.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel(value = "PriceResponse", description = "Model priceResponse")

public class PriceResponse {
        protected String id;
        protected String cityId;
        protected String productId;
        protected Float price;
}
