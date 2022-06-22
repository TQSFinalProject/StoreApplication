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

    public OrderIntermediaryDTO(long id, String orderStatus, String deliveryAddress, Double deliveryLat, Double deliveryLong, Long riderId, Long storeId, String orderDetails, String phone, Double rating) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.deliveryAddress = deliveryAddress;
        this.deliveryLat = deliveryLat;
        this.deliveryLong = deliveryLong;
        this.riderId = riderId;
        this.storeId = storeId;
        this.orderDetails = orderDetails;
        this.phone = phone;
        this.rating = rating;
    }

    public OrderIntermediaryDTO() {}

    public Order toOrderEntity() {
        return new Order(id, orderStatus, deliveryAddress, deliveryLat, deliveryLong, null, null, null, riderId, storeId, orderDetails, phone, rating);
    }

    public long getId() {
        return this.id;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public String getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public Double getDeliveryLat() {
        return this.deliveryLat;
    }

    public Double getDeliveryLong() {
        return this.deliveryLong;
    }

    public Long getRiderId() {
        return this.riderId;
    }

    public Long getStoreId() {
        return this.storeId;
    }

    public String getOrderDetails() {
        return this.orderDetails;
    }

    public String getPhone() {
        return this.phone;
    }

    public Double getRating() {
        return this.rating;
    }
}
