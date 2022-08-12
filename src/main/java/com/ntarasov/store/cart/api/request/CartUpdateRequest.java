package com.ntarasov.store.cart.api.request;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter

public class CartUpdateRequest {
    private ObjectId id;
    private Float totalCost;
    private Map<ObjectId, Integer> products = new HashMap<>();
}
