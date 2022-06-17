package com.tqs.chateauduvin.dto;

import java.util.Map;
import java.util.Objects;

import com.tqs.chateauduvin.model.Customer;

public class CustomerDTO {
    private Long id;
    private String name;
    private String phone;
    private String username;
    private Map<Long,Integer> cart;

    public static CustomerDTO fromCustomerEntity(Customer cust) {
        return new CustomerDTO(cust.getId(), cust.getName(), cust.getPhone(), cust.getUsername(), cust.getCart());
    }

    public CustomerDTO() {
    }

    public CustomerDTO(Long id, String name, String phone, String username, Map<Long,Integer> cart) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.cart = cart;
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

    public Map<Long,Integer> getCart() {
        return this.cart;
    }

    public void setCart(Map<Long,Integer> cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CustomerDTO)) {
            return false;
        }
        CustomerDTO customerDTO = (CustomerDTO) o;
        return Objects.equals(id, customerDTO.id) && Objects.equals(name, customerDTO.name) && Objects.equals(phone, customerDTO.phone) && Objects.equals(username, customerDTO.username) && Objects.equals(cart, customerDTO.cart);
    }

}
