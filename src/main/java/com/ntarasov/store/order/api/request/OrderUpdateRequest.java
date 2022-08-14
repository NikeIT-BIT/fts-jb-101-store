package com.ntarasov.store.order.api.request;

import com.ntarasov.store.order.model.Status;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter

public class OrderUpdateRequest {
    private ObjectId id;
    private Status status;
}
