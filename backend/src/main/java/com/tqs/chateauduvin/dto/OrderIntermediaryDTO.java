package com.tqs.chateauduvin.dto;

import com.tqs.chateauduvin.model.Order;

public class OrderIntermediaryDTO {
    private long id;
    private String orderStatus;
    private String deliveryAddress;
    private Double deliveryLat;
    private Double deliveryLong;
    private Long riderId;
    private Long storeId;
    private String orderDetails;
    private String phone;
    private Double rating;

    public OrderIntermediaryDTO() {}

    public Order toOrderEntity() {
        return new Order(id, orderStatus, deliveryAddress, deliveryLat, deliveryLong, null, null, null, riderId, storeId, orderDetails, phone, rating);
    }
}
