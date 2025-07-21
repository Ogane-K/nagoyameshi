package com.example.nagoyameshi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.RestaurantCategory;
import com.example.nagoyameshi.entity.RestaurantCategoryId;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.RestaurantCategoryRepository;

@Service
public class RestaurantCategoryService {

    private final RestaurantCategoryRepository restaurantCategoryRepository;
    private final CategoryRepository categoryRepository;

    public RestaurantCategoryService(RestaurantCategoryRepository restaurantCategoryRepository,
            CategoryRepository categoryRepository) {
        this.restaurantCategoryRepository = restaurantCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    // カテゴリーと店舗の関係を保存する
    @Transactional
    public void updateCategories(Restaurant restaurant, List<Integer> ids) {

        // 古いカテゴリーと店舗の関係を全削除
        restaurantCategoryRepository.deleteByRestaurant(restaurant);

        if (ids != null) {

            // リストの中のカテゴリーidと店舗との関係を保存する
            for (Integer categoryId : ids) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("カテゴリーが見つかりません"));

                // 対応関係の雛形を作成
                RestaurantCategory restaurantCategory = new RestaurantCategory();

                // 対応関係のIdを作成
                RestaurantCategoryId restaurantCategoryId = new RestaurantCategoryId();

                restaurantCategoryId.setCategoryId(category.getId());
                restaurantCategoryId.setRestaurantId(restaurant.getId());

                restaurantCategory.setId(restaurantCategoryId);
                restaurantCategory.setRestaurant(restaurant);
                restaurantCategory.setCategory(category);

                restaurantCategoryRepository.save(restaurantCategory);

            }
        }

    }

    // 店舗と紐づくカテゴリーを検索する
    public List<Category> findCategoriesByRestaurant(Restaurant restaurant) {
        return restaurantCategoryRepository.findCategoriesByRestaurant(restaurant);
    }

}
