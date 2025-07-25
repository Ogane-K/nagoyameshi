package com.example.nagoyameshi.controller;

import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.security.UserDetailsImpl;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal @Nullable UserDetailsImpl userDetailsImpl) {
        if (userDetailsImpl != null) {
            return "redirect:/";
        }

        return "gitauth/login";
    }
}
