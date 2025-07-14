package com.example.nagoyameshi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                .authorizeHttpRequests((requests) -> requests
                                                .requestMatchers("/", "/login", "/css/**", "/js/**", "/resources/**",
                                                                "/images/**",
                                                                "/storage/**", "/signup/**", "/verify/**")
                                                .permitAll()
                                                .requestMatchers("/api/stripe/**", "/webhook/**").permitAll()
                                                .requestMatchers("/restaurants/*/reviews/**")
                                                .hasAnyRole("PREMIUM_MEMBER", "SUPER_PREMIUM_MEMBER", "FREE_MEMBER")
                                                .requestMatchers("/restaurants/**")
                                                .access((authz, context) -> {
                                                        var authResult = authz.get().getAuthorities().stream()
                                                                        .noneMatch(a -> a.getAuthority()
                                                                                        .equals("ROLE_ADMIN"));// 管理者のみアクセス禁止
                                                        return new AuthorizationDecision(authResult);
                                                })
                                                .requestMatchers("/admin/**").hasRole("ADMIN") // 全ユーザーにアクセスを許可するURL
                                                .anyRequest().authenticated() // 上記以外のURLはログインが必要（ロールを問わない）
                                )
                                .formLogin((form) -> form
                                                .loginPage("/login") // ログインページのURL
                                                .loginProcessingUrl("/login") // ログインフォームの送信先URL
                                                .defaultSuccessUrl("/?loggedIn") // ログイン成功時のリダイレクト先URL
                                                .failureUrl("/login?error") // ログイン失敗時のリダイレクト先URL
                                                .permitAll())
                                .logout((logout) -> logout
                                                .logoutSuccessUrl("/?loggedOut") // ログアウト時のリダイレクト先URL
                                                .permitAll())
                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers("/api/stripe/**", "/webhook/**"));

                return http.build();
        }

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

}
