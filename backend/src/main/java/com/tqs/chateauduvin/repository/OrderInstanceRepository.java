package com.tqs.chateauduvin.repository;

import com.tqs.chateauduvin.model.Customer;
import com.tqs.chateauduvin.model.OrderInstance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderInstanceRepository extends JpaRepository<OrderInstance,Long> {
    public List<OrderInstance> findByCustomer(Customer customer);
}
