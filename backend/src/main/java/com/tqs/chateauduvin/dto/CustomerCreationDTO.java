package com.tqs.chateauduvin.dto;

import com.tqs.chateauduvin.model.Customer;

public class CustomerCreationDTO {
    private String name;
    private String phone;
    private String username;
    private String password;  

    public Customer toCustomerEntity() {
        return new Customer(name, phone, username, password);
    }

    public CustomerCreationDTO() {
    }

    public CustomerCreationDTO(String name, String phone, String username, String password) {
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
