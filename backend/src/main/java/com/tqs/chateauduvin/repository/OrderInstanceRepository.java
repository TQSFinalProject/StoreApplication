package com.tqs.chateauduvin.repository;

import com.tqs.chateauduvin.model.OrderInstance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderInstanceRepository extends JpaRepository<OrderInstance,Long> {
    
}
