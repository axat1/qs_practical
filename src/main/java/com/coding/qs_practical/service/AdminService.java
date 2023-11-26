package com.coding.qs_practical.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coding.qs_practical.entity.GroceryItem;

public interface AdminService {
	void addGroceryItem(GroceryItem groceryItem);
    List<GroceryItem> viewAllGroceryItems();
    void removeGroceryItem(Long itemId);
    void updateGroceryItemDetails(Long itemId, GroceryItem updatedItem);
    void manageInventory(Long itemId, int quantity);
}
