package com.example.nagoyameshi.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.exception.ReservationNotFoundException;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;

    public ReservationService(ReservationRepository reservationRepository,
            RestaurantRepository restaurantRepository) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
    }

    // 指定したidの予約を検索
    public Reservation findReservationById(Integer id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("予約情報が見つかりません。"));
    }

    // ユーザーに紐付いた予約を登録日時順にならべてページング取得
    public Page<Reservation> findByUserByLocaldateDesc(User user, Pageable pageable) {
        return reservationRepository.findByUserOrderByReservedDatetimeDesc(user, pageable);
    }

    // 全ての予約数のカウント
    public Long countReservations() {
        return reservationRepository.count();
    }

    // 最もidの大きい予約を取得
    public Reservation findFirstByIdDesc() {
        return reservationRepository.findFirstByOrderByIdDesc()
                .orElseThrow(() -> new ReservationNotFoundException("予約情報が見つかりません。"));
    }

    // 予約を作成
    @Transactional
    public void createReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    // 予約を削除
    @Transactional
    public void deleteReservation(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    // ReservationDateTime(予約日時)と現在の時間が2時間以上離れているか 離れているならtrue
    public boolean iaAtLeastTwoHourslnFuture(LocalDateTime ReservationDateTime) {
        LocalDateTime nowTime = LocalDateTime.now();
        long hours = ChronoUnit.HOURS.between(ReservationDateTime, nowTime);

        return Math.abs(hours) >= 2;
    }
}
