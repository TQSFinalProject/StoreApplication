package com.tqs.chateauduvin.dto;

import java.util.Objects;

public class AuthTokenDTO {
    private String token;

    public AuthTokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AuthTokenDTO)) {
            return false;
        }
        AuthTokenDTO authToken = (AuthTokenDTO) o;
        return Objects.equals(token, authToken.token);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(token);
    }

    @Override
    public String toString() {
        return "{" +
            " token='" + getToken() + "'" +
            "}";
    }

}
