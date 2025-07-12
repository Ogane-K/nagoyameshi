package com.example.nagoyameshi.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Subscriptions")
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // 関連するユーザーのID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // STRIPE内部のこの契約のID
    @Column(name = "stripe_subscription_id")
    private String stripeSubscriptionId;

    // 課金プランの種類
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    // 支払い方法
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    // 契約スタート日時
    @Column(name = "start_date")
    private LocalDateTime startDate;

    // 最初の支払い日時
    @Column(name = "current_period_end")
    private LocalDateTime nexPayMentDate;

    // 契約キャンセルの申請日時
    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    // 契約がキャンセルされている状態なのかフラグ /trueでキャンセル中
    @Column(name = "is_canceled")
    private boolean isCanceled;

    @Column(name = "stripe_status")
    private String stripeStatus;
}
