package com.example.nagoyameshi.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.exception.FavoriteNotFoundException;
import com.example.nagoyameshi.exception.RestaurantNotFoundException;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.FavoriteService;
import com.example.nagoyameshi.service.RestaurantService;
import com.example.nagoyameshi.service.UserRoleService;

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final UserRoleService userRoleService;
    private final RestaurantService restaurantService;

    public FavoriteController(FavoriteService favoriteService,
            UserRoleService userRoleService,
            RestaurantService restaurantService) {
        this.favoriteService = favoriteService;
        this.userRoleService = userRoleService;
        this.restaurantService = restaurantService;

    }

    // ユーザーのお気に入り一覧画面
    @GetMapping("/favorites")
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
            RedirectAttributes redirectAttributes,
            Model model) {

        // ログイン中のユーザー情報と、そのロール権限を取得
        User user = userDetailsImpl.getUser();
        // ユーザーのロールに応じて、予約フォームを表示するか判断
        Role nowRole = userRoleService.getRoleByUser(user);

        // 無料会員なら、予約不可 →プラン登録ページへ遷移
        String redirectUrl = redirectIfFreeMember(nowRole, redirectAttributes);
        if (redirectUrl != null) {
            return redirectUrl;
        }

        // お気に入りの一覧
        Page<Favorite> favoritePage = favoriteService.findFavoritesByUserOrderByCreatedAtDesc(user, pageable);

        model.addAttribute("favoritePage", favoritePage);

        return "favorites/index";
    }

    // お気に入りの登録POST
    @PostMapping("/restaurants/{restaurantId}/favorites/create")
    public String create(@PathVariable(value = "restaurantId") Integer restaurantId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            RedirectAttributes redirectAttributes,
            Model model) {

        // ログイン中のユーザー情報と、そのロール権限を取得
        User user = userDetailsImpl.getUser();
        // ユーザーのロールに応じて、予約フォームを表示するか判断
        Role nowRole = userRoleService.getRoleByUser(user);

        // 無料会員なら、予約不可 →プラン登録ページへ遷移
        String redirectUrl = redirectIfFreeMember(nowRole, redirectAttributes);
        if (redirectUrl != null) {
            return redirectUrl;
        }

        // 店舗情報の取得 →ミスしたら対象店舗がないので一覧へリダイレクト
        Restaurant restaurant;
        try {
            restaurant = restaurantService.findRestaurantById(restaurantId);
        } catch (RestaurantNotFoundException e) {
            System.err.println(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "予約対象の店舗の取得に失敗しました。");
            return "redirect:/restaurants";
        }

        // お気に入りの登録 →失敗したら店舗ページへリダイレクト
        try {
            favoriteService.createFavorite(restaurant, user);
        } catch (DataAccessException e) {
            System.err.println("お気に入りの登録に失敗しました" + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "お気に入りの登録に失敗しました。");
            return "redirect:/restaurants/" + restaurantId;
        }
        redirectAttributes.addFlashAttribute("successMessage", "お気に入りに登録しました。");

        return "redirect:/restaurants/" + restaurantId;
    }

    // お気に入りの削除POST
    @PostMapping("/favorites/{favoriteId}/delete")
    public String postMethodName(@PathVariable(value = "favoriteId") Integer favoriteId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            Model model) {

        // ログイン中のユーザー情報と、そのロール権限を取得
        User user = userDetailsImpl.getUser();
        // ユーザーのロールに応じて、予約フォームを表示するか判断
        Role nowRole = userRoleService.getRoleByUser(user);

        // 無料会員なら、予約不可 →プラン登録ページへ遷移
        String redirectUrl = redirectIfFreeMember(nowRole, redirectAttributes);
        if (redirectUrl != null) {
            return redirectUrl;
        }

        String referer = request.getHeader("Referer");

        // お気に入り情報を取得
        Favorite favorite;
        try {
            favorite = favoriteService.findFavoriteById(favoriteId);
        } catch (FavoriteNotFoundException e) {
            System.err.println(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "お気に入りが見つかりませんでした。");
            return "redirect:" + (referer != null ? referer : "/favorites");
        }

        if (!favorite.getUser().getId().equals(user.getId())) {
            System.err.println("他ユーザーのお気に入りを削除しようとしました。 お気に入りのユーザー : " + favorite.getUser().getName() + "| 実行したユーザー : "
                    + user.getName());
            redirectAttributes.addFlashAttribute("errorMessage", "不正なアクセスです。");
            return "redirect:" + (referer != null ? referer : "/favorites");
        }

        try {
            favoriteService.deleteFavorite(favorite);
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "お気に入りの解除に失敗しました。");
            return "redirect:" + (referer != null ? referer : "/favorites");
        }

        redirectAttributes.addFlashAttribute("successMessage", "お気に入りを解除しました。");
        return "redirect:" + (referer != null ? referer : "/favorites");
    }

    // ロールが無料会員だったら、プランの登録ページへのリダイレクトURLを返す
    public String redirectIfFreeMember(Role nowRole, RedirectAttributes redirectAttributes) {
        if (nowRole.getName().equals("ROLE_FREE_MEMBER")) {
            redirectAttributes.addFlashAttribute("subscriptionMessage", "この機能を利用するには有料プランへの登録が必要です。");
            return "redirect:/subscription/register";
        }
        return null;
    }
}
