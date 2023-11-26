package com.coding.qs_practical.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.qs_practical.entity.GroceryItem;
import com.coding.qs_practical.exception.NotFoundException;
import com.coding.qs_practical.repository.GroceryItemRepository;

import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Override
    public void addGroceryItem(GroceryItem groceryItem) {
        groceryItemRepository.save(groceryItem);
    }

    @Override
    public List<GroceryItem> viewAllGroceryItems() {
        return groceryItemRepository.findAll();
    }

    @Override
    public void removeGroceryItem(Long itemId) {
        groceryItemRepository.deleteById(itemId);
    }

    @Override
    public void updateGroceryItemDetails(Long itemId, GroceryItem updatedItem) {
        if (groceryItemRepository.existsById(itemId)) {
        	GroceryItem item = groceryItemRepository.getById(itemId);
        	item.setCategory((updatedItem.getCategory()!=null && !updatedItem.getCategory().equals(""))
        			?updatedItem.getCategory():item.getCategory());
        	item.setDescription((updatedItem.getDescription()!=null && !updatedItem.getDescription().equals(""))
        			? updatedItem.getDescription() : item.getDescription());
        	item.setPrice(updatedItem.getPrice()!=0.0
        			? updatedItem.getPrice() : item.getPrice());
        	item.setName((updatedItem.getName() != null && !updatedItem.getName().equals(""))
        			? updatedItem.getName(): item.getName());
        	item.setInventory(updatedItem.getInventory());
        	item.setLastUpdatedDate(new Date());
            groceryItemRepository.save(item);
        } else {
            throw new NotFoundException("Grocery item not found with id: " + itemId);
        }
    }

    @Override
    public void manageInventory(Long itemId, int quantity) {
        GroceryItem groceryItem = groceryItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Grocery item not found with id: " + itemId));

        // Additional logic for managing inventory, e.g., updating quantity
        groceryItem.setInventory(groceryItem.getInventory() + quantity);

        groceryItemRepository.save(groceryItem);
    }
}
