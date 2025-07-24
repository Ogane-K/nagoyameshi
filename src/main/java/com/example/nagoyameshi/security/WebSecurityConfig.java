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

        private final CustomAuthenticationFailureHandler customFailureHandler;

        public WebSecurityConfig(CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
                this.customFailureHandler = customAuthenticationFailureHandler;
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                .authorizeHttpRequests((requests) -> requests

                                                // だれでもアクセス許可のエリア
                                                .requestMatchers("/", "/login", "/css/**", "/js/**", "/resources/**",
                                                                "/images/**", "/storage/**", "/signup/**", "/verify/**")
                                                .permitAll()
                                                // API受取用の設定エリア
                                                .requestMatchers("/api/stripe/**", "/webhook/**").permitAll()
                                                .requestMatchers("/webhook/stripe").permitAll()
                                                // 会員のみアクセス許可のエリア
                                                .requestMatchers("/restaurants/*/reviews/**", "/favorites/**",
                                                                "/restaurants/{restaurantId}/favorites/**")
                                                .hasAnyRole("PREMIUM_MEMBER", "SUPER_PREMIUM_MEMBER", "FREE_MEMBER")
                                                // 管理者のみアクセス禁止のエリア(非会員/会員/有料会員OK)
                                                .requestMatchers("/restaurants/**", "/company", "/terms")
                                                .access((authz, context) -> {
                                                        var authResult = authz.get().getAuthorities().stream()
                                                                        .noneMatch(a -> a.getAuthority()
                                                                                        .equals("ROLE_ADMIN"));
                                                        return new AuthorizationDecision(authResult);
                                                })
                                                // 管理者のみアクセス許可のエリア
                                                .requestMatchers("/admin/**").hasRole("ADMIN")

                                                .anyRequest().authenticated() // 上記以外のURLはログインが必要（ロールを問わない）
                                )
                                .formLogin((form) -> form
                                                .loginPage("/login") // ログインページのURL
                                                .loginProcessingUrl("/login") // ログインフォームの送信先URL
                                                .defaultSuccessUrl("/?loggedIn") // ログイン成功時のリダイレクト先URL
                                                .failureHandler(customFailureHandler)
                                                .permitAll())
                                .logout((logout) -> logout
                                                .logoutSuccessUrl("/?loggedOut") // ログアウト時のリダイレクト先URL
                                                .permitAll())
                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers("/api/stripe/**", "/webhook/**",
                                                                "/webhook/stripe"));

                return http.build();
        }

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

}
