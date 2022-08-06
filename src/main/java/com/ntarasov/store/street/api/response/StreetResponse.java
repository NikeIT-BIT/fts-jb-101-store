package com.ntarasov.store.street.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel(value = "StreetResponse", description = "Model streetResponse")

public class StreetResponse {
        protected String id;
        protected String cityId;
        protected String  name;
}
