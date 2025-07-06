package com.example.nagoyameshi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    // キーワードに部分一致する名称をもつ店舗をページングして取得
    public Page<Restaurant> findBynameContaining(String keyword, Pageable pageable);

    // idを持つ店舗を取得
    public Optional<Restaurant> findById(Integer id);

    // idが最も大きい店舗レコードを取得
    public Optional<Restaurant> findFirstByOrderByIdDesc();

    // 該当の名前の店舗が存在しているかを確認
    public boolean existsByName(String name);

    // 作成日時順に、全店舗を並び替えてページング取得
    public Page<Restaurant> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // ランダムに6件取得
    @Query(value = "SELECT * FROM restaurants ORDER BY RAND() LIMIT 6", nativeQuery = true)
    List<Restaurant> findRandom6Restaurants();

}
