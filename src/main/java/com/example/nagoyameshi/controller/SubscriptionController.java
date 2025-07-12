package com.example.nagoyameshi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.nagoyameshi.entity.Payment;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.StripeService;
import com.example.nagoyameshi.service.UserService;
import com.stripe.exception.StripeException;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

@Controller
public class SubscriptionController {

    private final UserService userService;

    private final StripeService stripeService;

    public SubscriptionController(
            StripeService stripeService,
            UserService userService) {
        this.stripeService = stripeService;

        this.userService = userService;
    }

    private String stripePublicKey;
    private String stripeReturnUrl;

    @PostConstruct
    private void init() {
        Dotenv dotenv = Dotenv.configure().load();
        stripePublicKey = dotenv.get("STRIPE_PUBLIC_KEY");
        stripeReturnUrl = dotenv.get("STRIPE_PORTAL_RETURN_URL");
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

    // 有料プランのキャンセル画面
    @GetMapping("/subscription/cancel")
    public String cancel() {
        return "subscription/cancel";
    }

    // 有料プランのキャンセルリクエスト
    @PostMapping("/subscription/delete")
    public String cancelSubscription(@AuthenticationPrincipal UserDetailsImpl userDetails) throws StripeException {
        User user = userDetails.getUser();
        String newRoleName = stripeService.cancelSubscription(user);
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
    public String redirectToStripePortal(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
            throws StripeException {

        User user = userDetailsImpl.getUser();
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
