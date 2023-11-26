package com.coding.qs_practical.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coding.qs_practical.entity.GroceryItem;
import com.coding.qs_practical.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add-grocery-item")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addGroceryItem(@RequestBody GroceryItem groceryItem) {
        adminService.addGroceryItem(groceryItem);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/grocery-items")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GroceryItem>> viewAllGroceryItems() {
        List<GroceryItem> groceryItems = adminService.viewAllGroceryItems();
        return ResponseEntity.ok(groceryItems);
    }

    @DeleteMapping("/remove-grocery-item/{itemId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeGroceryItem(@PathVariable Long itemId) {
        adminService.removeGroceryItem(itemId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-grocery-item/{itemId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateGroceryItemDetails(@PathVariable Long itemId, @RequestBody GroceryItem updatedItem) {
        adminService.updateGroceryItemDetails(itemId, updatedItem);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/manage-inventory/{itemId}/{quantity}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> manageInventory(@PathVariable Long itemId, @PathVariable int quantity) {
        adminService.manageInventory(itemId, quantity);
        return ResponseEntity.ok().build();
    }
}