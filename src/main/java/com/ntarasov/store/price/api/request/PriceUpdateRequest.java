package com.ntarasov.store.price.api.request;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter

public class PriceUpdateRequest {
    private ObjectId id;
    private Float price;
}
