package com.nelsontr.shoppinglist.mapper;

import com.nelsontr.shoppinglist.dto.ItemRequestDto;
import com.nelsontr.shoppinglist.dto.ItemResponseDto;
import com.nelsontr.shoppinglist.model.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item toEntity(ItemRequestDto dto) {
        if (dto == null) return null;
        Item item = new Item();
        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        item.setQuantity(dto.getQuantity());
        item.setCategory(dto.getCategory());
        return item;
    }

    public ItemResponseDto toResponse(Item entity) {
        if (entity == null) return null;
        ItemResponseDto res = new ItemResponseDto();
        res.setId(entity.getId());
        res.setName(entity.getName());
        res.setPrice(entity.getPrice());
        res.setQuantity(entity.getQuantity());
        res.setCategory(entity.getCategory());
        res.setCost(entity.getCost());
        res.setCreatedAt(entity.getCreatedAt());
        res.setUpdatedAt(entity.getUpdatedAt());
        return res;
    }
}
