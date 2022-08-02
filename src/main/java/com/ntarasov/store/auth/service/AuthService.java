package com.ntarasov.store.auth.service;

import com.ntarasov.store.auth.api.request.AuthRequest;
import com.ntarasov.store.auth.entity.CustomAdminDetails;
import com.ntarasov.store.auth.exceptions.AuthException;
import com.ntarasov.store.admin.exception.AdminNotExistException;
import com.ntarasov.store.admin.model.AdminDoc;
import com.ntarasov.store.admin.repository.AdminRepository;
import com.ntarasov.store.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class AuthService {
    private final AdminRepository adminRepository;
    private final JwtProvider jwtProvider;

    public CustomAdminDetails loadAdminByEmail(String email) throws AdminNotExistException {
        AdminDoc adminDoc = adminRepository.findByEmail(email).orElseThrow(AdminNotExistException::new);
        return CustomAdminDetails.fromAdminEntityToCustomAdminDetails(adminDoc);
    }
    public String auth(AuthRequest authRequest) throws AdminNotExistException, AuthException {
        AdminDoc adminDoc = adminRepository.findByEmail(authRequest.getEmail()).orElseThrow(AdminNotExistException::new);
        if(adminDoc.getPassword().equals(AdminDoc.hexPassword(authRequest.getPassword())) == false){
            adminDoc.setFailLogin(adminDoc.getFailLogin() + 1);
            adminRepository.save(adminDoc);
            throw new AuthException();
        }
        if(adminDoc.getFailLogin() > 0){
            adminDoc.setFailLogin(0);
            adminRepository.save(adminDoc);
        }

        String token = jwtProvider.generateToken(authRequest.getEmail());
        return token;
    }
}
