package com.tqs.chateauduvin.IT;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

import com.tqs.chateauduvin.ChateauduvinApplication;

import com.tqs.chateauduvin.model.Wine;
import com.tqs.chateauduvin.repository.WineRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = ChateauduvinApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase
public class WineFiltersTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private WineRepository wineRepository;

    @AfterEach
    public void resetDb() {
        wineRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        Wine w1 = new Wine("1", 1.0, "A;B;C", 1.99, 10);
        Wine w2 = new Wine("2", 2.0, "A", 2.99, 10);
        Wine w3 = new Wine("3", 3.0, "B;C", 3.99, 10);
        Wine w4 = new Wine("4", 4.0, "A;C", 4.99, 10);
        Wine w5 = new Wine("5", 5.0, "C", 5.99, 10);
        Wine w6 = new Wine("6", 6.0, "B", 6.99, 10);
        Wine w7 = new Wine("7", 7.0, "A;B;C", 7.99, 10);
        Wine w8 = new Wine("8", 8.0, "A;C", 8.99, 10);
        Wine w9 = new Wine("9", 9.0, "C", 9.99, 10);
        Wine w10 = new Wine("10", 10.0, "A", 10.99, 10);
        Wine w11 = new Wine("11", 11.0, "A;B", 11.99, 10);
        Wine w12 = new Wine("12", 12.0, "C", 12.99, 10);
        Wine w13 = new Wine("13", 13.0, "C", 13.99, 10);
        Wine w14 = new Wine("14", 14.0, "B;C", 14.99, 10);

        wineRepository.save(w1);
        wineRepository.save(w2);
        wineRepository.save(w3);
        wineRepository.save(w4);
        wineRepository.save(w5);
        wineRepository.save(w6);
        wineRepository.save(w7);
        wineRepository.save(w8);
        wineRepository.save(w9);
        wineRepository.save(w10);
        wineRepository.save(w11);
        wineRepository.save(w12);
        wineRepository.save(w13);
        wineRepository.save(w14);

    }

    @Test
    void priceFiltering() throws Exception {
        mvc.perform(get("/api/wines?minPrice=10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(5)))
            .andExpect(jsonPath("$.content[*].name", hasItems("10","11","12","13","14")));

        mvc.perform(get("/api/wines?maxPrice=4"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(3)))
            .andExpect(jsonPath("$.content[*].name", hasItems("1","2","3")));

        mvc.perform(get("/api/wines?maxPrice=10&minPrice=7"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(3)))
            .andExpect(jsonPath("$.content[*].name", hasItems("7","8","9")));
    }

    @Test
    void alcoholFiltering() throws Exception {
        mvc.perform(get("/api/wines?minAlc=10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(5)))
            .andExpect(jsonPath("$.content[*].name", hasItems("10","11","12","13","14")));

        mvc.perform(get("/api/wines?maxAlc=4"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(4)))
            .andExpect(jsonPath("$.content[*].name", hasItems("1","2","3","4")));

        mvc.perform(get("/api/wines?maxAlc=10&minAlc=7"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(4)))
            .andExpect(jsonPath("$.content[*].name", hasItems("7","8","9","10")));
    }

    @Test
    void typeFiltering() throws Exception {
        mvc.perform(get("/api/wines?type=B"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(6)))
            .andExpect(jsonPath("$.content[*].name", hasItems("1","3","6","7","11","14")));
    }

    @Test
    void pagination() throws Exception {
        mvc.perform(get("/api/wines?page=0"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(8)))
            .andExpect(jsonPath("$.totalPages", is(2)));

        mvc.perform(get("/api/wines?page=1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(6)))
            .andExpect(jsonPath("$.totalPages", is(2)));
    }

    @Test
    void allFilters() throws Exception {
        mvc.perform(get("/api/wines?maxAlc=10&minAlc=7&maxPrice=13&minPrice=8&type=A"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(2)))
            .andExpect(jsonPath("$.content[*].name", hasItems("8","10")));
    }


}
