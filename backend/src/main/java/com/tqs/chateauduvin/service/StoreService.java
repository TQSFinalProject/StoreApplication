package com.tqs.chateauduvin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.tqs.chateauduvin.model.Order;
import com.tqs.chateauduvin.model.Wine;
import com.tqs.chateauduvin.model.OrderInstance;
import com.tqs.chateauduvin.dto.OrderCreationDTO;
import com.tqs.chateauduvin.dto.OrderDTO;
import com.tqs.chateauduvin.model.Customer;
import com.tqs.chateauduvin.repository.OrderRepository;
import com.tqs.chateauduvin.repository.WineRepository;
import com.tqs.chateauduvin.repository.OrderInstanceRepository;
import com.tqs.chateauduvin.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class StoreService implements UserDetailsService {
    @Autowired
    OrderRepository orderRep;

    @Autowired
    OrderInstanceRepository orderInstanceRep;

    @Value("${ti.url}")
    public String URL;

    private HttpRequests httpRequests = new HttpRequests();

    // Wine Logic
    
    @Autowired
    WineRepository wineRep;

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

    public Page<Wine> getWinesPagedAndFiltered(Integer page, Double minPrice, Double maxPrice, Double minAlc, Double maxAlc, String type) {
        Pageable pageable = PageRequest.of(page,8);
        if(type == null)
            return wineRep.findByPriceBetweenAndAlcoholBetween(minPrice, maxPrice, minAlc, maxAlc, pageable);
        else
            return wineRep.findByPriceBetweenAndAlcoholBetweenAndTypesContaining(minPrice, maxPrice, minAlc, maxAlc, type, pageable);
    }

    public OrderInstance newOrder(Customer customer, OrderCreationDTO orderDTO) throws Exception, IOException, InterruptedException {
        Map<Long, Integer> custCart = customer.getCart();
        if(custCart.isEmpty()) throw new NoSuchElementException();
        for(Long wineId : custCart.keySet()) {
            Optional<Wine> optWine = wineRep.findById(wineId);
            if(optWine.isPresent()) {
                Wine wine = optWine.get();
                wine.setStock(wine.getStock() - custCart.get(wineId));
                wineRep.save(wine);
            }
            else throw new NoSuchElementException();
            List<Customer> customers = customerRep.findAll();
            int customerNum = customers.size();
            for(int i = 0; i < customerNum; i++) {
                Customer otherCustomer = customers.get(i);
                if(otherCustomer == customer) continue;
                Map<Long, Integer> otherCustomerCart = otherCustomer.getCart();
                otherCustomerCart.remove(wineId);
                otherCustomer.setCart(otherCustomerCart);
                customerRep.save(otherCustomer);
            }
        }
        Order order = orderDTO.toOrderEntity();
        Map<Long, Integer> orderCart = new HashMap<>(custCart);
        OrderInstance orderInst = new OrderInstance(order, customer, orderCart);
        httpRequests.sendNewOrder(URL, order);
        orderInstanceRep.save(orderInst);
        customer.setCart(new HashMap<>());
        customerRep.save(customer);
        return orderInst;
    }

    public List<OrderDTO> getCustomerOrders(Customer customer) {
        List<OrderInstance> found = orderInstanceRep.findByCustomer(customer);
        List<OrderDTO> ret = new ArrayList<>();
        for(OrderInstance instance : found) {
            ret.add(OrderDTO.fromOrderInstanceEntity(instance));
        }
        return ret;
    }

    public OrderInstance getCustomerOrder(Customer customer, Long orderId) {
        Optional<OrderInstance> optOrder = orderInstanceRep.findById(orderId);
        if(optOrder.isPresent()) {
            OrderInstance order = optOrder.get();
            if(!order.getCustomer().getUsername().equals(customer.getUsername())) throw new SecurityException();
            else return order;
        }
        else throw new NoSuchElementException();
    }

}