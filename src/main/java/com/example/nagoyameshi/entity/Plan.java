package com.example.nagoyameshi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "plans")
@NoArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // プランの内部名 premium || superpremium
    @Column(name = "name")
    private String name;

    // Stipeの商品Id
    @Column(name = "stripe_price_id")
    private String priceId;

    // 表示用のプラン価格(円)
    @Column(name = "price_yen")
    private int priceYen;

    // 課金感覚 month || year など
    @Column(name = "plan_interval")
    private String interval;

    @Lob
    @Column(name = "description")
    private String description;
}
