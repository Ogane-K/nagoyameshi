package com.example.nagoyameshi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
        public List<Restaurant> findRandom6Restaurants();

        // 価格の安い順に、全店舗を並び替えてページング取得
        public Page<Restaurant> findAllByOrderByLowestPriceAsc(Pageable pageable);

        // キーワードに基づいて、作成日時が新しい順に店舗情報をページングした状態で取得
        @Query("SELECT DISTINCT r FROM Restaurant r " +
                        "LEFT JOIN r.categoriesRestaurants rc " +
                        "LEFT JOIN rc.category c " +
                        "WHERE r.name LIKE :keyword " +
                        "OR r.address LIKE :keyword " +
                        "OR c.name LIKE :keyword " +
                        "ORDER BY r.createdAt DESC ")
        public Page<Restaurant> findByKeywordOrderByCreatedAtDesc(@Param("keyword") String keyword, Pageable pageable);

        // キーワードに基づいて、最低価格が安いに店舗情報をページングした状態で取得
        @Query("SELECT DISTINCT r FROM Restaurant r " +
                        "LEFT JOIN r.categoriesRestaurants rc " +
                        "LEFT JOIN rc.category c " +
                        "WHERE r.name LIKE :keyword " +
                        "OR r.address LIKE :keyword " +
                        "OR c.name LIKE :keyword " +
                        "ORDER BY r.lowestPrice ASC ")
        public Page<Restaurant> findByKeywordOrderByLowestPriceAsc(@Param("keyword") String keyword, Pageable pageable);

        // カテゴリーidに基づいて、作成日時が新しい順にページングした状態で取得
        @Query("SELECT DISTINCT r FROM Restaurant r " +
                        "JOIN r.categoriesRestaurants rc " +
                        "WHERE rc.category.id = :categoryId " +
                        "ORDER BY r.createdAt DESC")
        public Page<Restaurant> findByCategoryIdOrderByCreatedAtDesc(@Param("categoryId") Integer categoryId,
                        Pageable pageable);

        // カテゴリーidに基づいて、価格が安い順にページングした状態で取得
        @Query("SELECT DISTINCT r FROM Restaurant r " +
                        "JOIN r.categoriesRestaurants rc " +
                        "WHERE rc.category.id = :categoryId " +
                        "ORDER BY r.lowestPrice ASC")
        public Page<Restaurant> findByCategoryIdOrderByLowestPriceAsc(@Param("categoryId") Integer categoryId,
                        Pageable pageable);

        // 指定した価格よりも最低価格が低い店舗を、作成日時が新しい順にページングした状態で取得
        @Query("SELECT r FROM Restaurant r " +
                        "WHERE r.lowestPrice <= :maxPrice " +
                        "ORDER BY r.createdAt DESC")
        public Page<Restaurant> findByMaxPriceOrderByCreatedAtDesc(@Param("maxPrice") Integer maxPrice,
                        Pageable pageable);

        // 指定した価格よりも最低価格が低い店舗を、価格が安い順にページングした状態で取得
        @Query("SELECT r FROM Restaurant r " +
                        "WHERE r.lowestPrice <= :maxPrice " +
                        "ORDER BY r.lowestPrice ASC")
        Page<Restaurant> findByMaxPriceOrderByLowestPriceAsc(@Param("maxPrice") Integer maxPrice, Pageable pageable);

        // すべての店舗を平均評価が高い順に並べ替え、ページングされた状態で取得する。
        public Page<Restaurant> findAllByOrderByRatingDesc(Pageable pageable);

        // 指定されたキーワードを店舗名または住所またはカテゴリ名に含む店舗を平均評価が高い順に並べ替え、ページングされた状態で取得する。
        @Query("""
                            SELECT DISTINCT r FROM Restaurant r
                            LEFT JOIN r.categoriesRestaurants rc
                            LEFT JOIN rc.category c
                            WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                               OR LOWER(r.address) LIKE LOWER(CONCAT('%', :keyword, '%'))
                               OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                            ORDER BY r.rating DESC
                        """)
        Page<Restaurant> searchByKeywordOrderByRatingDesc(@Param("keyword") String keyword, Pageable pageable);

        // 指定されたidのカテゴリが設定された店舗を平均評価が高い順に並べ替え、ページングされた状態で取得する。
        @Query("""
                            SELECT DISTINCT r FROM Restaurant r
                            JOIN r.categoriesRestaurants rc
                            JOIN rc.category c
                            WHERE c.id = :categoryId
                            ORDER BY r.rating DESC
                        """)
        Page<Restaurant> findByCategoryIdOrderByRatingDesc(@Param("categoryId") Integer categoryId, Pageable pageable);

        // 指定された最低価格以下の店舗を平均評価が高い順に並べ替え、ページングされた状態で取得する。
        @Query("""
                            SELECT r FROM Restaurant r
                            WHERE r.lowestPrice <= :maxPrice
                            ORDER BY r.rating DESC
                        """)
        Page<Restaurant> findByLowestPriceLessThanEqualOrderByRatingDesc(@Param("maxPrice") Integer maxPrice,
                        Pageable pageable);

        // すべての店舗を予約数が多い順に並べ替え、ページングされた状態で取得
        @Query("""
                        SELECT r FROM Restaurant r
                        LEFT JOIN r.reservations res
                        GROUP BY r
                        ORDER BY COUNT(res) DESC
                        """)
        Page<Restaurant> findAllOrderByReservationCountDesc(Pageable pageable);

        // 指定されたキーワードを店舗名または住所またはカテゴリ名に含む店舗を予約数が多い順に並べ替え、ページングされた状態で取得
        @Query("""
                        SELECT DISTINCT r FROM Restaurant r
                        LEFT JOIN r.reservations res
                        LEFT JOIN r.categoriesRestaurants cr
                        LEFT JOIN cr.category c
                        WHERE r.name LIKE :keyword
                        OR r.address LIKE :keyword
                        OR c.name LIKE :keyword
                        GROUP BY r
                        ORDER BY COUNT(res) DESC
                        """)
        Page<Restaurant> findByKeywordOrderByReservationCountDesc(@Param("keyword") String keyword, Pageable pageable);

        // 指定されたidのカテゴリが設定された店舗を予約数が多い順に並べ替え、ページングされた状態で取得
        @Query("""
                        SELECT DISTINCT r FROM Restaurant r
                        LEFT JOIN r.reservations res
                        LEFT JOIN r.categoriesRestaurants cr
                        JOIN cr.category c
                        WHERE c.id = :categoryId
                        GROUP BY r
                        ORDER BY COUNT(res) DESC
                        """)
        Page<Restaurant> findByCategoryIdOrderByReservationCountDesc(@Param("categoryId") Integer categoryId,
                        Pageable pageable);

        // 指定された最低価格以下の店舗を予約数が多い順に並べ替え、ページングされた状態で取得
        @Query("""
                        SELECT r FROM Restaurant r
                        LEFT JOIN r.reservations res
                        WHERE r.lowestPrice <= :maxPrice
                        GROUP BY r
                        ORDER BY COUNT(res) DESC
                        """)
        Page<Restaurant> findByPriceLessThanEqualOrderByReservationCountDesc(@Param("maxPrice") Integer maxPrice,
                        Pageable pageable);
}