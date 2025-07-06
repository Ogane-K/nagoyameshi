package com.example.nagoyameshi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.repository.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // すべてのカテゴリーのリストを取得
    public List<Category> findAllCategoriesList() {
        return categoryRepository.findAll();
    }

    // すべてのカテゴリをページングされた状態で取得する。
    public Page<Category> findAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    // 指定されたカテゴリー名を持つ最初のカテゴリーを取得する
    public Category findCategoryByKeyword(String keyword) {
        return categoryRepository.findFirstByName(keyword)
                .orElseThrow(() -> new EntityNotFoundException("カテゴリが見つかりませんでした keyword: " + keyword));
    }

    // 指定されたキーワードをカテゴリ名に含むカテゴリを、ページングされた状態で取得する。
    public Page<Category> findCategoriesByNameLike(String keyword, Pageable pageable) {
        return categoryRepository.findByNameContaining(keyword, pageable);
    }

    // 指定したidを持つカテゴリを取得する。
    public Category findCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("カテゴリが見つかりませんでした ID: " + id));
    }

    // カテゴリのレコード数を取得する。
    public long countCategories() {
        return categoryRepository.count();
    }

    // idが最も大きいカテゴリを取得する。
    public Category findFirstCategoryByOrderByIdDesc() {
        return categoryRepository.findFirstByOrderByIdDesc()
                .orElseThrow(() -> new EntityNotFoundException("最も大きいIDを持つカテゴリが見つかりませんでした"));
    }

    // すでに登録されていないカテゴリがフォームから送信された場合に
    // カテゴリ情報をデータベースに登録する。
    @Transactional
    public void createCategory(String name) {

        if (!categoryRepository.existsByName(name)) {

            Category category = new Category();
            category.setName(name);

            categoryRepository.save(category);

        } else {
            throw new IllegalArgumentException("そのカテゴリ名はすでに登録されています。");
        }
    }

    // フォームから送信されたカテゴリ情報でデータベースを更新する。
    @Transactional
    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }

    // 指定したカテゴリをデータベースから削除する。
    @Transactional
    public void deleteCategories(Category category) {
        categoryRepository.delete(category);
    }

}
