package com.coding.qs_practical.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coding.qs_practical.entity.GroceryItem;

import java.util.List;
import java.util.Set;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {

    List<GroceryItem> findByIdIn(Set<Long> groceryItemIds);
}
