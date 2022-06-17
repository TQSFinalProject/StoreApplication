package com.tqs.chateauduvin.dto;

import java.util.Objects;

public class LogInRequestDTO {
    private String username;
    private String password;

    public LogInRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof LogInRequestDTO)) {
            return false;
        }
        LogInRequestDTO logInReq = (LogInRequestDTO) o;
        return Objects.equals(username, logInReq.username) && Objects.equals(password, logInReq.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "{" +
            " username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }

}
