package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.UserRole;
import com.example.nagoyameshi.entity.UserRoleId;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    // userに基づいて検索
    List<UserRole> findByUser(User user);

    public void deleteByUser(User user);

}
