package com.tqs.chateauduvin.model;

import java.util.Map;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orderInstances") 
public class OrderInstance {
    @Id //The ID will be auto generated
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orders")
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer")
    private Customer customer;

    @ElementCollection
    private Map<Long, Integer> cart;

    @Column(name = "delivery_time")
    private Long mgmtOrderId;

    public OrderInstance() {
    }

    public OrderInstance(Order order, Customer customer, Map<Long,Integer> cart, Long mgmtOrderId) {
        this.order = order;
        this.customer = customer;
        this.cart = cart;
        this.mgmtOrderId = mgmtOrderId;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Map<Long,Integer> getCart() {
        return this.cart;
    }

    public void setCart(Map<Long,Integer> cart) {
        this.cart = cart;
    }

    public Long getMgmtOrderId() {
        return this.mgmtOrderId;
    }

    public void setMgmtOrderId(Long mgmtOrderId) {
        this.mgmtOrderId = mgmtOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof OrderInstance)) {
            return false;
        }
        OrderInstance orderInstance = (OrderInstance) o;
        return id == orderInstance.id && Objects.equals(order, orderInstance.order) && Objects.equals(customer, orderInstance.customer) && Objects.equals(cart, orderInstance.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, customer, cart);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", order='" + getOrder() + "'" +
            ", customer='" + getCustomer() + "'" +
            ", cart='" + getCart() + "'" +
            "}";
    }

}
