package com.ntarasov.store.photo.api.request;

import com.ntarasov.store.base.api.request.SearchRequest;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@SuperBuilder

public class PhotoSearchRequest extends SearchRequest {
    private ObjectId productId;
}
