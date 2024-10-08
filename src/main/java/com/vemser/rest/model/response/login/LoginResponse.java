package com.vemser.rest.model.response.login;

public class LoginResponse {

    private String email;
    private String password;
    private String message;
    private String authorization;

    public LoginResponse() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", message='" + message + '\'' +
                ", authorization='" + authorization + '\'' +
                '}';
    }
}
