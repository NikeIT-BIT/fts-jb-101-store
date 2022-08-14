package com.ntarasov.store.order.api.request;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.order.model.Status;
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

public class OrderSearchRequest extends SearchRequest {
    private ObjectId guesId;
    private Status status;
}
