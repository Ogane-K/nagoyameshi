package com.example.nagoyameshi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.exception.RestaurantNotFoundException;
import com.example.nagoyameshi.service.CategoryService;
import com.example.nagoyameshi.service.RestaurantService;

import jakarta.persistence.EntityNotFoundException;

@Controller
public class HomeController {

    private final RestaurantService restaurantService;
    private final CategoryService categoryService;

    public HomeController(RestaurantService restaurantService,
            CategoryService categoryService) {
        this.restaurantService = restaurantService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index(Model model) {

        // ビューに渡すオブジェクトの作成

        List<Restaurant> highlyRatedRestaurants = new ArrayList<>();
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
            highlyRatedRestaurants = restaurantService.findRandom6Restaurants();
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

}
