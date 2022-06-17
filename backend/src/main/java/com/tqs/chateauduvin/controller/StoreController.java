package com.tqs.chateauduvin.controller;

import java.util.Map;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.tqs.chateauduvin.config.TokenProvider;
import com.tqs.chateauduvin.dto.WineDTO;
import com.tqs.chateauduvin.model.Customer;
import com.tqs.chateauduvin.model.Wine;
import com.tqs.chateauduvin.service.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3002")
@RestController
@RequestMapping("/api")
public class StoreController {
    @Autowired
    private StoreService storeServ;

    @Autowired
    private TokenProvider jwtTokenUtil;

    // Orders

    // @GetMapping("/orders")
    // public ResponseEntity<List<Order>> getOrders() {
    //     return ResponseEntity.ok().body(storeServ.getOrders());
    // }

    // @PostMapping("/orders")
    // public Order createOrder(@RequestBody Order order) {
    //     return storeServ.saveOrder(order);
    // }

    // @GetMapping("/orderinst")
    // public ResponseEntity<List<OrderInstance>> getOrderInstances() {
    //     return ResponseEntity.ok().body(storeServ.getOrderInstances());
    // }

    // @PostMapping("/orderinst")
    // public OrderInstance createOrderInstance(@RequestBody OrderInstance orderInstance) {
    //     return storeServ.saveOrderInstance(orderInstance);
    // }

    // Wines

    @GetMapping("/wines")
    public ResponseEntity<Page<Wine>> getWines(
        @RequestParam(required = false) Integer page, 
        @RequestParam(required = false) Double minPrice, 
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(required = false) Double minAlc,
        @RequestParam(required = false) Double maxAlc,
        @RequestParam(required = false) String type) {
            if(page == null) page = 0;
            if(minPrice == null) minPrice = 0.0;
            if(maxPrice == null) maxPrice = Double.MAX_VALUE;
            if(minAlc == null) minAlc = 0.0;
            if(maxAlc == null) maxAlc = 100.0;
            return ResponseEntity.ok().body(storeServ.getWinesPagedAndFiltered(page, minPrice, maxPrice, minAlc, maxAlc, type));
    }

    @GetMapping("/wines/{wineid}")
    public ResponseEntity<Wine> getWineById(@PathVariable Long wineid) {
        Optional<Wine> wine = storeServ.getWineById(wineid);
        if(wine.isPresent()) return ResponseEntity.ok().body(wine.get());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/wines")
    public Wine createWine(@RequestBody WineDTO wine) {
        return storeServ.saveWine(wine.toWineEntity());
    }

    // Authentication Example
    // @GetMapping("/orderinst")
    // public ResponseEntity<List<OrderInstance>> getOrderInstances(@RequestHeader("authorization") String auth) {
    //     String token = auth.split(" ")[1];
    //     if(jwtTokenUtil.getUsernameFromToken(token).equals("admin"))
    //         return ResponseEntity.ok().body(storeServ.getOrderInstances());
    //     else
    //         return ResponseEntity.status(401).build();
    // }

    // Cart Endpoints

    @PutMapping("/cart/{wineid}")
    public ResponseEntity<String> addWineToCart(@RequestHeader("authorization") String auth, @PathVariable Long wineid, @RequestParam(required = false) Integer quantity) {
        String token = auth.split(" ")[1];
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer customer = storeServ.getCustomerByUsername(username);
        if(quantity == null) quantity = 1;
        try {
            storeServ.addWineToCart(customer, wineid, quantity);
            return ResponseEntity.ok().build();
        } catch(NoSuchElementException e) {
            return ResponseEntity.status(404).body("Wine not found.");
        } catch(MissingResourceException e) {
            return ResponseEntity.status(406).body("Not enough stock.");
        }

    }

    @DeleteMapping("/cart/{wineid}")
    public ResponseEntity<String> deleteWineToCart(@RequestHeader("authorization") String auth, @PathVariable Long wineid, @RequestParam(required = false) Integer quantity) {
        String token = auth.split(" ")[1];
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer customer = storeServ.getCustomerByUsername(username);
        if(quantity == null) quantity = 1;
        try {
            storeServ.deleteWineFromCart(customer, wineid, quantity);
            return ResponseEntity.ok().build();
        } catch(NoSuchElementException e) {
            return ResponseEntity.status(404).body("Wine not found.");
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<Map<Long,Integer>> getCustomerCart(@RequestHeader("authorization") String auth) {
        String token = auth.split(" ")[1];
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer customer = storeServ.getCustomerByUsername(username);
        return ResponseEntity.ok().body(customer.getCart());
    }
 
}