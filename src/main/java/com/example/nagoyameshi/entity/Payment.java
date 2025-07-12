package com.example.nagoyameshi.entity;

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
@Table(name = "payments")
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Stripe内の支払い情報id
    @Column(name = "payment_method_id")
    private String paymentMethodId;

    // カードのブランド visa || MasterCard など
    @Column(name = "card_brand")
    private String cardBrand;

    // カードの最後尾4文字
    @Column(name = "last4_number")
    private String last4Number;

    // 有効期限（月)
    @Column(name = "exp_month")
    private Integer expMonth;

    // 有効期限（年）
    @Column(name = "exp_year")
    private Integer expYear;

    // デフォルトの支払い情報に指定しているかフラグ
    @Column(name = "is_default")
    private boolean isDefault;

    // 支払い方法が有効かフラグ
    @Column(name = "is_active")
    private boolean isActive;

}
