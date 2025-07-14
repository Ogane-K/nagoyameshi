package com.example.nagoyameshi.mapper;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReviewEditForm;
import com.example.nagoyameshi.form.ReviewRegisterForm;

public class ReviewMapper {

    public static Review toEntity(Review review, ReviewRegisterForm form, User user, Restaurant restaurant) {

        

        review.setScore(form.getScore());

        review.setContent(form.getContent());
        review.setUser(user);
        review.setRestaurant(restaurant);

        return review;
    }

    public static Review toEntity(Review review,ReviewEditForm form, User user, Restaurant restaurant) {


        review.setScore(form.getScore());

        review.setContent(form.getContent());
        review.setUser(user);
        review.setRestaurant(restaurant);

        return review;
    }

    public static ReviewEditForm toEditForm(Review review) {
        ReviewEditForm form = new ReviewEditForm(review.getScore(), review.getContent());

        return form;
    }

}
