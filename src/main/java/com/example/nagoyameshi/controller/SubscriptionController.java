package com.example.nagoyameshi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Payment;
import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.StripeService;
import com.example.nagoyameshi.service.UserRoleService;
import com.example.nagoyameshi.service.UserService;
import com.stripe.exception.StripeException;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

@Controller
public class SubscriptionController {

    private final UserRoleService userRoleService;

    private final UserService userService;

    private final StripeService stripeService;

    public SubscriptionController(
            StripeService stripeService,
            UserService userService,
            UserRoleService userRoleService) {
        this.stripeService = stripeService;
        this.userRoleService = userRoleService;
        this.userService = userService;
    }

    private String stripePublicKey;
    private String stripeReturnUrl;

    @PostConstruct
    private void init() {
        String profile = System.getenv("SPRING_PROFILES_ACTIVE");

        if ("production".equalsIgnoreCase(profile)) {
            stripePublicKey = System.getenv("STRIPE_PUBLIC_KEY");
            stripeReturnUrl = System.getenv("STRIPE_PORTAL_RETURN_URL");
        } else {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            stripePublicKey = dotenv.get("STRIPE_PUBLIC_KEY");
            stripeReturnUrl = dotenv.get("STRIPE_PORTAL_RETURN_URL");
        }
    }

    // 有料プラン登録画面
    @GetMapping("/subscription/register")
    public String register(Model model) {
        System.out.println("key :  " + stripePublicKey);
        model.addAttribute("stripePublicKey", stripePublicKey);
        return "subscription/register";
    }

    // 有料プラン登録の完了画面

    @GetMapping("/subscription/complete")
    public String complete() {
        return "subscription/complete";
    }

    @GetMapping("/subscription/complete/redirect")
    public String completeRedirect() {
        return "redirect:/";
    }

    // 有料プランのキャンセル画面
    @GetMapping("/subscription/cancel")
    public String cancel(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            RedirectAttributes redirectAttributes) {
        User user = userDetailsImpl.getUser();

        Role nowRole = userRoleService.getRoleByUser(user);

        // 通常会員なら、トップページへリダイレクト
        if (nowRole.getName().equals("ROLE_FREE_MEMBER")) {
            redirectAttributes.addFlashAttribute("errorMessage", "不正なアクセスです。");
            return "redirect:/";
        }

        return "subscription/cancel";
    }

    // 有料プランのキャンセルリクエスト
    @PostMapping("/subscription/delete")
    public String cancelSubscription(@AuthenticationPrincipal UserDetailsImpl userDetails,
            RedirectAttributes redirectAttributes) throws StripeException {
        User user = userDetails.getUser();

        Role nowRole = userRoleService.getRoleByUser(user);

        // 通常会員なら、トップページへリダイレクト
        if (nowRole.getName().equals("ROLE_FREE_MEMBER")) {
            redirectAttributes.addFlashAttribute("errorMessage", "不正なアクセスです。");
            return "redirect:/";
        }

        String newRoleName = null;
        try {
            newRoleName = stripeService.cancelSubscription(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "解約の際にエラーが発生しました。:" + e.getMessage());
            return "redirect:/subscription/cancel";
        }

        userService.refreshAuthenticationByRole(newRoleName);

        return "redirect:/subscription/canceled";
    }

    // 有料プランのキャンセル完了
    @GetMapping("/subscription/canceled")
    public String canceled() {
        return "subscription/canceled";
    }

    // カード情報の表示画面
    @GetMapping("/subscription/edit")
    public String getMethodName(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            Model model) throws StripeException {

        User user = userDetailsImpl.getUser();

        Payment updatedPayment = stripeService.updatePayment(user);

        model.addAttribute("payment", updatedPayment);

        return "subscription/edit";
    }

    // Stripeポータルへの転送
    @PostMapping("/subscription/payment/portal")
    public String redirectToStripePortal(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            RedirectAttributes redirectAttributes)
            throws StripeException {

        User user = userDetailsImpl.getUser();

        // 支払い情報がない場合 →特殊な場合なので、同ページにエラー文付きでリダイレクトさせる
        if (user.getStripeCustomerId() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "お客様の支払い情報は登録されておりません。");
            return "redirect:/subscription/edit";
        }

        String customerId = user.getStripeCustomerId();

        Map<String, Object> params = new HashMap<>();
        params.put("customer", customerId);
        params.put("return_url", stripeReturnUrl);

        com.stripe.model.billingportal.Session session = com.stripe.model.billingportal.Session.create(params);

        return "redirect:" + session.getUrl(); // Stripeのポータルへリダイレクト
    }

    // 更新完了画面の表示
    @GetMapping("/subscription/payment/updated")
    public String supdeted() {
        return "subscription/updated";
    }

}
