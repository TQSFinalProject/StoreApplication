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

import com.tqs.chateauduvin.ChateauduvinApplication;
import com.tqs.chateauduvin.JsonUtils;
import com.tqs.chateauduvin.dto.WineDTO;
import com.tqs.chateauduvin.model.Wine;
import com.tqs.chateauduvin.repository.WineRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.List; 

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = ChateauduvinApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class WineLogicTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private WineRepository wineRepository;

    @AfterEach
    public void resetDb() {
        wineRepository.deleteAll();
    }

    @Test
    void createWine() throws IOException, Exception {
        WineDTO newWine = new WineDTO("name", 12.5, "rose;sparkling", 12.99, 5);
        mvc.perform(post("/api/wines").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(newWine)))
        .andExpect(status().isOk());

        List<Wine> found = wineRepository.findAll();
        Wine wine = found.get(0);
        assertEquals("name", wine.getName());
        assertEquals(12.5, wine.getAlcohol());
        assertEquals("rose;sparkling", wine.getTypes());
        assertEquals(12.99, wine.getPrice());
        assertEquals(5, wine.getStock());
    }

    @Test
    void getWineByID() throws Exception {
        Wine wine = new Wine("A",12.5, "rose;sparkling", 12.99, 5);
        wineRepository.save(wine);

        mvc.perform(get("/api/wines/"+wine.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("A")))
        .andExpect(jsonPath("$.alcohol", is(12.5)))
        .andExpect(jsonPath("$.types", is("rose;sparkling")))
        .andExpect(jsonPath("$.price", is(12.99)))
        .andExpect(jsonPath("$.stock", is(5)));
    }

    @Test
    void getUnexistentWine() throws Exception {
        mvc.perform(get("/api/wines/1234"))
        .andExpect(status().isNotFound());
    }
}
