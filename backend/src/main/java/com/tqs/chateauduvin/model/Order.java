package com.tqs.chateauduvin.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order")
public class Order {
    @Id // The ID will be auto generated
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "estimated_delivery_time")
    private LocalDateTime estimatedDeliveryTime;

    @Column(name = "submited_time", nullable = false)
    private LocalDateTime submitedTime;

    @Column(name = "delivery_time")
    private LocalDateTime deliveryTime;

    @Column(name = "rider_id")
    private Long riderId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "details")
    private String details;

    @Column(name = "phone", nullable = false)
    private Long phone;

    public Order() {
    }

    public Order(long id, String status, String deliveryAddress, LocalDateTime estimatedDeliveryTime,
            LocalDateTime submitedTime, LocalDateTime deliveryTime, Long riderId, Long storeId, String details,
            Long phone) {
        this.id = id;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.submitedTime = submitedTime;
        this.deliveryTime = deliveryTime;
        this.riderId = riderId;
        this.storeId = storeId;
        this.details = details;
        this.phone = phone;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDateTime getEstimatedDeliveryTime() {
        return this.estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public LocalDateTime getSubmitedTime() {
        return this.submitedTime;
    }

    public void setSubmitedTime(LocalDateTime submitedTime) {
        this.submitedTime = submitedTime;
    }

    public Long getRiderId() {
        return this.riderId;
    }

    public void setRiderId(Long riderId) {
        this.riderId = riderId;
    }

    public Long getStoreId() {
        return this.storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getPhone() {
        return this.phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public LocalDateTime getDeliveryTime() {
        return this.deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", status='" + getStatus() + "'" +
                ", deliveryAddress='" + getDeliveryAddress() + "'" +
                ", estimatedDeliveryTime='" + getEstimatedDeliveryTime() + "'" +
                ", submitedTime='" + getSubmitedTime() + "'" +
                ", deliveryTime='" + getDeliveryTime() + "'" +
                ", riderId='" + getRiderId() + "'" +
                ", storeId='" + getStoreId() + "'" +
                ", details='" + getDetails() + "'" +
                ", phone='" + getPhone() + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Order)) {
            return false;
        }
        Order order = (Order) o;
        return id == order.id && Objects.equals(status, order.status) && Objects.equals(deliveryAddress, order.deliveryAddress) && Objects.equals(estimatedDeliveryTime, order.estimatedDeliveryTime) && Objects.equals(submitedTime, order.submitedTime) && Objects.equals(deliveryTime, order.deliveryTime) && Objects.equals(riderId, order.riderId) && Objects.equals(storeId, order.storeId) && Objects.equals(details, order.details) && Objects.equals(phone, order.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, deliveryAddress, estimatedDeliveryTime, submitedTime, deliveryTime, riderId, storeId, details, phone);
    }


}
