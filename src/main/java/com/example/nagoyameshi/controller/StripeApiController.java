package com.example.nagoyameshi.controller;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

@RestController
public class StripeApiController {

    private final StripeService stripeService;

    public StripeApiController(StripeService stripeService) {
        this.stripeService = stripeService;

    }

    @GetMapping("/api/Stripe/setup-Intent")
    public Map<String, String> getSetupIntent(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
            throws StripeException {

        String clientSecret = null;
        try {
            stripeService.createCustomer();

            User user = userDetailsImpl.getUser();
            clientSecret = stripeService.createSetupIntent(user.getStripeCustomerId());
        } catch (StripeException e) {
            System.err.println("顧客情報の作成またはclientSecretの作成でエラーが発生しました : " + e.getMessage());

        }
        return Map.of("clientSecret", clientSecret);
    }

    @PostMapping("/api/stripe/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestBody Map<String, String> request,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws StripeException {
        User user = userDetailsImpl.getUser();
        
        stripeService.createCustomer();

        String customerId = user.getStripeCustomerId();

        Integer planId = Integer.parseInt(request.get("planId"));

        // DBからpriceIdを取得
        String priceId = stripeService.getPriceIdFromPlan(planId);

        // checkOut Sessionを作成
        Session session = stripeService.createCheckoutSession(customerId, priceId);

        System.out.println("StripeセッションID: " + session.getId());

        return Map.of("sessionId", session.getId());
    }

}
