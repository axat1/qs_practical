package com.coding.qs_practical.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="grocery_item")
public class GroceryItem extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "inventory")
    private int inventory;
    
    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;
    

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
