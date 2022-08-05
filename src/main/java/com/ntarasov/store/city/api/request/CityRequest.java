package com.ntarasov.store.city.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.HashMap;

@Getter
@Setter
@ApiModel(value = "CityRequest", description = "Model city request")

public class CityRequest {
    private ObjectId id;
    private String name;
    private String minTimeDelivery;
    private String priceDelivery;
}
