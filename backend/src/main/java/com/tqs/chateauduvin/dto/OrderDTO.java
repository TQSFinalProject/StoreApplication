package com.tqs.chateauduvin.dto;

import java.util.Map;

import com.tqs.chateauduvin.model.Order;
import com.tqs.chateauduvin.model.OrderInstance;

public class OrderDTO {
    private Long id;
    private Order order;
    private CustomerDTO customer;
    private Map<Long, Integer> cart;
    private Long mgmtOrderId;

    public static OrderDTO fromOrderInstanceEntity(OrderInstance orderInst) {
        return new OrderDTO(orderInst.getId(), orderInst.getOrder(), CustomerDTO.fromCustomerEntity(orderInst.getCustomer()), orderInst.getCart(), orderInst.getMgmtOrderId());
    }

    public OrderDTO(Long id, Order order, CustomerDTO customer, Map<Long,Integer> cart, Long mgmtOrderId) {
        this.id = id;
        this.order = order;
        this.customer = customer;
        this.cart = cart;
        this.mgmtOrderId = mgmtOrderId;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public CustomerDTO getCustomer() {
        return this.customer;
    }

    public void setCustomer(CustomerDTO customer) {
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
}
