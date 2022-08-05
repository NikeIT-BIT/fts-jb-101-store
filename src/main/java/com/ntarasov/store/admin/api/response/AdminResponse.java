package com.ntarasov.store.admin.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel(value = "AdminResponse", description = "Model adminResponse")

public class AdminResponse {
    protected String id;
    protected String email;
    protected String password;
}
