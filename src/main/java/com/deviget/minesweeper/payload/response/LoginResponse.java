package com.deviget.minesweeper.payload.response;

import java.util.List;

/**
 * Payload class used for user login responses.
 * 
 * @author david.rios
 */
public class LoginResponse {

    private String token;
    private String username;
    private String email;
    private String type = "Bearer";
    private List<String> roles;

    public LoginResponse(String accessToken, String username, String email, List<String> roles) {
        this.username = username;
        this.email = email;
        this.token = accessToken;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
