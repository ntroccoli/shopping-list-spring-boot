package com.nelsontr.shoppinglist;

import com.nelsontr.shoppinglist.model.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ItemEntityTest {

    @Test
    void getCost_returnsProductOfPriceAndQuantity_whenBothPresent() {
        Item item = new Item();
        item.setPrice(new BigDecimal("2.50"));
        item.setQuantity(4);

        assertEquals(new BigDecimal("10.00"), item.getCost());
    }

    @Test
    void getCost_returnsNull_whenPriceIsNull() {
        Item item = new Item();
        item.setQuantity(3);

        assertNull(item.getCost());
    }

    @Test
    void getCost_returnsNull_whenQuantityIsNull() {
        Item item = new Item();
        item.setPrice(new BigDecimal("1.99"));

        assertNull(item.getCost());
    }
}

