package com.tqs.chateauduvin.IT;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
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
import com.tqs.chateauduvin.dto.CustomerCreationDTO;
import com.tqs.chateauduvin.dto.LogInRequestDTO;
import com.tqs.chateauduvin.model.Customer;
import com.tqs.chateauduvin.repository.CustomerRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.List;
import org.json.JSONObject;   

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = ChateauduvinApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AuthenticationTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    public void resetDb() {
        customerRepository.deleteAll();
    }

    @Test
    void whenRegister_thenSuccessfullAuth() throws IOException, Exception {
        CustomerCreationDTO cust = new CustomerCreationDTO("Bob", "919191919", "BobPancakes", "pancake123");

        mvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(cust)))
        .andExpect(status().isOk());

        // User was saved
        List<Customer> found = customerRepository.findAll();
        assertThat(found).extracting(Customer::getName).containsOnly("Bob");

        // Password was not stored in plaintext
        assertNotEquals(cust.getPassword(), found.get(0).getPassword());

        // User can successfully authenticate, recieving a token
        LogInRequestDTO credentials = new LogInRequestDTO(cust.getUsername(), cust.getPassword());
        MvcResult result = mvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(credentials)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").exists())
        .andReturn();

        // Test given token on endpoint requiring auth
        JSONObject tokenJSON = new JSONObject(result.getResponse().getContentAsString());
        String token = tokenJSON.getString("token");
        mvc.perform(get("/myprofile").header("Authorization", "Bearer "+token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", is("BobPancakes")));
    }

    @Test
    void whenAuthenticateWithNoCreds_thenUnsuccessfulAuth() throws IOException, Exception {
        LogInRequestDTO unregisteredCredentials = new LogInRequestDTO("not", "registered");

        mvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(unregisteredCredentials)))
        .andExpect(status().is(500))
        .andExpect(jsonPath("$.message", is("Bad credentials")));
    }

    @Test
    void whenRegisterWithDuplicatedUsername_thenUnsuccessfulRegister() throws IOException, Exception {
        CustomerCreationDTO cust1 = new CustomerCreationDTO("Bob", "919191919", "BobPancakes", "pancake123");
        CustomerCreationDTO cust2 = new CustomerCreationDTO("Rita","929292929","BobPancakes","rita");

        mvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(cust1)))
        .andExpect(status().isOk());

        mvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(cust2)))
        .andExpect(status().is(409));

    }
}
