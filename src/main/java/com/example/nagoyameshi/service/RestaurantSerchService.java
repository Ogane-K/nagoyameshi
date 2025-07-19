package com.example.nagoyameshi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.RestaurantCategoryRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;

@Service
public class RestaurantSerchService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantSerchService(RestaurantRepository restaurantRepository,
            CategoryRepository categoryRepository,
            RestaurantCategoryRepository restaurantCategoryRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // 1. 全件 × 新着順
    public Page<Restaurant> findAllOrderByCreatedAtDesc(Pageable pageable) {
        return restaurantRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    // 2. 全件 × 価格順
    public Page<Restaurant> findAllOrderByLowestPriceAsc(Pageable pageable) {
        return restaurantRepository.findAllByOrderByLowestPriceAsc(pageable);
    }

    // 3.キーワード×新着順
    public Page<Restaurant> findRestaurantsByKeywordOrderByCreatedAtDesc(String keyword, Pageable pageable) {
        return restaurantRepository.findByKeywordOrderByCreatedAtDesc("%" + keyword + "%", pageable);
    }

    // 4.キーワード×価格順
    public Page<Restaurant> findRestaurantsByKeywordOrderByLowestPriceAsc(String keyword, Pageable pageable) {
        return restaurantRepository.findByKeywordOrderByLowestPriceAsc("%" + keyword + "%", pageable);
    }

    // 5.カテゴリーid×新着順
    public Page<Restaurant> findRestaurantsByCategoryIdOrderByCreatedAtDesc(Integer id, Pageable pageable) {
        return restaurantRepository.findByCategoryIdOrderByCreatedAtDesc(id, pageable);
    }

    // 6.カテゴリーid×価格順
    public Page<Restaurant> findRestaurantsByCategoryIdOrderByLowestPriceAsc(Integer id, Pageable pageable) {
        return restaurantRepository.findByCategoryIdOrderByLowestPriceAsc(id, pageable);
    }

    // 7.指定価格以下×新着順
    public Page<Restaurant> findRestaurantsByMaxPriceOrderByCreatedAtDesc(Integer maxPrice, Pageable pageable) {
        return restaurantRepository.findByMaxPriceOrderByCreatedAtDesc(maxPrice, pageable);
    }

    // 8.指定価格以下×価格順
    public Page<Restaurant> findRestaurantsByMaxPriceOrderByLowestPriceAsc(Integer maxPrice, Pageable pageable) {
        return restaurantRepository.findByMaxPriceOrderByLowestPriceAsc(maxPrice, pageable);
    }

    // 9.全件×評価順
    public Page<Restaurant> findAllRestaurantsByOrderByRatingDesc(Pageable pageable) {
        return restaurantRepository.findAllByOrderByRatingDesc(pageable);
    }

    // 10.キーワード×評価順
    public Page<Restaurant> serchByKeywordsOrderByRating(String keyword, Pageable pageable) {
        return restaurantRepository.searchByKeywordOrderByRatingDesc(keyword, pageable);
    }

    // 11.id×評価順
    // 指定されたidのカテゴリが設定された店舗を平均評価が高い順に並べ替え、ページングされた状態で取得する。
    public Page<Restaurant> findRestaurantsByCategoryIdOrderByRating(Integer id, Pageable pageable) {
        return restaurantRepository.findByCategoryIdOrderByRatingDesc(id, pageable);
    }

    // 12.最低価格以下×評価順
    // 指定された最低価格以下の店舗を平均評価が高い順に並べ替え、ページングされた状態で取得する。
    public Page<Restaurant> findRestaurantsByLowestPriceLessThanEqualOrderByRating(Integer lowestPrice,
            Pageable pageable) {
        return restaurantRepository.findByLowestPriceLessThanEqualOrderByRatingDesc(lowestPrice, pageable);
    }

    // 13.全件×予約数順
    public Page<Restaurant> findAllRestaurantsByOrderByReservationCountDesc(Pageable pageable) {
        return restaurantRepository.findAllOrderByReservationCountDesc(pageable);
    }

    // 14.キーワード×予約数順
    public Page<Restaurant> findRestaurantsByKeywordOrderByReservationCount(String keyword, Pageable pageable) {
        return restaurantRepository.findByKeywordOrderByReservationCountDesc(keyword, pageable);
    }

    // 15.カテゴリーid×予約順
    public Page<Restaurant> findRestaurantsByCategoryIdOrderByReserVationCountDesc(Integer categoryId,
            Pageable pageable) {
        return restaurantRepository.findByCategoryIdOrderByReservationCountDesc(categoryId, pageable);
    }

    // 16.最低価格×予約数順
    public Page<Restaurant> findRestaurantsByLowestPriceOrderByResetbationCountDesc(Integer lowestPrice,
            Pageable pageable) {
        return restaurantRepository.findAllByOrderByLowestPriceAsc(pageable);
    }

}
