package com.website.ecommerce.model;

public class AuthenticationResponse {

    private String token;
    private String error;

    public AuthenticationResponse(String token, String error) {
        this.token = token;
        this.error = error;
    }

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    
}