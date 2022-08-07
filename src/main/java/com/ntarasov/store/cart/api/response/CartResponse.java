package com.ntarasov.store.cart.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel(value = "CartResponse", description = "Model cartResponse")

public class CartResponse {
        protected String id;
        protected String cityId;
        protected Float totalCost;
        protected Map<String, Integer> products;
}
