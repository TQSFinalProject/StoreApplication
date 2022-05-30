package com.tqs.chateauduvin.repository;

import com.tqs.chateauduvin.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{
    
}