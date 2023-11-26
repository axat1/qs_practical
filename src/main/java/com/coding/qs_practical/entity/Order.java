package com.coding.qs_practical.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

	@ManyToOne
    @JoinColumn(name = "user_id")
	@JsonIgnoreProperties("orders")
    private User user;

	@ManyToMany
    @JoinTable(
        name = "order_grocery_items",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "grocery_item_id")
    )
    private Set<GroceryItem> groceryItems;
    
    @Column(name = "order_date")
    private Date orderDate;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<GroceryItem> getGroceryItems() {
		return groceryItems;
	}

	public void setGroceryItems(Set<GroceryItem> groceryItems) {
		this.groceryItems = groceryItems;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}



}