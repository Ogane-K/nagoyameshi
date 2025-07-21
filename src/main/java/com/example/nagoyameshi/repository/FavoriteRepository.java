package com.example.nagoyameshi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    // 店舗とユーザーに結びついたお気に入りの取得
    public Optional<Favorite> findByRestaurantAndUser(Restaurant restaurant, User user);

    // 店舗とユーザーに結びついたお気に入りがあるかどうか
    public boolean existsByRestaurantAndUser(Restaurant restaurant, User user);

    // 指定したユーザーの全てのお気に入りを作成日時順に並び替えてページング取得する
    public Page<Favorite> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);

}
