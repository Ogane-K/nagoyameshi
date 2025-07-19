package com.example.nagoyameshi.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "lowestPrice")
    private Integer lowestPrice;

    @Column(name = "highestPrice")
    private Integer highestPrice;

    @Column(name = "postalCode")
    private String postalCode;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "openingTime")
    private LocalTime openingTime;

    @Column(name = "closingTime")
    private LocalTime closingTime;

    @Column(name = "seatingCapacity")
    private Integer seatingCapacity;

    @Column(name = "favoriteCount")
    private Integer favoriteCount = 0;

    @Column(name = "rating")
    private BigDecimal rating = BigDecimal.ZERO;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Holiday> Holidays = new ArrayList<>();

    @Column(name = "createdAt")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RestaurantCategory> categoriesRestaurants;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    @Transient
    public BigDecimal getAverageScore() {
        if (reviews == null || reviews.isEmpty()) {
            return BigDecimal.ZERO;
        }

        double avg = reviews.stream()
                .mapToInt(Review::getScore)
                .average()
                .orElse(0.0);

        return BigDecimal.valueOf(avg).setScale(1, RoundingMode.HALF_UP);

    }
}
