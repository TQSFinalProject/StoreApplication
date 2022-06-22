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

import com.github.tomakehurst.wiremock.WireMockServer;
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
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;  

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = ChateauduvinApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderUpdateTests {
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

    private WireMockServer wireMockServer = new WireMockServer(8085);

    @AfterAll
    public void resetDb() {
        wireMockServer.stop();
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        wineRepository.deleteAll();
    }

    Customer cust2;

    String token1;
    String token2;

    String orderInstId;

    @BeforeAll
    void setUp() throws IOException, Exception {
        wireMockServer.start();

        Wine w1 = new Wine("w1", 12.0, "dry;rose", 12.99, 12);
        Wine w2 = new Wine("w2", 12.0, "dry;white", 12.99, 6);
        Wine w3 = new Wine("w3", 12.0, "red", 12.99, 8);
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
        MvcResult result1 = mvc.perform(post("/authentication").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(req1))).andReturn();
        JSONObject tokenJSON1 = new JSONObject(result1.getResponse().getContentAsString());
        token1 = tokenJSON1.getString("token");

        configureFor("localhost", 8085);

        String orderBody = "{" + 
        "\"id\":123" +
        "\"orderStatus\":\"requested\"" +
        "\"deliveryAddress\":\"exampleAddress\"" +
        "\"deliveryLat\":10.0" +
        "\"deliveryLong\":10.0" +
        "\"orderDetails\":\"some details\"" +
        "\"phone\":\"989898989\"" +
        "\"submitedTime\":\"2022-06-22T15:24:18\"" +
        "}";

        stubFor(post(urlEqualTo("/api/store/order")).willReturn(aResponse().withStatus(200).withBody(orderBody)));
        stubFor(post(urlEqualTo("/registration/store")).willReturn(aResponse().withStatus(200)));
        stubFor(post(urlEqualTo("/authentication")).willReturn(aResponse().withStatus(200).withBody("{\"token\":\"sometoken\"}")));

        OrderCreationDTO newOrder = new OrderCreationDTO("exampleAddress", 10.0, 10.0, "some details", "989898989");
        MvcResult idResult = mvc.perform(post("/api/orders").header("Authorization", "Bearer "+token1)
        .contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(newOrder))).andReturn();
        JSONObject orderInstJSON = new JSONObject(idResult.getResponse().getContentAsString());
        orderInstId = orderInstJSON.getString("id"); 

        cust2 = new Customer("Jess", "929292929", "Jessica123", "jess00"); 
        Map<Long,Integer> cart2 = new HashMap<>();
        cart2.put(w1.getId(), 1);
        cart2.put(w2.getId(), 2);
        cart2.put(w3.getId(), 3);
        cust2.setCart(cart2);
        storeServ.saveCustomer(cust2);
        LogInRequestDTO req2 = new LogInRequestDTO("Jessica123", "jess00");
        MvcResult result2 = mvc.perform(post("/authentication").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(req2))).andReturn();
        JSONObject tokenJSON2 = new JSONObject(result2.getResponse().getContentAsString());
        token2 = tokenJSON2.getString("token");
    }

    @Test
    @Order(1)
    void getOrderInfo() throws Exception {
        String orderBody = "{\"order\": {" + 
        "\"id\":123" +
        "\"orderStatus\":\"requested\"" +
        "\"deliveryAddress\":\"exampleAddress\"" +
        "\"deliveryLat\":10.0" +
        "\"deliveryLong\":10.0" +
        "\"orderDetails\":\"some details\"" +
        "\"phone\":\"989898989\"" +
        "\"submitedTime\":\"2022-06-22T15:24:18\"}" +
        "\"rider\": null" + 
        "}";

        stubFor(get(urlEqualTo("/api/store/order/123")).willReturn(aResponse().withStatus(200).withBody(orderBody)));
    
        mvc.perform(get("/api/orders/"+orderInstId).header("Authorization", "Bearer "+token1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.order.order.orderStatus", is("requested")))
        .andExpect(jsonPath("$.order.order.deliveryAddress", is("exampleAddress")))
        .andExpect(jsonPath("$.order.order.orderDetails", is("some details")))
        .andExpect(jsonPath("$.order.order.phone", is("989898989")))
        .andExpect(jsonPath("$.order.customer.name", is("Bob")))
        .andExpect(jsonPath("$.order.customer.username", is("BobPancakes123")))
        .andExpect(jsonPath("$.order.customer.phone", is("919191919")))
        .andExpect(jsonPath("$.order.customer.password").doesNotExist())
        .andExpect(jsonPath("$.order.mgmtOrderId", is(123)));
    }

    @Test
    @Order(2)
    void getOrderInfo_butHasNoPermission() throws Exception {
        mvc.perform(get("/api/orders/"+orderInstId).header("Authorization", "Bearer "+token2))
        .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(3)
    void getOrderInfo_afterOrderChanges() throws Exception {
        String orderBody = "{\"order\": {" + 
        "\"id\":123" +
        "\"orderStatus\":\"accepted\"" +
        "\"deliveryAddress\":\"exampleAddress\"" +
        "\"deliveryLat\":10.0" +
        "\"deliveryLong\":10.0" +
        "\"orderDetails\":\"some details\"" +
        "\"phone\":\"989898989\"" +
        "\"submitedTime\":\"2022-06-22T15:24:18\"}" +
        "\"rider\": null" + 
        "}";

        stubFor(get(urlEqualTo("/api/store/order/123")).willReturn(aResponse().withStatus(200).withBody(orderBody)));
    
        mvc.perform(get("/api/orders/"+orderInstId).header("Authorization", "Bearer "+token1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.order.order.orderStatus", is("accepted")))
        .andExpect(jsonPath("$.order.order.deliveryAddress", is("exampleAddress")))
        .andExpect(jsonPath("$.order.order.orderDetails", is("some details")))
        .andExpect(jsonPath("$.order.order.phone", is("989898989")))
        .andExpect(jsonPath("$.order.customer.name", is("Bob")))
        .andExpect(jsonPath("$.order.customer.username", is("BobPancakes123")))
        .andExpect(jsonPath("$.order.customer.phone", is("919191919")))
        .andExpect(jsonPath("$.order.customer.password").doesNotExist())
        .andExpect(jsonPath("$.order.mgmtOrderId", is(123)));
    }
    
}
