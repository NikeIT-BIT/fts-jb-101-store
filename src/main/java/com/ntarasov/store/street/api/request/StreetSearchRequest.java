package com.ntarasov.store.street.api.request;

import com.ntarasov.store.base.api.request.SearchRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class StreetSearchRequest extends SearchRequest {
    private ObjectId cityId;
}
