package com.example.nagoyameshi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Holiday;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.exception.FavoriteNotFoundException;
import com.example.nagoyameshi.exception.RestaurantNotFoundException;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.CategoryService;
import com.example.nagoyameshi.service.FavoriteService;
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
    private final FavoriteService favoriteService;

    public RestaurantController(RestaurantSerchService restaurantSerchService,
            CategoryService categoryService,
            RestaurantService restaurantService,
            HolidayService holidayService,
            RestaurantCategoryService restaurantCategoryService,
            FavoriteService favoriteService) {
        this.restaurantSerchService = restaurantSerchService;
        this.categoryService = categoryService;
        this.restaurantService = restaurantService;
        this.holidayService = holidayService;
        this.restaurantCategoryService = restaurantCategoryService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/restaurants")
    public String index(@RequestParam(value = "", required = false) String keyword,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "price", required = false) Integer price,
            @RequestParam(value = "order", required = false, defaultValue = "createdAtDesc") String order,
            @PageableDefault(page = 0, size = 15, sort = "id", direction = Direction.ASC) Pageable pageable,
            Model model) {

        // 店舗一覧の雛形
        Page<Restaurant> restaurantPage;

        // 検索if分岐の構造
        // 大項目 キーワード検索orカテゴリーid検索or指定した価格以下検索
        // 小項目 並び順の指定

        if (keyword != null && !keyword.isBlank()) {
            // キーワード検索
            if ("lowestPriceAsc".equals(order)) {
                restaurantPage = restaurantSerchService.findRestaurantsByKeywordOrderByLowestPriceAsc(keyword,
                        pageable);
            } else if ("popularDesc".equals(order)) {
                restaurantPage = restaurantSerchService.findRestaurantsByKeywordOrderByReservationCount(keyword,
                        pageable);
            } else if ("ratingDesc".equals(order)) {
                restaurantPage = restaurantSerchService.serchByKeywordsOrderByRating(keyword, pageable);
            } else {
                restaurantPage = restaurantSerchService.findRestaurantsByKeywordOrderByCreatedAtDesc(keyword,
                        pageable);
            }
        } else if (categoryId != null) {
            // カテゴリID検索
            if ("lowestPriceAsc".equals(order)) {
                restaurantPage = restaurantSerchService.findRestaurantsByCategoryIdOrderByLowestPriceAsc(categoryId,
                        pageable);
            } else if ("popularDesc".equals(order)) {
                restaurantPage = restaurantSerchService
                        .findRestaurantsByCategoryIdOrderByReserVationCountDesc(categoryId, pageable);
            } else if ("ratingDesc".equals(order)) {
                restaurantPage = restaurantSerchService.findRestaurantsByCategoryIdOrderByRating(categoryId, pageable);
            } else {
                restaurantPage = restaurantSerchService.findRestaurantsByCategoryIdOrderByCreatedAtDesc(categoryId,
                        pageable);
            }
        } else if (price != null) {
            // 予算以下検索
            if ("lowestPriceAsc".equals(order)) {
                restaurantPage = restaurantSerchService.findRestaurantsByMaxPriceOrderByLowestPriceAsc(price,
                        pageable);
            } else if ("popularDesc".equals(order)) {
                restaurantPage = restaurantSerchService.findRestaurantsByLowestPriceOrderByResetbationCountDesc(price,
                        pageable);
            } else if ("ratingDesc".equals(order)) {
                restaurantPage = restaurantSerchService.findRestaurantsByLowestPriceLessThanEqualOrderByRating(price,
                        pageable);
            } else {
                restaurantPage = restaurantSerchService.findRestaurantsByMaxPriceOrderByCreatedAtDesc(price, pageable);
            }
        } else {
            // 条件なし（全件）
            if ("lowestPriceAsc".equals(order)) {
                restaurantPage = restaurantSerchService.findAllOrderByLowestPriceAsc(pageable);
            } else if ("popularDesc".equals(order)) {
                restaurantPage = restaurantSerchService.findAllRestaurantsByOrderByReservationCountDesc(pageable);
            } else if ("ratingDesc".equals(order)) {
                restaurantPage = restaurantSerchService.findAllRestaurantsByOrderByRatingDesc(pageable);
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
    public String show(@PathVariable(value = "id") Integer id,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            RedirectAttributes redirectAttributes,
            Model model) {

        // ログイン中のユーザー
        User user = userDetailsImpl.getUser();

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

        Favorite favorite = null;
        boolean isFavorite = favoriteService.isFavorite(user, restaurant);
        if (isFavorite) {
            try {
                favorite = favoriteService.findFavoriteByRestaurantAndUser(restaurant, user);
            } catch (FavoriteNotFoundException e) {
                System.err.println("(店舗概要ページ)" + e.getMessage());
            }
        }

        model.addAttribute("favorite", favorite);
        model.addAttribute("isFavorite", isFavorite);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("holidayList", holidayList);

        return "restaurants/show";
    }
}
