package com.example.nagoyameshi.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.UserRole;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.repository.UserRoleRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 引数に入力されたものと一致するユーザーをテーブルから取得し、そこからユーザーネームの取得
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("メールアドレスが存在しません" + email));

        List<UserRole> userRoles = userRoleRepository.findByUser(user);

        List<GrantedAuthority> authorities = userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getName()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(user, authorities);

    }

}
