package com.tqs.chateauduvin.dto;

public class StoreDTO {
    private String storeName;
    private Double shippingTax;
    private String storeAddress;
    private Double storeLat;
    private Double storeLong;
    private String password;
    private String username;

    public StoreDTO(String storeName, Double shippingTax, String storeAddress, Double storeLat, Double storeLong, String password, String username) {
        this.storeName = storeName;
        this.shippingTax = shippingTax;
        this.storeAddress = storeAddress;
        this.storeLat = storeLat;
        this.storeLong = storeLong;
        this.password = password;
        this.username = username;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public Double getShippingTax() {
        return this.shippingTax;
    }

    public String getStoreAddress() {
        return this.storeAddress;
    }

    public Double getStoreLat() {
        return this.storeLat;
    }

    public Double getStoreLong() {
        return this.storeLong;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }
}
