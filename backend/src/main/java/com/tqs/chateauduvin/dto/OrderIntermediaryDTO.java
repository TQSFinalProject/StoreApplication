package com.tqs.chateauduvin.dto;

import com.tqs.chateauduvin.model.Order;

public class OrderIntermediaryDTO {
    public long id;
    public String orderStatus;
    public String deliveryAddress;
    public Double deliveryLat;
    public Double deliveryLong;
    public Long riderId;
    public Long storeId;
    public String orderDetails;
    public String phone;
    public Double rating;

    public OrderIntermediaryDTO() {
        // Empty constructor for jackson serialization
    }

    public Order toOrderEntity() {
        return new Order(id, orderStatus, deliveryAddress, deliveryLat, deliveryLong, null, null, null, riderId, storeId, orderDetails, phone, rating);
    }
}
