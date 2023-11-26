package com.coding.qs_practical.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.qs_practical.entity.GroceryItem;
import com.coding.qs_practical.exception.NotFoundException;
import com.coding.qs_practical.repository.GroceryItemRepository;

@Service
public class GroceryServiceImpl implements GroceryService {

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Override
    public List<GroceryItem> getAllGroceryItems() {
        return groceryItemRepository.findAll();
    }

    @Override
    public GroceryItem getGroceryItemById(Long groceryItemId) {
        return groceryItemRepository.findById(groceryItemId)
                .orElseThrow(() -> new NotFoundException("Grocery item not found with id: " + groceryItemId));
    }

    @Override
    public void addGroceryItem(GroceryItem groceryItem) {
        // Additional validation and business logic can be added here
        groceryItemRepository.save(groceryItem);
    }

    @Override
    public void removeGroceryItem(Long groceryItemId) {
        // Additional validation and business logic can be added here
        groceryItemRepository.deleteById(groceryItemId);
    }

    @Override
    public void updateGroceryItem(Long groceryItemId, GroceryItem updatedGroceryItem) {
        GroceryItem existingItem = getGroceryItemById(groceryItemId);
        existingItem.setName(updatedGroceryItem.getName());
        existingItem.setPrice(updatedGroceryItem.getPrice());
        existingItem.setInventory(updatedGroceryItem.getInventory());
        groceryItemRepository.save(existingItem);
    }

    @Override
    public List<GroceryItem> getGroceryItemsByIds(Set<Long> groceryItemIds) {
        return groceryItemRepository.findByIdIn(groceryItemIds);
    }

    @Override
    public void manageInventory(Long groceryItemId, int quantity) {
        // Additional validation and business logic can be added here
        GroceryItem existingItem = getGroceryItemById(groceryItemId);
        existingItem.setInventory(existingItem.getInventory() + quantity);
        groceryItemRepository.save(existingItem);
    }
}
