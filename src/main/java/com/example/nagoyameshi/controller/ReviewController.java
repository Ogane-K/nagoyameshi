package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.UserRole;
import com.example.nagoyameshi.exception.RestaurantNotFoundException;
import com.example.nagoyameshi.exception.ReviewNotFoundException;
import com.example.nagoyameshi.exception.RoleNotFoundException;
import com.example.nagoyameshi.form.ReviewEditForm;
import com.example.nagoyameshi.form.ReviewRegisterForm;
import com.example.nagoyameshi.mapper.ReviewMapper;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.RestaurantSerchService;
import com.example.nagoyameshi.service.RestaurantService;
import com.example.nagoyameshi.service.ReviewService;
import com.example.nagoyameshi.service.UserRoleService;

@Controller

public class ReviewController {

    private final RestaurantService restaurantService;
    private final ReviewService reviewService;
    private final UserRoleService userRoleService;

    public ReviewController(RestaurantService restaurantService,
            ReviewService reviewService,
            RestaurantSerchService restaurantSerchService,
            UserRoleService userRoleService) {
        this.restaurantService = restaurantService;
        this.reviewService = reviewService;
        this.userRoleService = userRoleService;
    }

    // レビュー閲覧画面の表示
    @GetMapping("/restaurants/{restaurantId}/reviews")
    public String index(@PathVariable(value = "restaurantId") Integer id,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable,
            RedirectAttributes redirectAttributes,
            Model model) {

        // ログイン中のユーザー情報を取得
        User user = userDetailsImpl.getUser();

        Restaurant restaurant = null;
        try {
            restaurant = restaurantService.findRestaurantById(id);
        } catch (RestaurantNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "店舗が存在しません。");
            return "redirect:/restaurant";
        }

        UserRole userRole = null;
        try {
            userRole = userRoleService.findByUser(user).get(0);
        } catch (RoleNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMesssage", "現在ログイン中のユーザーのロールが取得できませんでした。");
            return "redirect:/restaurant";
        }

        Role role = userRole.getRole();
        if ("ROLE_FREE_MEMBER".equals(role.getName())) {
            pageable = PageRequest.of(0, 3, Direction.ASC);
        }

        // レビューの一覧を取得する
        Page<Review> reviewPage = reviewService.findReviewsByRestaurantOrderByCreatedAtDesc(restaurant, pageable);

        Boolean hasUserAlreadyReviewed = reviewService.hasUserAlreadyReviewed(user, restaurant);

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("userRoleName", role.getName());
        model.addAttribute("reviewPage", reviewPage);
        model.addAttribute("hasUserAlreadyReviewed", hasUserAlreadyReviewed);

        return "reviews/index";
    }

    // レビュー投稿画面の表示
    @GetMapping("/restaurants/{restaurantId}/reviews/register")
    public String register(@PathVariable(value = "restaurantId") Integer id,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            RedirectAttributes redirectAttributes,
            Model model) {

        // ログイン中のユーザー情報と、そのロール権限を取得
        User user = userDetailsImpl.getUser();
        Role nowRole = userRoleService.getRoleByUser(user);

        // 通常会員なら、有料プラン登録ページへリダイレクト
        if (nowRole.getName().equals("ROLE_FREE_MEMBER")) {
            redirectAttributes.addFlashAttribute("subscriptionMessage", "この機能を利用するには有料プランへの登録が必要です。");
            return "redirect:/subscription/register";
        }

        // 店舗情報を取得
        Restaurant restaurant = null;
        try {
            restaurant = restaurantService.findRestaurantById(id);
        } catch (RestaurantNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "店舗が存在しません。");
            return "redirect:/restaurants";
        }

        // フォームクラスを用意、Modelにセット
        ReviewRegisterForm reviewRegisterForm = new ReviewRegisterForm(5, null);

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("reviewRegisterForm", reviewRegisterForm);

        return "reviews/register";

    }

    // レビューの投稿
    @PostMapping("/restaurants/{restaurantId}/reviews/create")
    public String create(@PathVariable(value = "restaurantId") Integer id,
            @ModelAttribute @Validated ReviewRegisterForm registerForm,
            BindingResult bindingresult,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            RedirectAttributes redirectAttributes,
            Model model) {

        User user = userDetailsImpl.getUser();
        Role nowRole = userRoleService.getRoleByUser(user);

        // 通常会員なら、有料プラン登録ページへリダイレクト
        if (nowRole.getName().equals("ROLE_FREE_MEMBER")) {
            redirectAttributes.addFlashAttribute("subscriptionMessage", "この機能を利用するには有料プランへの登録が必要です。");
            return "redirect:/subscription/register";
        }

        // 店舗情報を取得
        Restaurant restaurant = null;
        try {
            restaurant = restaurantService.findRestaurantById(id);
        } catch (RestaurantNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "店舗が存在しません。");
            return "redirect:/restaurants";
        }

        // バリデーションエラーのチェック
        if (bindingresult.hasErrors()) {
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("errorMessage", "入力内容にエラーがあります。");
            return "reviews/register";
        }

        Review review = new Review();
        review = ReviewMapper.toEntity(review, registerForm, user, restaurant);

        try {
            reviewService.createReview(review);
            redirectAttributes.addFlashAttribute("successMessage", "レビューを投稿しました。");
        } catch (Exception e) {
            System.err.println("レビュー内容の保存の際エラーが発生しました。 : " + e);
            redirectAttributes.addFlashAttribute("errorMessage", "レビュー内容の保存に失敗しました。");
            return "redirect:/restaurants";
        }

        return "redirect:/restaurants/" + id + "/reviews";
    }

    // レビューの編集画面
    @GetMapping("/restaurants/{restaurantId}/reviews/{reviewId}/edit")
    public String edit(@PathVariable(value = "restaurantId") Integer id,
            @PathVariable(value = "reviewId") Integer revId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @ModelAttribute ReviewEditForm editForm,
            RedirectAttributes redirectAttributes,
            Model model) {

        User user = userDetailsImpl.getUser();
        Role nowRole = userRoleService.getRoleByUser(user);

        // 通常会員なら、有料プラン登録ページへリダイレクト
        if (nowRole.getName().equals("ROLE_FREE_MEMBER")) {
            redirectAttributes.addFlashAttribute("subscriptionMessage", "この機能を利用するには有料プランへの登録が必要です。");
            return "redirect:/subscription/register";
        }
        // 店舗情報とレビュー情報を取得
        Review review = null;
        Restaurant restaurant = null;
        try {
            restaurant = restaurantService.findRestaurantById(id);
            review = reviewService.findReviewByid(revId);

            if (review == null) {
                throw new ReviewNotFoundException("ユーザー、または店舗に紐づいたレビューが見つかりません。");
            } else if (!review.getUser().equals(user)) {
                throw new ReviewNotFoundException("不正なアクセスです。");
            }
        } catch (RestaurantNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "店舗が存在しません。");
            return "redirect:/restaurants";
        } catch (ReviewNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "レビューが存在しません。");
            return "redirect:/restaurants";
        }

        // レビュー情報をフォームクラスに箱詰め
        editForm = ReviewMapper.toEditForm(review);

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("review", review);
        model.addAttribute("reviewEditForm", editForm);

        return "reviews/edit";
    }

    // レビューの更新処理リクエスト
    @PostMapping("/restaurants/{restaurantId}/reviews/{reviewId}/update")
    public String update(@PathVariable(value = "restaurantId") Integer id,
            @PathVariable(value = "reviewId") Integer revId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @ModelAttribute @Validated ReviewEditForm editForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {
        User user = userDetailsImpl.getUser();
        Role nowRole = userRoleService.getRoleByUser(user);

        // 通常会員なら、有料プラン登録ページへリダイレクト
        if (nowRole.getName().equals("ROLE_FREE_MEMBER")) {
            redirectAttributes.addFlashAttribute("subscriptionMessage", "この機能を利用するには有料プランへの登録が必要です。");
            return "redirect:/subscription/register";
        }
        // 店舗情報とレビュー情報を取得
        Review review = null;
        Restaurant restaurant = null;
        try {
            restaurant = restaurantService.findRestaurantById(id);
            review = reviewService.findReviewByid(revId);

            if (review == null) {
                throw new ReviewNotFoundException("ユーザー、または店舗に紐づいたレビューが見つかりません。");
            } else if (!review.getUser().equals(user)) {
                throw new ReviewNotFoundException("不正なアクセスです。");
            }
        } catch (RestaurantNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "店舗が存在しません。");
            return "redirect:/restaurants";
        } catch (ReviewNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "レビューが存在しません。");
            return "redirect:/restaurants";
        }

        // バリデーションエラーのチェック
        if (bindingResult.hasErrors()) {
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("review", review);
            model.addAttribute("errorMessage", "入力内容にエラーがあります。");
            return "reviews/edit";
        }

        review = ReviewMapper.toEntity(review, editForm, user, restaurant);

        try {
            reviewService.updateReview(review);
            redirectAttributes.addFlashAttribute("successMessage", "レビューの内容を更新しました。");
        } catch (Exception e) {
            System.err.println("レビュー内容の保存の際エラーが発生しました。 : " + e);
            redirectAttributes.addFlashAttribute("errorMessage", "レビュー内容の保存に失敗しました。");
            return "redirect:/restaurants";
        }

        return "redirect:/restaurants/" + id + "/reviews";
    }

    @PostMapping("/restaurants/{restaurantId}/reviews/{reviewId}/delete")
    public String delete(@PathVariable(value = "restaurantId") Integer id,
            @PathVariable(value = "reviewId") Integer revId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            RedirectAttributes redirectAttributes,
            Model model) {

        User user = userDetailsImpl.getUser();
        Role nowRole = userRoleService.getRoleByUser(user);

        // 通常会員なら、有料プラン登録ページへリダイレクト
        if (nowRole.getName().equals("ROLE_FREE_MEMBER")) {
            redirectAttributes.addFlashAttribute("subscriptionMessage", "この機能を利用するには有料プランへの登録が必要です。");
            return "redirect:/subscription/register";
        }
        // 店舗情報とレビュー情報を取得
        Review review = null;
        Restaurant restaurant = null;
        try {
            restaurant = restaurantService.findRestaurantById(id);
            review = reviewService.findReviewByid(revId);

            if (review == null) {
                throw new ReviewNotFoundException("ユーザー、または店舗に紐づいたレビューが見つかりません。");
            } else if (!review.getUser().equals(user)) {
                throw new ReviewNotFoundException("不正なアクセスです。");
            }
        } catch (RestaurantNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "店舗が存在しません。");
            return "redirect:/restaurants";
        } catch (ReviewNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "レビューが存在しません。");
            return "redirect:/restaurants";
        }

        try {
            reviewService.deleteReview(review);
            redirectAttributes.addFlashAttribute("successMessage", "レビューを削除しました。");
        } catch (Exception e) {
            System.err.println("レビュー内容の保存の際エラーが発生しました。 : " + e);
            redirectAttributes.addFlashAttribute("errorMessage", "レビューの削除に失敗しました。");
            return "redirect:/restaurants";
        }

        return "redirect:/restaurants";
    }

}
