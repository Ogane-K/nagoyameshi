package com.example.nagoyameshi.controller;

import java.util.ArrayList;
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

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Holiday;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.exception.RestaurantNotFoundException;
import com.example.nagoyameshi.service.CategoryService;
import com.example.nagoyameshi.service.HolidayService;
import com.example.nagoyameshi.service.RestaurantCategoryService;
import com.example.nagoyameshi.service.RestaurantSerchService;
import com.example.nagoyameshi.service.RestaurantService;

@Controller
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantSerchService restaurantSerchService;
    private final CategoryService categoryService;
    private final HolidayService holidayService;
    private final RestaurantCategoryService restaurantCategoryService;

    public RestaurantController(RestaurantSerchService restaurantSerchService,
            CategoryService categoryService,
            RestaurantService restaurantService,
            HolidayService holidayService,
            RestaurantCategoryService restaurantCategoryService) {
        this.restaurantSerchService = restaurantSerchService;
        this.categoryService = categoryService;
        this.restaurantService = restaurantService;
        this.holidayService = holidayService;
        this.restaurantCategoryService = restaurantCategoryService;
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

    // 店舗詳細情報の表示
    @GetMapping("/restaurants/{id}")
    public String getMethodName(@PathVariable(value = "id") Integer id,
            RedirectAttributes redirectAttributes,
            Model model) {

        // 店舗のデーター
        Restaurant restaurant = new Restaurant();
        // 店舗が所属しているカテゴリーのリスト
        List<Category> categoryList = new ArrayList<>();
        // 店舗に設定している休日情報のリスト
        List<Holiday> holidayList = new ArrayList<>();

        // ビューに送る店舗エンティティを用意
        try {
            restaurant = restaurantService.findRestaurantById(id);
            categoryList = restaurantCategoryService.findCategoriesByRestaurant(restaurant);
            holidayList = holidayService.findHolidaysByRestaurant(restaurant);
        } catch (RestaurantNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "店舗が存在しません。");
            System.err.println(e.getMessage());

            return "redirect:/restaurants";
        }

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("holidayList", holidayList);

        return "restaurants/show";
    }

}