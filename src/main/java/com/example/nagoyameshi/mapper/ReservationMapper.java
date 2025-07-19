package com.example.nagoyameshi.mapper;

import java.time.LocalDateTime;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationRegisterForm;

public class ReservationMapper {

    public static Reservation mapToEntity(ReservationRegisterForm form, Reservation reservation, User user,
            Restaurant restaurant) {

        LocalDateTime reservedDatetime = LocalDateTime.of(form.getReservationDate(), form.getReservationTime());
        reservation.setReservedDatetime(reservedDatetime);

        reservation.setNumberOfPeople(form.getNumberOfPeople());
        reservation.setUser(user);
        reservation.setRestaurant(restaurant);

        return reservation;
    }

}
