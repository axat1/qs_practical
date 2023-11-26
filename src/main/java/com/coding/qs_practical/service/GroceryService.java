package com.coding.qs_practical.service;

import java.util.List;
import java.util.Set;

import com.coding.qs_practical.entity.GroceryItem;

public interface GroceryService {

    List<GroceryItem> getAllGroceryItems();

    GroceryItem getGroceryItemById(Long groceryItemId);

    void addGroceryItem(GroceryItem groceryItem);

    void removeGroceryItem(Long groceryItemId);

    void updateGroceryItem(Long groceryItemId, GroceryItem updatedGroceryItem);

    List<GroceryItem> getGroceryItemsByIds(Set<Long> groceryItemIds);

    void manageInventory(Long groceryItemId, int quantity);
}
