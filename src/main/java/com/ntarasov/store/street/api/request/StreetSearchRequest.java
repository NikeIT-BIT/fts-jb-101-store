package com.ntarasov.store.street.api.request;

import com.ntarasov.store.base.api.request.SearchRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@SuperBuilder
@Setter
@Getter

public class StreetSearchRequest extends SearchRequest {
    private ObjectId cityId;
}
