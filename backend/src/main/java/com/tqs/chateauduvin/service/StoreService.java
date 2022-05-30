package com.tqs.chateauduvin.service;

import java.util.List;

import com.tqs.chateauduvin.model.Order;
import com.tqs.chateauduvin.model.Wine;
import com.tqs.chateauduvin.model.OrderInstance;
import com.tqs.chateauduvin.model.Customer;
import com.tqs.chateauduvin.repository.OrderRepository;
import com.tqs.chateauduvin.repository.WineRepository;
import com.tqs.chateauduvin.repository.OrderInstanceRepository;
import com.tqs.chateauduvin.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
    
    @Autowired
    OrderRepository orderRep;
    
    @Autowired
    WineRepository wineRep;

    @Autowired
    OrderInstanceRepository orderInstanceRep;

    @Autowired
    CustomerRepository customerRep;

    public List<Order> getOrders() {
        return orderRep.findAll();
    }

    public List<Wine> getWines() {
        return wineRep.findAll();
    }

    public List<OrderInstance> getOrderInstances() {
        return orderInstanceRep.findAll();
    }

    public List<Customer> getCustomers() {
        return customerRep.findAll();
    }

    public Order saveOrder(Order order) {
        return orderRep.save(order);
    }

    public Wine saveWine(Wine wine) {
        return wineRep.save(wine);
    }

    public OrderInstance saveOrderInstance(OrderInstance orderInstance) {
        return orderInstanceRep.save(orderInstance);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRep.save(customer);
    }
}