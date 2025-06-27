package com.example.nagoyameshi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
