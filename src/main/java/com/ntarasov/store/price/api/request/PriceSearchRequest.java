package com.ntarasov.store.price.api.request;

import com.ntarasov.store.base.api.request.SearchRequest;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@SuperBuilder

public class PriceSearchRequest extends SearchRequest {
    private ObjectId productId;
    private ObjectId cityId;
}
