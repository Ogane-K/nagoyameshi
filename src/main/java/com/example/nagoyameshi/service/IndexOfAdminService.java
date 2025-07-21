package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;

import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.UserRoleRepository;

@Service
public class IndexOfAdminService {

    private final UserRoleRepository userRoleRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;

    public IndexOfAdminService(UserRoleRepository userRoleRepository,
            RestaurantRepository restaurantRepository,
            ReservationRepository reservationRepository) {
        this.userRoleRepository = userRoleRepository;
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
    }

    // 指定した会員名を持つロールの数を取得
    public Long getNumberOfPeopleByAnyRole(String roleName) {
        return userRoleRepository.countByRoleName(roleName);
    }

    // 全店舗の総数を取得する
    public Long getTotalRestaurants() {
        System.out.println("Restaurants in DB: " + restaurantRepository.count());

        return restaurantRepository.count();
    }

    public Long getTotalReservations() {
        System.out.println("Reservations in DB: " + reservationRepository.count());
        return reservationRepository.count();
    }

}
