package com.ntarasov.store.price.api.request;

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

public class PriceSearchRequest extends SearchRequest {
    private ObjectId productId;
    private ObjectId cityId;
}
