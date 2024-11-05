package org.example.servera.controller;

import org.example.servera.JwtTokenProvider;
import org.example.servera.entity.CustomUserDetails;
import org.example.servera.entity.RefreshToken;
import org.example.servera.entity.RefreshTokenRequest;
import org.example.servera.entity.User;
import org.example.servera.payload.LoginRequest;
import org.example.servera.payload.LoginResponse;
import org.example.servera.payload.TokenRefreshResponse;
import org.example.servera.service.RefreshTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    RefreshTokenService refreshTokenService;

    // Initialize the logger
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    public LoginResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        //log loginRequest
        logger.info(loginRequest.toString());
        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateTokenFromUsername(loginRequest.getUsername());
        // tạo refreshToken
        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken( ((CustomUserDetails) authentication.getPrincipal()).getUsername());
        return new LoginResponse(jwt, refreshToken.getToken());
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody RefreshTokenRequest refreshToken) {
        String requestRefreshToken = refreshToken.getRefreshToken();
        System.out.println("request refresh token" + requestRefreshToken);
        // find refreshToken in database
        Optional<RefreshToken> refreshTokenEntity = refreshTokenService.findByToken(requestRefreshToken);
        if(refreshTokenEntity.isPresent()){
            RefreshToken refreshTokenEntity1 = refreshTokenEntity.get();
            // verify expiration, return null if expired
            refreshTokenEntity1 = refreshTokenService.verifyExpiration(refreshTokenEntity1);
            if(refreshTokenEntity1 != null){
                // get user from refreshToken
                User user = refreshTokenEntity1.getUser();
                // generate new token
                String token = tokenProvider.generateTokenFromUsername(user.getUsername());
                return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
            }
            else {
                return ResponseEntity.badRequest().body("Refresh token is expired!");
            }
        }
        return ResponseEntity.badRequest().body("Refresh token is not in database!");

//        return refreshTokenService.findByToken(requestRefreshToken)
//                .map(refreshTokenService::verifyExpiration)
//                .map(RefreshToken::getUser)
//                .map(user -> {
//                    String token = tokenProvider.generateTokenFromUsername(user.getUsername());
//                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
//                })
//                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

}

