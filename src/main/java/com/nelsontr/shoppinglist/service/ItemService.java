package com.nelsontr.shoppinglist.service;

import com.nelsontr.shoppinglist.dto.ItemRequestDto;
import com.nelsontr.shoppinglist.exception.ItemNotFoundException;
import com.nelsontr.shoppinglist.model.Item;
import com.nelsontr.shoppinglist.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    public Item update(Long id, ItemRequestDto request) {
        Item existing = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
        existing.setName(request.getName());
        existing.setPrice(request.getPrice());
        existing.setQuantity(request.getQuantity());
        existing.setCategory(request.getCategory());
        return itemRepository.save(existing);
    }
}