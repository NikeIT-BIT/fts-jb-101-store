package com.ntarasov.store.gues.api.request;

import com.ntarasov.store.gues.model.Address;
import com.ntarasov.store.gues.model.PaymentMethod;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "GuesRequest", description = "Model gues request")

public class GuesRequest {
        private ObjectId id;
        private String  name;
        private String  phone;
        private String  time;
        private ObjectId cartId;
        private Address address = new Address();
        private PaymentMethod paymentMethod = new PaymentMethod();
}
