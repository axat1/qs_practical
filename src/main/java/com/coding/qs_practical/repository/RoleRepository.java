package com.coding.qs_practical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coding.qs_practical.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(String roleName);
}
