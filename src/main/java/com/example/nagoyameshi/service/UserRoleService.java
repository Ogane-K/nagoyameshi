package com.example.nagoyameshi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.UserRole;
import com.example.nagoyameshi.repository.UserRoleRepository;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public List<UserRole> findByUser(User user) {
        return userRoleRepository.findByUser(user);
    }

    // 現在該当のユーザーのロールを返す
    public Role getRoleByUser(User user) {
        UserRole userRole = findByUser(user).get(0);

        return userRole.getRole();
    }

}
