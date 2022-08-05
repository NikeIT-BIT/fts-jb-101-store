package com.ntarasov.store.admin.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "AdminRequest", description = "Model admin request")

public class AdminRequest {
    private ObjectId id;
    private String email;
    private String password;
}
