package com.ntarasov.store.price.api.request;

import com.ntarasov.store.base.api.request.SearchRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@SuperBuilder
@Getter
@Setter

public class PriceSearchRequest extends SearchRequest {
    private ObjectId productId;
    private ObjectId cityId;
}
