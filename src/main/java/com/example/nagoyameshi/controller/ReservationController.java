package com.example.nagoyameshi.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.exception.ReservationNotFoundException;
import com.example.nagoyameshi.exception.RestaurantNotFoundException;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.mapper.ReservationMapper;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.HolidayService;
import com.example.nagoyameshi.service.ReservationService;
import com.example.nagoyameshi.service.RestaurantService;
import com.example.nagoyameshi.service.UserRoleService;

import jakarta.validation.Valid;

@Controller
public class ReservationController {

    private final RestaurantService restaurantService;

    private final HolidayService holidayService;
    private final ReservationService reservationService;
    private final UserRoleService userRoleService;

    public ReservationController(HolidayService holidayService,
            ReservationService reservationService,
            UserRoleService userRoleService, RestaurantService restaurantService) {
        this.holidayService = holidayService;
        this.reservationService = reservationService;
        this.userRoleService = userRoleService;
        this.restaurantService = restaurantService;
    }

    // 店舗の予約トップページ
    @GetMapping("/reservations")
    public String index(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @PageableDefault(page = 0, size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            RedirectAttributes redirectAttributes,
            Model model) {

        // ログイン中のユーザー情報と、そのロール権限を取得
        User user = userDetailsImpl.getUser();
        Role nowRole = userRoleService.getRoleByUser(user);

        // 通常会員なら、有料プラン登録ページへリダイレクト
        String redirectUrl = redirectIfFreeMember(nowRole, redirectAttributes);
        if (redirectUrl != null) {
            return redirectUrl;
        }

        Page<Reservation> reservationPage = reservationService.findByUserByLocaldateDesc(user, pageable);
        LocalDateTime currentDateTime = LocalDateTime.now();

        model.addAttribute("reservationPage", reservationPage);
        model.addAttribute("currentDateTime", currentDateTime);

        return "reservations/index";
    }

    // 店舗の予約登録ページ
    @GetMapping("/restaurants/{restaurantId}/reservations/register")
    public String showReservationRegisterForm(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @PathVariable("restaurantId") Integer restaurantId,
            @ModelAttribute ReservationRegisterForm reservationRegisterForm,
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

        // 店舗の定休日情報を取得(flatPikr用) →ミスしたら対象店舗ページも見れない可能性があるので一覧へリダイレクト
        List<Integer> restaurantRegularHolidays;
        try {
            restaurantRegularHolidays = holidayService.getrestaurantRegularHolidays(restaurant);
        } catch (DataAccessException e) {
            System.err.println("休日情報の取得に失敗しました" + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "予約対象の店舗の定休日の取得に失敗しました。");
            return "redirect:/restaurants";
        }

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("restaurantRegularHolidays", restaurantRegularHolidays);

        return "reservations/register";
    }

    // 予約登録のPOST
    @PostMapping("/restaurants/{restaurantId}/reservations/create")
    public String create(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @PathVariable("restaurantId") Integer restaurantId,
            @ModelAttribute @Valid ReservationRegisterForm reservationRegisterForm,
            BindingResult bindingResult,
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

        // 店舗の定休日情報を取得(flatPikr用) →ミスしたら対象店舗ページも見れない可能性があるので一覧へリダイレクト
        List<Integer> restaurantRegularHolidays;
        try {
            restaurantRegularHolidays = holidayService.getrestaurantRegularHolidays(restaurant);
        } catch (DataAccessException e) {
            System.err.println("休日情報の取得に失敗しました" + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "予約対象の店舗の定休日の取得に失敗しました。");
            return "redirect:/restaurants";
        }

        // 入力フォームのバリデーションエラー →再度入力画面へ戻す
        if (bindingResult.hasErrors()) {
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("restaurantRegularHolidays", restaurantRegularHolidays);
            return "/reservations/register";
        }

        Reservation reservation = new Reservation();
        reservation = ReservationMapper.mapToEntity(reservationRegisterForm, reservation, user, restaurant);

        // 予約の日時が現在より２時間以内の場合 →時間的余裕がないので、再度入力画面へ戻す
        if (!reservationService.iaAtLeastTwoHourslnFuture(reservation.getReservedDatetime())) {
            bindingResult.rejectValue("reservationTime", "reservation.tooEarly", "予約時間は現在の時間より２時間以上あとにしてください。");

            model.addAttribute("restaurant", restaurant);
            model.addAttribute("restaurantRegularHolidays", restaurantRegularHolidays);
            return "/reservations/register";
        }

        // 予約の作成 →成功メッセージを送って店舗TOPへリダイレクトする
        try {
            reservationService.createReservation(reservation);
        } catch (DataAccessException e) {
            System.err.println("予約の登録に失敗しました。" + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "予約の登録処理に失敗しました。再び登録をお願いします。");
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("restaurantRegularHolidays", restaurantRegularHolidays);
            return "/reservations/register";
        }
        redirectAttributes.addFlashAttribute("successMessage", "予約が完了しました。");

        return "redirect:/restaurants/" + restaurantId;
    }

    // 予約削除のPOST
    @PostMapping("/reservations/{reservationId}/delete")
    public String delete(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @PathVariable("reservationId") Integer reservationId,
            RedirectAttributes redirectAttributes) {

        // ログイン中のユーザー情報と、そのロール権限を取得
        User user = userDetailsImpl.getUser();
        // ユーザーのロールに応じて、予約フォームを表示するか判断
        Role nowRole = userRoleService.getRoleByUser(user);

        // 無料会員なら、予約不可 →プラン登録ページへ遷移
        String redirectUrl = redirectIfFreeMember(nowRole, redirectAttributes);
        if (redirectUrl != null) {
            return redirectUrl;
        }

        // 予約情報の取得 →取得できない場合には、予約のトップページへ移動させる
        Reservation reservation;
        try {
            reservation = reservationService.findReservationById(reservationId);
        } catch (ReservationNotFoundException e) {
            System.err.println("予約情報が取得できませんでした。");
            redirectAttributes.addFlashAttribute("errorMessage", "予約情報が取得できませんでした。");
            return "redirect:/reservations";
        }

        // 予約情報に紐づくユーザーのidと現在ログイン中のユーザーidの比較 →一致しなければ、不正なユーザーなので予約一覧へ遷移させる
        if (!reservation.getUser().getId().equals(user.getId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "不正なアクセスです。");
            return "redirect:/reservations";
        }

        // 削除処理 →失敗したなら、予約一覧へ遷移させる
        try {
            reservationService.deleteReservation(reservation);
        } catch (DataAccessException e) {
            System.err.println("予約の削除処理に失敗しました。" + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "予約の削除に失敗しました。");
            return "redirect:/reservations";
        }

        redirectAttributes.addFlashAttribute("successMessage", "予約をキャンセルしました。");
        return "redirect:/reservations";
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
