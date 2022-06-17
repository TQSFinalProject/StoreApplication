package com.tqs.chateauduvin.service;

import java.util.ArrayList;
// import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
// import java.util.Set;
import java.util.Optional;

import com.tqs.chateauduvin.model.Order;
import com.tqs.chateauduvin.model.Wine;
import com.tqs.chateauduvin.model.OrderInstance;
import com.tqs.chateauduvin.model.Customer;
import com.tqs.chateauduvin.repository.OrderRepository;
import com.tqs.chateauduvin.repository.WineRepository;
import com.tqs.chateauduvin.repository.OrderInstanceRepository;
import com.tqs.chateauduvin.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    public Optional<Wine> getWineById(Long id) {
        return wineRep.findById(id);
    }

    public Wine saveWine(Wine wine) {
        return wineRep.save(wine);
    }

    // Customer Logic

    @Autowired
    CustomerRepository customerRep;

    // @Autowired
    private BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

    public Customer saveCustomer(Customer cust) {

        cust.setPassword(bcryptEncoder.encode(cust.getPassword()));

        // In case we need role authorization
        // Role role = roleService.findByName("USER");
        // Set<Role> roleSet = new HashSet<>();
        // roleSet.add(role);

        return customerRep.save(cust);
    }

    public Customer getCustomerByUsername(String username) {
        return customerRep.findByUsername(username);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRep.findById(id);
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

    

    // Cart Logic

    public void addWineToCart(Customer cust, Long wineid, Integer quantity) {
        Optional<Wine> optWine = getWineById(wineid);
        Wine wine;
        if(optWine.isPresent())
            wine = optWine.get();
        else
            throw new NoSuchElementException();


        Map<Long,Integer> cart = cust.getCart();
        
        Integer overall = (cart.keySet().contains(wine.getId())) ? (cart.get(wine.getId())+quantity) : quantity;

        if(wine.getStock() < overall) throw new MissingResourceException(null, null, null);
        else cart.put(wine.getId(), overall);

        cust.setCart(cart);
        customerRep.save(cust);
    }

    public void deleteWineFromCart(Customer customer, Long wineid, Integer quantity) {
        Optional<Wine> optWine = getWineById(wineid);
        Wine wine;
        if(optWine.isPresent())
            wine = optWine.get();
        else
            throw new NoSuchElementException();

        Map<Long, Integer> cart = customer.getCart(); 

        // Is wine in user cart
        if(cart.containsKey(wine.getId())) {
            cart.put(wine.getId(), cart.get(wine.getId())-quantity);
            if(cart.get(wine.getId())<=0) cart.remove(wine.getId());
            customer.setCart(cart);
            customerRep.save(customer);
        }
        else throw new NoSuchElementException();
    }

}