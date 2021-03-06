package com.tqs.chateauduvin.dto;

import com.tqs.chateauduvin.model.Wine;

public class WineDTO {
    private String name;
    private Double alcohol;
    private String types;
    private Double price;
    private Integer stock;

    public Wine toWineEntity() {
        return new Wine(name, alcohol, types, price, stock);
    }

    public static WineDTO fromWineEntity(Wine wine) {
        return new WineDTO(wine.getName(), wine.getAlcohol(), wine.getTypes(), wine.getPrice(), wine.getStock());
    }

    public WineDTO() {
    }

    public WineDTO(String name, Double alcohol, String types, Double price, Integer stock) {
        this.name = name;
        this.alcohol = alcohol;
        this.types = types;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAlcohol() {
        return this.alcohol;
    }

    public void setAlcohol(Double alcohol) {
        this.alcohol = alcohol;
    }

    public String getTypes() {
        return this.types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
