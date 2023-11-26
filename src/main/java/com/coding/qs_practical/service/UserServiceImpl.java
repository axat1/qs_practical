package com.coding.qs_practical.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coding.qs_practical.dto.UserRegistrationDTO;
import com.coding.qs_practical.entity.GroceryItem;
import com.coding.qs_practical.entity.Order;
import com.coding.qs_practical.entity.Role;
import com.coding.qs_practical.entity.User;
import com.coding.qs_practical.entity.UserRole;
import com.coding.qs_practical.exception.NotFoundException;
import com.coding.qs_practical.repository.RoleRepository;
import com.coding.qs_practical.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{

    @Autowired
    private GroceryService groceryService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserRegistrationDTO registrationDTO) {
        User user = new User();
        user.setUsername(registrationDTO.getUsername());

        String encryptedPassword = passwordEncoder.encode(registrationDTO.getPassword());
        user.setPassword(encryptedPassword);

        Set<Role> roles = new HashSet<>();
        String roleName = (registrationDTO.getRole() != null) ? registrationDTO.getRole() : UserRole.USER.toString();
        Role userRole = null;
        try {
            userRole = roleRepository.findByName(roleName);
        }catch(NotFoundException e){
        	e.printStackTrace();
        }
//        Role role = new Role();
//        role.setName(UserRole.ROLE_USER.toString());
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
    }
    
    @Override
    public List<GroceryItem> viewGroceryItems() {
        return groceryService.getAllGroceryItems();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        User user = getUserById(userId);
        return user.getOrders();
    }

    @Override
    public void placeOrder(Long userId, Set<Long> groceryItemIds) {
        User user = getUserById(userId);
        List<GroceryItem> groceryItems = groceryService.getGroceryItemsByIds(groceryItemIds);
        
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setGroceryItems(new HashSet<>(groceryItems));

        // Add additional logic such as updating inventory, calculating total, etc.

        user.getOrders().add(order);
        userRepository.save(user);
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userDetail = userRepository.findByUsername(username); 
		  
        return userDetail.map(UserInfoDetails::new) 
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
    } 
}
