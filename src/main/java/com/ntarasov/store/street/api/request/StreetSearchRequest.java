package com.ntarasov.store.street.api.request;

import com.ntarasov.store.base.api.request.SearchRequest;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@SuperBuilder

public class StreetSearchRequest extends SearchRequest {
    private ObjectId cityId;
}
