package com.example.nagoyameshi.service;

import org.hibernate.query.NativeQuery.ReturnProperty;
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
    private final CategoryRepository categoryRepository;
    private final RestaurantCategoryRepository restaurantCategoryRepository;

    public RestaurantSerchService(RestaurantRepository restaurantRepository,
            CategoryRepository categoryRepository,
            RestaurantCategoryRepository restaurantCategoryRepository) {
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
        this.restaurantCategoryRepository = restaurantCategoryRepository;
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

    // 平均レビュースコアが高い店舗順に並べ替えてページング
    public Page<Restaurant> findAllRestaurantsByOrderByRatingDesc(Pageable pageable) {
        return restaurantRepository.findAllByOrderByRatingDesc(pageable);
    }

    // 指定されたキーワードを店舗名または住所またはカテゴリ名に含む店舗を平均評価が高い順に並べ替え、ページングされた状態で取得する
    public Page<Restaurant> serchhByKeywordsOrderByRating(String keyword, Pageable pageable) {
        return restaurantRepository.searchByKeywordOrderByRatingDesc(keyword, pageable);
    }

    // 指定されたidのカテゴリが設定された店舗を平均評価が高い順に並べ替え、ページングされた状態で取得する。
    public Page<Restaurant> findRestaurantsByCategoryIdOrderByRating(Integer id, Pageable pageable) {
        return restaurantRepository.findByCategoryIdOrderByRatingDesc(id, pageable);
    }

    // 指定された最低価格以下の店舗を平均評価が高い順に並べ替え、ページングされた状態で取得する。
    public Page<Restaurant> findRestaurantsByLowestPriceLessThanEqualOrderByRating(Integer lowestPrice,
            Pageable pageable) {
        return restaurantRepository.findByLowestPriceLessThanEqualOrderByRatingDesc(lowestPrice, pageable);
    }

}
