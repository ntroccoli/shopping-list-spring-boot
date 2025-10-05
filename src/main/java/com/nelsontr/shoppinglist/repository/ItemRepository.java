package com.nelsontr.shoppinglist.repository;

import com.nelsontr.shoppinglist.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}