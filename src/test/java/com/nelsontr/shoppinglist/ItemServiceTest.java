package com.nelsontr.shoppinglist;

import com.nelsontr.shoppinglist.dto.ItemRequestDto;
import com.nelsontr.shoppinglist.exception.ItemNotFoundException;
import com.nelsontr.shoppinglist.model.Item;
import com.nelsontr.shoppinglist.repository.ItemRepository;
import com.nelsontr.shoppinglist.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    private ItemRepository itemRepository;
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        itemService = new ItemService(itemRepository);
    }

    @Test
    void update_updatesExistingItem() {
        Item existing = new Item();
        existing.setName("Old");
        existing.setPrice(new BigDecimal("1.00"));
        existing.setQuantity(1);
        existing.setCategory("Cat");

        when(itemRepository.findById(42L)).thenReturn(Optional.of(existing));
        when(itemRepository.save(any(Item.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ItemRequestDto req = new ItemRequestDto();
        req.setName("Milk");
        req.setPrice(new BigDecimal("2.49"));
        req.setQuantity(2);
        req.setCategory("Dairy");

        Item updated = itemService.update(42L, req);

        assertEquals("Milk", updated.getName());
        assertEquals(new BigDecimal("2.49"), updated.getPrice());
        assertEquals(2, updated.getQuantity());
        assertEquals("Dairy", updated.getCategory());

        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository).save(captor.capture());
        assertEquals("Milk", captor.getValue().getName());
    }

    @Test
    void update_throwsWhenNotFound() {
        when(itemRepository.findById(99L)).thenReturn(Optional.empty());

        ItemRequestDto req = new ItemRequestDto();
        req.setName("X");
        req.setPrice(new BigDecimal("1.00"));
        req.setQuantity(1);
        req.setCategory("C");

        assertThrows(ItemNotFoundException.class, () -> itemService.update(99L, req));
        verify(itemRepository, never()).save(any());
    }
}

