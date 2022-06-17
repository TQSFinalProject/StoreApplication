package com.tqs.chateauduvin.dto;

import java.util.List;
import java.util.Objects;

import com.tqs.chateauduvin.model.Wine;

public class WineDTO {
    private String name;
    private Double alcohol;
    private List<String> types;
    private Double price;
    private Integer stock;

    public Wine toWineEntity() {
        return new Wine(name, alcohol, types, price, stock);
    }

    public WineDTO() {
    }

    public WineDTO(String name, Double alcohol, List<String> types, Double price, Integer stock) {
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

    public List<String> getTypes() {
        return this.types;
    }

    public void setTypes(List<String> types) {
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof WineDTO)) {
            return false;
        }
        WineDTO wineDTO = (WineDTO) o;
        return Objects.equals(name, wineDTO.name) && Objects.equals(alcohol, wineDTO.alcohol) && Objects.equals(types, wineDTO.types) && Objects.equals(price, wineDTO.price) && Objects.equals(stock, wineDTO.stock);
    }
}
