package com.tqs.chateauduvin.dto;

public class StoreDTO {
    private String storeName;
    private Double shippingTax;
    private String storeAddress;

    public StoreDTO(String storeName, Double shippingTax, String storeAddress) {
        this.storeName = storeName;
        this.shippingTax = shippingTax;
        this.storeAddress = storeAddress;
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
}
