package com.example.nagoyameshi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.UserRole;
import com.example.nagoyameshi.exception.RestaurantNotFoundException;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.CategoryService;
import com.example.nagoyameshi.service.RestaurantSerchService;
import com.example.nagoyameshi.service.RestaurantService;
import com.example.nagoyameshi.service.UserRoleService;

import jakarta.persistence.EntityNotFoundException;

@Controller
public class HomeController {

    private final RestaurantService restaurantService;
    private final CategoryService categoryService;
    private final RestaurantSerchService restaurantSerchService;
    private final UserRoleService userRoleService;

    public HomeController(RestaurantService restaurantService,
            CategoryService categoryService,
            RestaurantSerchService restaurantSerchService,
            UserRoleService userRoleService) {
        this.restaurantService = restaurantService;
        this.categoryService = categoryService;
        this.restaurantSerchService = restaurantSerchService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/")
    public String index(@AuthenticationPrincipal @Nullable UserDetailsImpl userDetailsImpl,
            RedirectAttributes redirectAttributes,
            Model model) {

        // ログイン中のユーザー情報と、そのロール権限を取得
        if (userDetailsImpl != null) {
            User user = userDetailsImpl.getUser();
            // ユーザーが管理者の場合は、管理者用のトップページにリダイレクトさせる
            Role nowRole = userRoleService.getRoleByUser(user);

            String redirectUrl = redirectIfAdmin(nowRole, redirectAttributes);
            if (redirectUrl != null) {
                return redirectUrl;
            }
        }

        // ビューに渡すオブジェクトの作成

        Page<Restaurant> highlyRatedRestaurants = null;
        Page<Restaurant> newRestaurant = Page.empty();

        List<Category> categories = new ArrayList<>();

        Category washoku = new Category();
        Category udon = new Category();
        Category don = new Category();
        Category ramen = new Category();
        Category oden = new Category();
        Category fried = new Category();

        Pageable pageable = PageRequest.of(0, 6);

        // 各要素の取得
        try {
            highlyRatedRestaurants = restaurantSerchService.findAllRestaurantsByOrderByRatingDesc(pageable);
            newRestaurant = restaurantService.findAllRestaurantsByOrderByCreatedAtDesc(pageable);

            categories = categoryService.findAllCategoriesList();

            washoku = categoryService.findCategoryByKeyword("和食");
            udon = categoryService.findCategoryByKeyword("うどん");
            don = categoryService.findCategoryByKeyword("丼物");
            ramen = categoryService.findCategoryByKeyword("ラーメン");
            oden = categoryService.findCategoryByKeyword("おでん");
            fried = categoryService.findCategoryByKeyword("揚げ物");

        } catch (RestaurantNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (EntityNotFoundException e) {
            System.err.println(e.getMessage());
        }

        model.addAttribute("highlyRatedRestaurants", highlyRatedRestaurants);
        model.addAttribute("newRestaurants", newRestaurant);
        model.addAttribute("categories", categories);

        model.addAttribute("washoku", washoku);
        model.addAttribute("udon", udon);
        model.addAttribute("don", don);
        model.addAttribute("ramen", ramen);
        model.addAttribute("oden", oden);
        model.addAttribute("fried", fried);

        return "index";
    }

    public String redirectIfAdmin(Role nowRole, RedirectAttributes redirectAttributes) {
        if (nowRole.getName().equals("ROLE_ADMIN")) {
            redirectAttributes.addFlashAttribute("errorMessage", "管理者はユーザー用のページにはアクセスの許可をされていません。");
            return "redirect:/admin";
        }
        return null;
    }

}
