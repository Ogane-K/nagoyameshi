package com.example.nagoyameshi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.User;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    public Page<Reservation> findByUserOrderByReservedDatetimeDesc(User user, Pageable pageable);

    public Optional<Reservation> findFirstByOrderByIdDesc();
}
