package com.coding.qs_practical.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import com.coding.qs_practical.dto.UserRegistrationDTO;
import com.coding.qs_practical.entity.GroceryItem;
import com.coding.qs_practical.entity.Order;
import com.coding.qs_practical.entity.User;

public interface UserService {
	
	List<GroceryItem> viewGroceryItems();
    User getUserById(Long userId);
    List<Order> getUserOrders(Long userId);
    void placeOrder(Long userId, Set<Long> groceryItemIds);
	void registerUser(UserRegistrationDTO registrationDTO);
	UserDetails loadUserByUsername(String username);
}
