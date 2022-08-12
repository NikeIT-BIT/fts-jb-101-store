package com.ntarasov.store.photo.api.request;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter


public class PhotoUpdateRequest {
    private ObjectId id;
    private String name;
}
