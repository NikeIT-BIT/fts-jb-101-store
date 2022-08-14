package com.ntarasov.store.gues.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GuesDoc {
    @Id
    private ObjectId id;
    private String name;
    private String phone;
    private String time;
    private ObjectId cartId;
    private ObjectId streetId;
    private Address address = new Address();
    private PaymentMethod paymentMethod;

}
