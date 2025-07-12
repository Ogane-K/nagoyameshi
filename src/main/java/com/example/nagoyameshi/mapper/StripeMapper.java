package com.example.nagoyameshi.mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.example.nagoyameshi.entity.Payment;
import com.example.nagoyameshi.entity.Plan;
import com.example.nagoyameshi.entity.User;
import com.stripe.exception.StripeException;
import com.stripe.model.Invoice;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Subscription;

public class StripeMapper {

    // 契約情報(StripeAPIライブラリのsubscription)の情報を元に、subscriptionエンティティ（DB保存用)にデータを保存する
    public static com.example.nagoyameshi.entity.Subscription mapToSubscriptionEntity(
            com.example.nagoyameshi.entity.Subscription userSub,
            Subscription stripeSub,
            User user,
            Plan plan,
            Payment payment) throws StripeException {

        // データ系のセット
        userSub.setUser(user);
        userSub.setStripeSubscriptionId(stripeSub.getId());
        userSub.setPlan(plan);
        userSub.setPayment(payment);

        // 日時系をセット
        // 開始日
        userSub.setStartDate(
                Instant.ofEpochSecond(stripeSub.getStartDate()).atZone(ZoneId.of("Asia/Tokyo"))
                        .toLocalDateTime());

        // 次の支払日
        String invoiceId = stripeSub.getLatestInvoice();
        LocalDateTime nextPaymentDate = null;
        try {
            Invoice invoice = Invoice.retrieve(invoiceId);
            if (invoice != null) {
                Long nextPaymentUnix = invoice.getNextPaymentAttempt();
                if (nextPaymentUnix != null) {
                    nextPaymentDate = Instant.ofEpochSecond(nextPaymentUnix)
                            .atZone(ZoneId.of("Asia/Tokyo"))
                            .toLocalDateTime();
                }
            }
        } catch (StripeException e) {
            System.err.println("invoiceの取得に失敗しました。(invoiceid : " + invoiceId + ") - " + e.getMessage());
        }

        userSub.setNexPayMentDate(nextPaymentDate);

        // その他の値のセット
        userSub.setCanceled(false);
        userSub.setStripeStatus(null);

        return userSub;
    }

    // 支払い情報(Paymentエンティティ)に、各情報を、マッピングする
    public static Payment mapToPaymentEntity(Payment payment,
            User user,
            PaymentMethod paymentMethod) {

        if (paymentMethod != null) {
            // 使用されたクレジットカードの情報を取得する
            PaymentMethod.Card card = paymentMethod.getCard();
            if (card != null) {
                String cardBrand = card.getBrand();
                String cardLast4 = card.getLast4();
                Long cardExpMonth = card.getExpMonth();
                Long cardExpYear = card.getExpYear();

                payment.setCardBrand(cardBrand);
                payment.setLast4Number(cardLast4);
                payment.setExpMonth(cardExpMonth.intValue());
                payment.setExpYear(cardExpYear.intValue());
            } else {
                // カード情報が取得できなかった場合の処理
                payment.setCardBrand(null);
                payment.setLast4Number(null);
                payment.setExpMonth(null);
                payment.setExpYear(null);
            }
            payment.setPaymentMethodId(paymentMethod.getId());
        } else {
            // paymentMethod が null の場合
            payment.setPaymentMethodId(null);
            payment.setCardBrand(null);
            payment.setLast4Number(null);
            payment.setExpMonth(null);
            payment.setExpYear(null);
        }

        payment.setUser(user);
        payment.setDefault(true);
        payment.setActive(true);

        return payment;
    }

}
