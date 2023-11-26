package com.coding.qs_practical.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coding.qs_practical.dto.UserRegistrationDTO;
import com.coding.qs_practical.entity.GroceryItem;
import com.coding.qs_practical.entity.Order;
import com.coding.qs_practical.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserRegistrationDTO registrationDTO) {
        userService.registerUser(registrationDTO);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/grocery-items")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<GroceryItem>> viewAllGroceryItems() {
        List<GroceryItem> groceryItems = userService.viewGroceryItems();
        return ResponseEntity.ok(groceryItems);
    }

    @PostMapping("/place-order/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> placeOrder(@PathVariable Long userId,@RequestBody Set<Long> groceryItemIds) {
        userService.placeOrder(userId, groceryItemIds);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId) {
        List<Order> userOrders = userService.getUserOrders(userId);
        return ResponseEntity.ok(userOrders);
    }
}