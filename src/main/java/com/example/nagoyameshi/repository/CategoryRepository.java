package com.example.nagoyameshi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // カテゴリ名で一致するものが在るかを検索 あればtrue ないならfalse
    boolean existsByName(String name);

    // カテゴリ名で部分一致検索してページング
    Page<Category> findByNameContaining(String name, Pageable pageable);

    // IDが最大のカテゴリを1件取得
    Optional<Category> findFirstByOrderByIdDesc();

}
