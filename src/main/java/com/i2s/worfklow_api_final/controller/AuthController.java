package com.i2s.worfklow_api_final.controller;

import com.i2s.worfklow_api_final.dto.LoginRequestDTO;
import com.i2s.worfklow_api_final.security.JwtTokenProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    AuthenticationManager authenticationManager;

    JwtTokenProvider tokenProvider;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        Long userId = tokenProvider.getUserIdFromJWT(jwt);
        String role = tokenProvider.getRoleFromJWT(jwt);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt,userId,role));
    }

    @Getter
    @Setter
    public static class JwtAuthenticationResponse {
        private String accessToken;
        private String tokenType = "Bearer";
        private long userId;
        private String role;

        public JwtAuthenticationResponse(String accessToken, long userId, String role) {
            this.accessToken = accessToken;
            this.userId = userId;
            this.role = role;
        }


    }
}
