package com.ntarasov.store.order.api.request;

import com.ntarasov.store.base.api.response.OkResponse;
import com.ntarasov.store.order.model.Status;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "OrderRequest", description = "Model order request")

public class OrderRequest {
        private ObjectId id;
        private ObjectId guesId;
        private String  numberOrder;
        private Status status;
}
