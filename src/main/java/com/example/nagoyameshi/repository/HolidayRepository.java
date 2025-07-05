package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.Holiday;
import com.example.nagoyameshi.entity.Restaurant;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Integer> {

    public List<Holiday> findByRestaurant(Restaurant restaurant);

    // 店舗情報に合致する休日情報を削除
    public void deleteByRestaurant(Restaurant restaurant);
}
