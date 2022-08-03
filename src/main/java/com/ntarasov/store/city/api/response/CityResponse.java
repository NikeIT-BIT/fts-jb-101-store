package com.ntarasov.store.city.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel(value = "CityResponse", description = "Model cityResponse")

public class CityResponse {
        protected String id;
        protected String  name;
        protected String  minTimeDelivery;
        protected String  priceDelivery;
}
