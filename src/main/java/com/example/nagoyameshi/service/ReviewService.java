package com.example.nagoyameshi.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.exception.ReviewNotFoundException;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.ReviewRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    public ReviewService(ReviewRepository reviewRepository,
            RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
    }

    // 指定したidのレビューを取得
    public Review findReviewByid(Integer id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("該当のIdのレビューが見つかりません id : " + id));
    }

    // ユーザーと店舗に結びついたレビューを取得する
    public Review findReviewByRestaurantAndUser(User user, Restaurant restaurant) {
        return findReviewByRestaurantAndUser(user, restaurant);
    }

    // 指定した店舗の全レビューを作成日時が新しい順にページングして取得
    public Page<Review> findReviewsByRestaurantOrderByCreatedAtDesc(Restaurant restaurant, Pageable pageable) {
        return reviewRepository.findByRestaurantOrderByCreatedAtDesc(restaurant, pageable);
    }

    // レビューの数を数える
    public Long countReviews() {
        return reviewRepository.count();
    }

    // Idがもっとも大きいレビューを取得
    public Review findFirstReviewByOrderByIdDesc(Integer id) {
        return reviewRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new ReviewNotFoundException("Idが最も大きいレビューが取得できませんでした。"));
    }

    // レビューの作成
    @Transactional
    public void createReview(Review review) {
        reviewRepository.save(review);
        updateRestaurantRating(review);
    }

    // レビューの更新
    @Transactional
    public void updateReview(Review review) {
        reviewRepository.save(review);
        updateRestaurantRating(review);
    }

    // レビューの削除
    @Transactional
    public void deleteReview(Review review) {
        reviewRepository.delete(review);
        updateRestaurantRating(review);
    }

    // ユーザーと店舗に結びついたレビューがすでに存在するか するならtrue
    public boolean hasUserAlreadyReviewed(User user, Restaurant restaurant) {
        return reviewRepository.existsByRestaurantAndUser(restaurant, user);
    }

    // 店舗に関連すうｒレビューのスコアを統計して、店舗情報を更新する
    @Transactional
    public void updateRestaurantRating(Review review) {
        Restaurant restaurant = review.getRestaurant();
        Double avg = reviewRepository.findAverageScoreByRestaurant(restaurant);

        if (avg == null) {
            restaurant.setRating(BigDecimal.ZERO);
        } else {
            restaurant.setRating(BigDecimal.valueOf(avg).setScale(1, RoundingMode.HALF_UP));
        }
        restaurantRepository.save(restaurant);
    }

}
