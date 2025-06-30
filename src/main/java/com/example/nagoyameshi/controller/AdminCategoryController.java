package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.form.CategoryEditForm;
import com.example.nagoyameshi.form.CategoryRegisterForm;
import com.example.nagoyameshi.service.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // カテゴリーマスタの編集画面の表示
    @GetMapping("/admin/categories")
    public String index(@RequestParam(value = "keyword",required = false ) String keyword,
            @PageableDefault(page = 0, size = 15, sort = "id", direction = Direction.ASC) Pageable pageable,
            Model model) {

        Page<Category> category;

        // キーワードがあるなら、キーワード検索した結果を返す
        if (keyword == null || keyword.isEmpty()) {

            category = categoryService.findAllCategories(pageable);
        } else {

            category = categoryService.findCategoriesByNameLike(keyword, pageable);
        }

        // ページングされたカテゴリーのリストを送る
        model.addAttribute("categoryPage", category);

        // キーワードを送る
        model.addAttribute("keyword", keyword);

        // CRUD機能用のフォームクラスを用意する
        model.addAttribute("categoryRegisterForm", new CategoryRegisterForm());
        model.addAttribute("categoryEditForm", new CategoryEditForm());

        return "admin/categories/index";
    }

    // カテゴリーの新規登録
    @PostMapping("/admin/categories/create")
    public String create(@ModelAttribute @Validated CategoryRegisterForm categoryRegisterForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (!bindingResult.hasErrors()) {
            try {

                categoryService.createCategory(categoryRegisterForm.getName());
                redirectAttributes.addFlashAttribute("successMessage", "カテゴリを登録しました。");
            } catch (Exception e) {

                System.err.println("カテゴリーの登録に失敗しました。 :" + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "登録処理中にエラーが発生しました。");
                return "redirect:/admin/categories";
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "カテゴリ名を入力してください。");
        }

        return "redirect:/admin/categories";
    }

    // 特定カテゴリーの更新
    @PostMapping("/admin/categories/{id}/update")
    public String update(@PathVariable(name = "id") Integer id,
            @ModelAttribute @Validated CategoryEditForm categoryEditForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (!bindingResult.hasErrors()) {
            try {

                Category category = categoryService.findCategoryById(id);

                // idに該当するカテゴリーが見つからなかった場合のエラー

                category.setName(categoryEditForm.getName());
                categoryService.updateCategory(category);
                redirectAttributes.addFlashAttribute("successMessage", "カテゴリを編集しました。");

            } catch (IllegalArgumentException e) {

                redirectAttributes.addFlashAttribute("errorMessage", "カテゴリが存在しません。");
            } catch (Exception e) {

                System.err.println("カテゴリーの更新に失敗しました :" + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "更新処理中にエラーが発生しました。");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "カテゴリ名を入力してください。");
        }

        return "redirect:/admin/categories";
    }

    // 指定したカテゴリーを削除する
    @PostMapping("/admin/categories/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id,
            RedirectAttributes redirectAttributes) {

        try {
            Category category = categoryService.findCategoryById(id);

            categoryService.deleteCategories(category);
            redirectAttributes.addFlashAttribute("successMessage", "カテゴリを削除しました。");
        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("errorMessage", "カテゴリが存在しません。");
        } catch (Exception e) {

            System.err.println("カテゴリーの削除に失敗しました。 :" + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "削除処理中にエラーが発生しました。");
        }

        return "redirect:/admin/categories";
    }

}
