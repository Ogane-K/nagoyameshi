package com.example.nagoyameshi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.mapper.UserMapper;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            Model model) {

        User loginUser = userDetailsImpl.getUser();

        User user = userService.findByUserById(loginUser.getId());

        model.addAttribute("user", user);
        return "user/index";
    }

    @GetMapping("/user/edit")
    public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @ModelAttribute UserEditForm userEditForm,
            Model model) {

        userEditForm = UserMapper.mapToForm(userService.findByUserById(userDetailsImpl.getUser().getId()));

        model.addAttribute("userEditForm", userEditForm);
        return "user/edit";
    }

    @PostMapping("/user/update")
    public String update(@ModelAttribute @Validated UserEditForm editForm,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            RedirectAttributes redirectAttributes,
            Model model) {

        // バリデーションチェック
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }

        // 更新の成功判定用変数
        boolean success;

        // 更新を行う
        try {
            success = userService.updateUser(editForm, userDetailsImpl.getUser().getId());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "ユーザー情報の更新に失敗しました。");
            return "redirect:/user";
        }

        // 既存の他アカウントのメールアドレスを入力していた場合
        if (!success) {
            bindingResult.rejectValue("email", "duplicate", "このメールアドレスはすでに使われています。");
            return "user/edit";
        }

        redirectAttributes.addFlashAttribute("successMessage", "会員情報を編集しました。");
        return "redirect:/user";
    }

}
