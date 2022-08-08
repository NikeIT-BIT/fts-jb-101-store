package com.ntarasov.store.gues.api.response;

import com.ntarasov.store.gues.model.Address;
import com.ntarasov.store.gues.model.PaymentMethod;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel(value = "GuesResponse", description = "Model guesResponse")

public class GuesResponse {
        protected String id;
        protected String  name;
        protected String  phone;
        protected String  time;
        protected String cartId;
        protected Address address;
        protected PaymentMethod paymentMethod;
}
