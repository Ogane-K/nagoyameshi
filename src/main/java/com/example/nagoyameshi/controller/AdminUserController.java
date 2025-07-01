package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.UserRole;
import com.example.nagoyameshi.exception.UserNotFoundException;
import com.example.nagoyameshi.service.UserRoleService;
import com.example.nagoyameshi.service.UserService;

@Controller
public class AdminUserController {
    private final UserService userService;
    private final UserRoleService userRoleService;

    public AdminUserController(UserService userService,UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    // 管理者用ユーザー一覧画面
    @GetMapping("/admin/users")
    public String index(@RequestParam(value = "keyword", required = false) String keyword,
            @PageableDefault(page = 0, size = 15, sort = "id", direction = Direction.ASC) Pageable pageable,
            Model model) {

        Page<User> userPage;

        // キーワードが存在したら、キーワード検索する ソレ以外は全ユーザーを取得
        if (keyword == null || keyword.isEmpty()) {
            userPage = userService.findAllUsers(pageable);
        } else {
            userPage = userService.seachUsersByNameOrFurigana(keyword, pageable);
        }

        model.addAttribute("userPage", userPage);
        model.addAttribute("keyword", keyword);

        return "admin/users/index";
    }

    @GetMapping("/admin/users/{id}")
    public String show(@PathVariable(value = "id") Integer id,
            RedirectAttributes redirectAttributes,
            Model model) {

        User user;
        try {
            user = userService.findByUserById(id);
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "ユーザーが存在しません。");
            return "redirect:/admin/users";
        }

        // ロール情報を取得
        List<UserRole> roles = userRoleService.findByUser(user);

        model.addAttribute("roles", roles);
        model.addAttribute("user", user);

        return "admin/users/show";
    }

}
