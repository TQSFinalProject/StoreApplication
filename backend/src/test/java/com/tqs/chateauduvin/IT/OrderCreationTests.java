package com.tqs.chateauduvin.IT;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.tqs.chateauduvin.ChateauduvinApplication;
import com.tqs.chateauduvin.JsonUtils;
import com.tqs.chateauduvin.dto.LogInRequestDTO;
import com.tqs.chateauduvin.dto.OrderCreationDTO;
import com.tqs.chateauduvin.model.Customer;
import com.tqs.chateauduvin.model.Wine;
import com.tqs.chateauduvin.repository.CustomerRepository;
import com.tqs.chateauduvin.repository.OrderInstanceRepository;
import com.tqs.chateauduvin.repository.WineRepository;
import com.tqs.chateauduvin.service.StoreService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;  

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = ChateauduvinApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderCreationTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private WineRepository wineRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderInstanceRepository orderRepository;

    @Autowired
    private StoreService storeServ;

    String token1;
    String token2;

    Wine w1;
    Wine w2;
    Wine w3;

    @AfterAll
    public void resetDb() {
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        wineRepository.deleteAll();
    }

    @BeforeAll
    void setUp() throws IOException, Exception {
        w1 = new Wine("w1", 12.0, "dry;rose", 12.99, 12);
        w2 = new Wine("w2", 12.0, "dry;white", 12.99, 6);
        w3 = new Wine("w3", 12.0, "red", 12.99, 8);
        wineRepository.save(w1);
        wineRepository.save(w2);
        wineRepository.save(w3);

        Customer cust1 = new Customer("Bob", "919191919", "BobPancakes123", "bobby99");
        Map<Long,Integer> cart1 = new HashMap<>();
        cart1.put(w1.getId(), 5);
        cart1.put(w2.getId(), 3);
        cust1.setCart(cart1);
        storeServ.saveCustomer(cust1);
        LogInRequestDTO req1 = new LogInRequestDTO("BobPancakes123", "bobby99");
        MvcResult result1 = mvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(req1))).andReturn();
        JSONObject tokenJSON1 = new JSONObject(result1.getResponse().getContentAsString());
        token1 = tokenJSON1.getString("token");

        Customer cust2 = new Customer("Jess", "929292929", "Jessica123", "jess00"); 
        Map<Long,Integer> cart2 = new HashMap<>();
        cart2.put(w1.getId(), 1);
        cart2.put(w2.getId(), 2);
        cart2.put(w3.getId(), 3);
        cust2.setCart(cart2);
        storeServ.saveCustomer(cust2);
        LogInRequestDTO req2 = new LogInRequestDTO("Jessica123", "jess00");
        MvcResult result2 = mvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(req2))).andReturn();
        JSONObject tokenJSON2 = new JSONObject(result2.getResponse().getContentAsString());
        token2 = tokenJSON2.getString("token");
    }

    @Test
    @Order(1)
    void givenUserWithCartFull_createsOrder() throws Exception {
        // Bob has items in his cart
        mvc.perform(get("/api/cart").header("Authorization", "Bearer "+token1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$."+w1.getId(), is(5)))
        .andExpect(jsonPath("$."+w2.getId(), is(3)));

        // and so does Jess
        mvc.perform(get("/api/cart").header("Authorization", "Bearer "+token2))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$."+w1.getId(), is(1)))
        .andExpect(jsonPath("$."+w2.getId(), is(2)))
        .andExpect(jsonPath("$."+w3.getId(), is(3)));

        // Initital stock check for wines
        mvc.perform(get("/api/wines/"+w1.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.stock", is(12)));

        mvc.perform(get("/api/wines/"+w2.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.stock", is(6)));

        mvc.perform(get("/api/wines/"+w3.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.stock", is(8)));

        // Bob creates a new order, recieving the proper order instance back
        OrderCreationDTO newOrder = new OrderCreationDTO("exampleAddress", "some details", "989898989");
        mvc.perform(post("/api/orders").header("Authorization", "Bearer "+token1)
        .contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(newOrder)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.order.orderStatus", is("created")))
        .andExpect(jsonPath("$.order.deliveryAddress", is("exampleAddress")))
        .andExpect(jsonPath("$.order.orderDetails", is("some details")))
        .andExpect(jsonPath("$.order.phone", is("989898989")))
        .andExpect(jsonPath("$.customer.name", is("Bob")))
        .andExpect(jsonPath("$.customer.username", is("BobPancakes123")))
        .andExpect(jsonPath("$.customer.phone", is("919191919")))
        .andExpect(jsonPath("$.customer.password").doesNotExist())
        .andExpect(jsonPath("$.cart."+w1.getId(), is(5)))
        .andExpect(jsonPath("$.cart."+w2.getId(), is(3)));
    }

    @Test
    @Order(2)
    void afterOrder_stockReduces() throws Exception {
        mvc.perform(get("/api/wines/"+w1.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.stock", is(7)));

        mvc.perform(get("/api/wines/"+w2.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.stock", is(3)));

        mvc.perform(get("/api/wines/"+w3.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.stock", is(8)));
    }

    @Test
    @Order(3)
    void afterOrder_wineDisappearsFromOtherUserCarts() throws Exception {
        mvc.perform(get("/api/cart").header("Authorization", "Bearer "+token2))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$."+w1.getId()).doesNotExist())
        .andExpect(jsonPath("$."+w2.getId()).doesNotExist())
        .andExpect(jsonPath("$."+w3.getId(), is(3)));
    }

    @Test
    @Order(4)
    void withCartEmpty_cantOrderAnything() throws Exception {
        mvc.perform(get("/api/cart").header("Authorization", "Bearer "+token1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());

        OrderCreationDTO newOrder2 = new OrderCreationDTO("exampleAddress2", "some other details", "989898989");
        mvc.perform(post("/api/orders").header("Authorization", "Bearer "+token1)
        .contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(newOrder2)))
        .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    void withManyUsersOrders_gettingOrders_returnsOnlyUserOrders() throws Exception {
        OrderCreationDTO newOrder3 = new OrderCreationDTO("exampleAddress3", "some other other details", "999999999");
        mvc.perform(post("/api/orders").header("Authorization", "Bearer "+token2)
        .contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(newOrder3)))
        .andExpect(status().isOk());

        mvc.perform(get("/api/orders").header("Authorization", "Bearer "+token1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].customer.username", is("BobPancakes123")));
    }

    @Test
    @Order(6)
    void whenGettingSpecificOrder_getSpecificOrder() throws Exception {
        mvc.perform(get("/api/orders/1").header("Authorization", "Bearer "+token1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @Order(7)
    void whenGettingOtherUsersOrder_Unauthorized() throws Exception {
        mvc.perform(get("/api/orders/2").header("Authorization", "Bearer "+token1))
        .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(7)
    void whenGettingUnexistentOrder_notFound() throws Exception {
        mvc.perform(get("/api/orders/1234").header("Authorization", "Bearer "+token1))
        .andExpect(status().isNotFound());
    }
    
}
