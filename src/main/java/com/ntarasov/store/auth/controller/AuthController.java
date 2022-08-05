package com.ntarasov.store.auth.controller;

import com.ntarasov.store.auth.api.request.AuthRequest;
import com.ntarasov.store.auth.api.response.AuthResponse;
import com.ntarasov.store.auth.exceptions.AuthException;
import com.ntarasov.store.auth.routes.AuthRoutes;
import com.ntarasov.store.auth.service.AuthService;
import com.ntarasov.store.base.api.response.OkResponse;
import com.ntarasov.store.admin.api.request.AdminRequest;
import com.ntarasov.store.admin.api.response.AdminResponse;
import com.ntarasov.store.admin.exception.AdminExistException;
import com.ntarasov.store.admin.exception.AdminNotExistException;
import com.ntarasov.store.admin.mapping.AdminMapping;
import com.ntarasov.store.admin.service.AdminApiService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController

public class AuthController {

    private final AdminApiService adminApiService;
    private final AuthService authService;

    @PostMapping(AuthRoutes.REGISTRATION)
    @ApiOperation(value = "registr", notes = "Admin this when you need register and new create admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Admin alreade exist")
    })
    public OkResponse<AdminResponse> registration(@RequestBody AdminRequest request) throws AdminExistException {
        return OkResponse.of(AdminMapping.getInstance().getResponse().convert(adminApiService.registration(request)));

    }

    @PostMapping(AuthRoutes.AUTH)
    @ApiOperation(value = "authorization", notes = "Get token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Admin not exist"),
            @ApiResponse(code = 401, message = "Bad password")
    })
    public OkResponse<AuthResponse> auth(@RequestBody AuthRequest request) throws AuthException, AdminNotExistException {
        return OkResponse.of(authService.auth(request));
    }
}
