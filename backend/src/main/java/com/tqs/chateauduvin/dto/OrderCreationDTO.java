package com.tqs.chateauduvin.dto;

import java.time.LocalDateTime;

import com.tqs.chateauduvin.model.Order;

public class OrderCreationDTO {
    public String deliveryAddress;
    public String orderDetails;
    public String phone;

    public Order toOrderEntity() {
        return new Order("created", deliveryAddress, null, LocalDateTime.now(), null, null, null, orderDetails, phone, null);
    }

    public OrderCreationDTO(String deliveryAddress, String orderDetails, String phone) {
        this.deliveryAddress = deliveryAddress;
        this.orderDetails = orderDetails;
        this.phone = phone;
    }

    public String getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOrderDetails() {
        return this.orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
