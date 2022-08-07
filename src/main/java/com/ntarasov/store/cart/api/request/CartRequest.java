package com.ntarasov.store.cart.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ApiModel(value = "CartRequest", description = "Model cart request")

public class CartRequest {
        private ObjectId id;
        private ObjectId cityId;
        private Float totalCost;
        private Map<ObjectId, Integer> products = new HashMap<>();
}
