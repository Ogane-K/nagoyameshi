package com.example.nagoyameshi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // 店舗に関連したレビューの取得
    public List<Review> findByRestaurant(Restaurant restaurant);

    // ユーザーと店舗に関連したレビューの取得
    public Optional<Review> findByRestaurantAndUser(Restaurant restaurant, User user);

    // 店舗に結びついたレビューを作成日時順に取得
    public Page<Review> findByRestaurantOrderByCreatedAtDesc(Restaurant restaurant, Pageable pageable);

    // 最も新しいレビューを取得
    public Optional<Review> findTopByOrderByIdDesc();

    // 指定した店舗に対する平均スコアを取得
    @Query("SELECT AVG(r.score) FROM Review r WHERE r.restaurant = :restaurant")
    Double findAverageScoreByRestaurant(@Param("restaurant") Restaurant restaurant);

    // ユーザーと店舗に紐づいたレビューが存在するか
    public boolean existsByRestaurantAndUser(Restaurant restaurant, User user);
}
