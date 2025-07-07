package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.service.CategoryService;
import com.example.nagoyameshi.service.RestaurantSerchService;

@Controller
public class RestaurantController {

    private final RestaurantSerchService restaurantSerchService;
    private final CategoryService categoryService;

    public RestaurantController(RestaurantSerchService restaurantSerchService,
            CategoryService categoryService) {
        this.restaurantSerchService = restaurantSerchService;
        this.categoryService = categoryService;
    }

    @GetMapping("/restaurants")
    public String index(@RequestParam(value = "", required = false) String keyword,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "price", required = false) Integer price,
            @RequestParam(value = "order", required = false, defaultValue = "createdAtDesc") String order,
            @PageableDefault(page = 0, size = 15, direction = Direction.ASC) Pageable pageable,
            Model model) {

        // 店舗一覧の雛形
        Page<Restaurant> restaurantPage;
        if (keyword != null && !keyword.isBlank()) {
            // キーワード検索
            if ("lowestPriceAsc".equals(order)) {
                restaurantPage = restaurantSerchService.findRestaurantsByKeywordOrderByLowestPriceAsc(keyword,
                        pageable);
            } else {
                restaurantPage = restaurantSerchService.findRestaurantsByKeywordOrderByCreatedAtDesc(keyword,
                        pageable);
            }
        } else if (categoryId != null) {
            // カテゴリID検索
            if ("lowestPriceAsc".equals(order)) {
                restaurantPage = restaurantSerchService.findRestaurantsByCategoryIdOrderByLowestPriceAsc(categoryId,
                        pageable);
            } else {
                restaurantPage = restaurantSerchService.findRestaurantsByCategoryIdOrderByCreatedAtDesc(categoryId,
                        pageable);
            }
        } else if (price != null) {
            // 予算以下検索
            if ("lowestPriceAsc".equals(order)) {
                restaurantPage = restaurantSerchService.findRestaurantsByMaxPriceOrderByLowestPriceAsc(price,
                        pageable);
            } else {
                restaurantPage = restaurantSerchService.findRestaurantsByMaxPriceOrderByCreatedAtDesc(price, pageable);
            }
        } else {
            // 条件なし（全件）
            if ("lowestPriceAsc".equals(order)) {
                restaurantPage = restaurantSerchService.findAllOrderByLowestPriceAsc(pageable);
            } else {
                restaurantPage = restaurantSerchService.findAllOrderByCreatedAtDesc(pageable);
            }
        }

        model.addAttribute("restaurantPage", restaurantPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("price", price);
        model.addAttribute("order", order);
        model.addAttribute("categories", categoryService.findAllCategoriesList());

        return "restaurants/index";

    }
}