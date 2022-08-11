package com.ntarasov.store.street.api.request;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter

public class StreetUpdateRequest {
    private ObjectId id;
    private String  name;
}
