package com.example.nagoyameshi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.DayType;
import com.example.nagoyameshi.entity.Holiday;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.repository.HolidayRepository;

@Service
public class HolidayService {

    private final HolidayRepository holidayRepository;

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    // 休日情報の更新/新規作成
    @Transactional
    public void updateHolidays(List<String> holidayCodes, Restaurant restaurant) {

        if (holidayCodes == null || holidayCodes.isEmpty()) {
            return;
        }

        // 既存の休日情報を削除
        holidayRepository.deleteByRestaurant(restaurant);
        holidayRepository.flush();

        // 新しい休日情報を作成する
        List<Holiday> holidays = holidayCodes.stream()
                .map((String code) -> {
                    DayType dayType = DayType.valueOf(code);
                    Holiday h = new Holiday();
                    h.setDayType(dayType);
                    h.setRestaurant(restaurant);
                    return h;
                })
                .collect(Collectors.toList());

        // 作成した休日情報のリストをすべて保存
        holidayRepository.saveAll(holidays);
    }

    // 休日情報を検索する
    public List<Holiday> findHolidaysByRestaurant(Restaurant restaurant) {
        return holidayRepository.findByRestaurant(restaurant);
    }

    // 休日情報をList<String>の型で検索する
    public List<String> extractHolidayCodes(Restaurant restaurant) {
        List<String> holidayCodes = findHolidaysByRestaurant(restaurant).stream()
                .map(h -> h.getDayType().name())
                .toList();

        return holidayCodes;
    }

    // 指定した店舗の休日情報を値に変換する
    public List<Integer> getrestaurantRegularHolidays(Restaurant restaurant) {
        List<Integer> restaurantRegularHolidays = findHolidaysByRestaurant(restaurant).stream()
                .map(holiday -> holiday.getDayType().getJsDayOfWeek())
                .filter(dow -> dow >= 0)
                .toList();

        return restaurantRegularHolidays;
    }
}
