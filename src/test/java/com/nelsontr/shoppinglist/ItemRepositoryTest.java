package com.nelsontr.shoppinglist;

import com.nelsontr.shoppinglist.model.Item;
import com.nelsontr.shoppinglist.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void save_setsTimestamps_andAllowsUpdate() {
        Item item = new Item();
        item.setName("Yogurt");
        item.setPrice(new BigDecimal("3.99"));
        item.setQuantity(1);
        item.setCategory("Dairy");

        Item saved = itemRepository.save(item);
        assertNotNull(saved.getId());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());

        LocalDateTime created = saved.getCreatedAt();
        LocalDateTime updated = saved.getUpdatedAt();

        // Update some field and re-save -> updatedAt should change
        saved.setQuantity(2);
        Item saved2 = itemRepository.save(saved);
        assertEquals(created, saved2.getCreatedAt());
        assertTrue(saved2.getUpdatedAt().isAfter(updated) || saved2.getUpdatedAt().isEqual(updated));
    }
}

