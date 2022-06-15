package com.tqs.chateauduvin.model;

import java.util.Map;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {
    @Id // The ID will be auto generated
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection
    private Map<Wine, Integer> cart;

    public Customer() {
    }

    public Customer(String name, String phone, String username, String password, Map<Wine,Integer> cart) {
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.cart = cart;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<Wine,Integer> getCart() {
        return this.cart;
    }

    public void setCart(Map<Wine,Integer> cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Customer)) {
            return false;
        }
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(name, customer.name) && Objects.equals(phone, customer.phone) && Objects.equals(username, customer.username) && Objects.equals(password, customer.password) && Objects.equals(cart, customer.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, username, password, cart);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", cart='" + getCart() + "'" +
            "}";
    }
    
}
