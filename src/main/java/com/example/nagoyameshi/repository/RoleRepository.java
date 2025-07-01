package com.example.nagoyameshi.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    // Idに基づいて検索
    Optional<Role> findById(Integer id);

    // nameに基づいて検索
    Optional<Role> findByName(String name);

    

}
