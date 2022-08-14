package com.ntarasov.store.photo.api.request;

import com.ntarasov.store.base.api.request.SearchRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class PhotoSearchRequest extends SearchRequest {
    private ObjectId productId;
}
