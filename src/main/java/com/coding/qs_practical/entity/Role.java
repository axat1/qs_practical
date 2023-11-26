package com.coding.qs_practical.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority{

	private static final long serialVersionUID = 1L;

	@Column(name = "name")
    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return this.name;
	}

}