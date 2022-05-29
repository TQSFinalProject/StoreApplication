package com.tqs.chateauduvin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {
    @Id // The ID will be auto generated
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


}
