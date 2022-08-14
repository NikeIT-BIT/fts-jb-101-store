package com.ntarasov.store.order.api.response;

import com.ntarasov.store.order.model.Status;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel(value = "OrderResponse", description = "Model orderResponse")

public class OrderResponse {
        protected String id;
        protected String guesId;
        protected String  numberOrder;
        protected Status status;
}
