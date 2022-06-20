package com.tqs.chateauduvin.unit;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.tqs.chateauduvin.model.Order;

public class OrderTests {
    
    @Test
    public void testOrderEquals() {
        Order order1 = new Order("A", "B", 10.0, 10.0, LocalDateTime.of(2001, 2, 27, 0, 0), LocalDateTime.of(2002, 2, 27, 0, 0), LocalDateTime.of(2003, 2, 27, 0, 0), 1L, 1L, "C", "D", 2.0);
        order1.setId(2L);
        Order order2 = new Order("A", "B", 10.0, 10.0, LocalDateTime.of(2001, 2, 27, 0, 0), LocalDateTime.of(2002, 2, 27, 0, 0), LocalDateTime.of(2003, 2, 27, 0, 0), 1L, 1L, "C", "D", 2.0);
        order2.setId(1L);
        Order order3 = new Order("B", "B", 10.0, 10.0, LocalDateTime.of(2001, 2, 27, 0, 0), LocalDateTime.of(2002, 2, 27, 0, 0), LocalDateTime.of(2003, 2, 27, 0, 0), 1L, 1L, "C", "D", 2.0);
    
        // Order 1 and 2 are identical except for the id
        System.out.println(order1.getId() + " " + order2.getId());
        assertFalse(order1.getId() == order2.getId());

        // But they are still equal
        assertTrue(order1.equals(order2));;

        // However, order 1 and 3 are not identical, therefore not equal
        assertFalse(order1.equals(order3));
    }

}
