package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.RestaurantCategory;
import com.example.nagoyameshi.entity.RestaurantCategoryId;

public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, RestaurantCategoryId> {

    // 指定して店舗に紐づくカテゴリーをリストとして受け取る
    @Query("SELECT rc.category FROM RestaurantCategory rc WHere rc.restaurant = :restaurant")
    public List<Category> findCategoriesByRestaurant(Restaurant restaurant);

    public void deleteByRestaurant(Restaurant restaurant);

}
