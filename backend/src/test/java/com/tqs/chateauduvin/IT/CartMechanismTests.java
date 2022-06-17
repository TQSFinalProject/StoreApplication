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

import com.tqs.chateauduvin.model.Customer;
import com.tqs.chateauduvin.model.LogInReq;
import com.tqs.chateauduvin.model.Wine;
import com.tqs.chateauduvin.repository.CustomerRepository;
import com.tqs.chateauduvin.repository.WineRepository;
import com.tqs.chateauduvin.service.StoreService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;  

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = ChateauduvinApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CartMechanismTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WineRepository wineRepository;

    @Autowired
    private StoreService storeServ;

    @AfterAll
    public void resetDb() {
        customerRepository.deleteAll();
    }

    String token1;
    String token2;

    @BeforeAll
    public void setUp() throws IOException, Exception {
        Wine w1 = new Wine("w1", 12.0, "dry;rose", 12.99, 12);
        Wine w2 = new Wine("w2", 12.0, "dry;white", 12.99, 3);
        wineRepository.save(w1);
        wineRepository.save(w2);

        Customer cust1 = new Customer("Bob", "919191919", "BobPancakes", "bobby99");
        storeServ.saveCustomer(cust1);
        LogInReq req1 = new LogInReq("BobPancakes", "bobby99");
        MvcResult result1 = mvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(req1)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").exists())
        .andReturn();
        JSONObject tokenJSON1 = new JSONObject(result1.getResponse().getContentAsString());
        token1 = tokenJSON1.getString("token");
    }

    @Test
    @Order(1) 
    public void givenProducts_whenUserAddToCart_isInCart() throws Exception {   
        mvc.perform(put("/api/cart/1?quantity=5").header("Authorization", "Bearer "+token1))
        .andExpect(status().isOk());

        Customer cust1  = storeServ.getCustomerByUsername("BobPancakes");
        Wine w1 = storeServ.getWineById(1L).get();
        assertEquals(5, cust1.getCart().get(w1.getId()));

        mvc.perform(get("/api/cart").header("Authorization", "Bearer "+token1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.1", is(5)));
    }

    @Test
    @Order(2) 
    public void givenProducts_whenNoStock_CantAdd() throws Exception {
        mvc.perform(put("/api/cart/2?quantity=5").header("Authorization", "Bearer "+token1))
        .andExpect(status().is(406));
    }

    @Test
    @Order(3) 
    public void givenUser_whenDeleteWineFromCart_thenCartCountReduces() throws Exception {
        mvc.perform(delete("/api/cart/1?quantity=2").header("Authorization", "Bearer "+token1))
        .andExpect(status().isOk());

        mvc.perform(get("/api/cart").header("Authorization", "Bearer "+token1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.1", is(3)));
    }

    @Test
    @Order(4) 
    public void givenUser_whenExcessDelete_thenNoWineInCart() throws Exception {
        mvc.perform(delete("/api/cart/1?quantity=100").header("Authorization", "Bearer "+token1))
        .andExpect(status().isOk());

        mvc.perform(get("/api/cart").header("Authorization", "Bearer "+token1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.1").doesNotExist());
    }
}
