package com.tqs.chateauduvin.dto;

public class StoreDTO {
    private String storeName;
    private Double shippingTax;
    private String storeAddress;
    private Double storeLat;
    private Double storeLong;

    public StoreDTO(String storeName, Double shippingTax, String storeAddress, Double storeLat, Double storeLong) {
        this.storeName = storeName;
        this.shippingTax = shippingTax;
        this.storeAddress = storeAddress;
        this.storeLat = storeLat;
        this.storeLong = storeLong;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Double getShippingTax() {
        return this.shippingTax;
    }

    public void setShippingTax(Double shippingTax) {
        this.shippingTax = shippingTax;
    }

    public String getStoreAddress() {
        return this.storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public Double getStoreLat() {
        return this.storeLat;
    }

    public void setStoreLat(Double storeLat) {
        this.storeLat = storeLat;
    }

    public Double getStoreLong() {
        return this.storeLong;
    }

    public void setStoreLong(Double storeLong) {
        this.storeLong = storeLong;
    }
}
