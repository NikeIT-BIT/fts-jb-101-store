package com.ntarasov.store.auth.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class AuthResponse {
    private String token;
}
