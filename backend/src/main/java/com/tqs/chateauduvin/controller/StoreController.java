package com.tqs.chateauduvin.controller;

import java.util.List;
import java.util.Map;

import com.tqs.chateauduvin.config.TokenProvider;
import com.tqs.chateauduvin.model.Order;
import com.tqs.chateauduvin.model.Wine;
import com.tqs.chateauduvin.model.OrderInstance;
import com.tqs.chateauduvin.service.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class StoreController {
    @Autowired
    private StoreService storeServ;

    @Autowired
    private TokenProvider jwtTokenUtil;

    // Base Endpoints

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok().body(storeServ.getOrders());
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order order) {
        return storeServ.saveOrder(order);
    }

    @GetMapping("/wines")
    public ResponseEntity<List<Wine>> getWines() {
        return ResponseEntity.ok().body(storeServ.getWines());
    }

    @PostMapping("/wines")
    public Wine createWine(@RequestBody Wine wine) {
        return storeServ.saveWine(wine);
    }

    @GetMapping("/orderinst")
    public ResponseEntity<List<OrderInstance>> getOrderInstances() {
        return ResponseEntity.ok().body(storeServ.getOrderInstances());
    }

    @PostMapping("/orderinst")
    public OrderInstance createOrderInstance(@RequestBody OrderInstance orderInstance) {
        return storeServ.saveOrderInstance(orderInstance);
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
}