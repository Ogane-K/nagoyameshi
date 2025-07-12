package com.example.nagoyameshi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.StripeService;
import com.example.nagoyameshi.service.UserService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentMethod;
import com.stripe.net.Webhook;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

@Controller
public class WebhookController {

    private final UserService userService;

    private final StripeService stripeService;

    private String webhookSecret;

    public WebhookController(StripeService stripeService, UserService userService) {
        this.stripeService = stripeService;
        this.userService = userService;
    }

    // Webhookシークレットをenvから取得する
    @PostConstruct
    public void getWebhookSecret() {
        Dotenv dotenv = Dotenv.configure().load();
        this.webhookSecret = dotenv.get("STRIPE_WEBHOOK_SECRET");
    }

    @PostMapping("/webhook/stripe")
    public ResponseEntity<String> webhook(@RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws StripeException {

        User user = userDetailsImpl.getUser();
        Event event = null;

        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            System.out.println("Webhookの署名シークレットが正しくありません。");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payload");
        }

        // 有料プラン登録の場合
        if ("checkout.session.completed".equals(event.getType())) {
            String newRoleName = stripeService.handleCheckoutSessionCompleted(event);

            userService.refreshAuthenticationByRole(newRoleName);
            // 支払い情報更新の場合
        } else if ("payment_method.updated".equals(event.getType())) {
            PaymentMethod paymentMethod = (PaymentMethod) event.getDataObjectDeserializer().getObject().orElse(null);

            if (paymentMethod != null) {
                stripeService.updatePayment(user);
            }
        }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
