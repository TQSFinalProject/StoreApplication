package com.tqs.chateauduvin.service;

import java.util.ArrayList;
// import java.util.HashSet;
import java.util.List;
// import java.util.Set;


import com.tqs.chateauduvin.model.Order;
import com.tqs.chateauduvin.model.Wine;
import com.tqs.chateauduvin.model.OrderInstance;
import com.tqs.chateauduvin.model.Customer;
import com.tqs.chateauduvin.repository.OrderRepository;
import com.tqs.chateauduvin.repository.WineRepository;
import com.tqs.chateauduvin.repository.OrderInstanceRepository;
import com.tqs.chateauduvin.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class StoreService implements UserDetailsService {

    // Order Logic
    
    @Autowired
    OrderRepository orderRep;

    @Autowired
    OrderInstanceRepository orderInstanceRep;

    public List<Order> getOrders() {
        return orderRep.findAll();
    }

    public Order saveOrder(Order order) {
        return orderRep.save(order);
    }

    public List<OrderInstance> getOrderInstances() {
        return orderInstanceRep.findAll();
    }

    public OrderInstance saveOrderInstance(OrderInstance order) {
        return orderInstanceRep.save(order);
    }

    // Wine Logic
    
    @Autowired
    WineRepository wineRep;

    public List<Wine> getWines() {
        return wineRep.findAll();
    }

    public Wine saveWine(Wine wine) {
        return wineRep.save(wine);
    }

    // Customer Logic

    @Autowired
    CustomerRepository customerRep;

    // @Autowired
    private BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

    public List<Customer> getCustomers() {
        return customerRep.findAll();
    }

    public Customer getCustomer(String username) {
        return customerRep.findByUsername(username);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer cust = customerRep.findByUsername(username);
        if(cust == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        // In case we need role authorization
        // return new org.springframework.security.core.userdetails.User(cust.getUsername(), cust.getPassword(), getAuthority(user));
        
        return new org.springframework.security.core.userdetails.User(cust.getUsername(), cust.getPassword(), new ArrayList<>());
    }

    // In case we need role authorization
    // private Set<SimpleGrantedAuthority> getAuthority(Customer cust) {
    //     Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    //     cust.getRoles().forEach(role -> {
    //         authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
    //     });
    //     return authorities;
    // }

    public Customer saveCustomer(Customer cust) {

        cust.setPassword(bcryptEncoder.encode(cust.getPassword()));

        // In case we need role authorization
        // Role role = roleService.findByName("USER");
        // Set<Role> roleSet = new HashSet<>();
        // roleSet.add(role);

        return customerRep.save(cust);
    }

}