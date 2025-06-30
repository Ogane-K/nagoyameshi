package com.example.nagoyameshi.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurants_categories")
@Data
@NoArgsConstructor
public class RestaurantCategory {

    @EmbeddedId
    private RestaurantCategoryId id;

    @ManyToOne
    @MapsId("restaurantId")
    @JoinColumn(name = "restaurants_id")
    private Restaurant restaurant;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;
}
