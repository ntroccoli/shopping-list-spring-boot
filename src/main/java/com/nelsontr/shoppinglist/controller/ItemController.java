package com.nelsontr.shoppinglist.controller;

import com.nelsontr.shoppinglist.exception.ItemNotFoundException;
import com.nelsontr.shoppinglist.model.Item;
import com.nelsontr.shoppinglist.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemService.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item createItem(@RequestBody Item item) {
        return itemService.save(item);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item item) {
        Item existingItem = itemService.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
        item.setId(id);
        return itemService.save(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        Item item = itemService.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
        itemService.deleteById(id);
    }
}
