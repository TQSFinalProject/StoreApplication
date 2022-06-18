package com.tqs.chateauduvin.dto;

import com.tqs.chateauduvin.model.Customer;

public class CustomerDTO {
    private Long id;
    private String name;
    private String phone;
    private String username;

    public static CustomerDTO fromCustomerEntity(Customer cust) {
        return new CustomerDTO(cust.getId(), cust.getName(), cust.getPhone(), cust.getUsername());
    }

    public CustomerDTO() {
    }

    public CustomerDTO(Long id, String name, String phone, String username) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.username = username;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
