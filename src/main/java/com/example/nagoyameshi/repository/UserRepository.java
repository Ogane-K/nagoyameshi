package com.example.nagoyameshi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    // idに基づいて検索
    Optional<User> findById(Integer id);

    // emailに基づいて検索
    Optional<User> findByEmail(String email);

    // Emailで検索して、存在するならtrue ないならfalse
    boolean existsByEmail(String email);
}
