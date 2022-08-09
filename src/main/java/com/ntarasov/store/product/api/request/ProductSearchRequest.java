package com.ntarasov.store.product.api.request;

import com.ntarasov.store.base.api.request.SearchRequest;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
@SuperBuilder

public class ProductSearchRequest extends SearchRequest {
    private ObjectId categoryId;
}
