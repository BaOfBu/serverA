package org.example.servera.payload;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    // thêm vào refreshToken
    private String refreshToken;
    private String tokenType = "Bearer";

    public LoginResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
