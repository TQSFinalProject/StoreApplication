package com.tqs.chateauduvin.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wines")
public class Wine {
    @Id // The ID will be auto generated
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "alcohol", nullable = false)
    private Double alcohol;

    @Column(name = "types", nullable = false)
    private String types;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "stock", nullable = false)
    private Integer stock;


    public Wine() {
    }

    public Wine(String name, Double alcohol, String types, Double price, Integer stock) {
        this.name = name;
        this.alcohol = alcohol;
        this.types = types;
        this.price = price;
        this.stock = stock;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Wine)) {
            return false;
        }
        Wine wine = (Wine) o;
        return id == wine.id && Objects.equals(name, wine.name) && Objects.equals(alcohol, wine.alcohol) && Objects.equals(types, wine.types) && Objects.equals(price, wine.price) && Objects.equals(stock, wine.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, alcohol, types, price, stock);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", alcohol='" + getAlcohol() + "'" +
            ", types='" + getTypes() + "'" +
            ", price='" + getPrice() + "'" +
            ", stock='" + getStock() + "'" +
            "}";
    }

}
