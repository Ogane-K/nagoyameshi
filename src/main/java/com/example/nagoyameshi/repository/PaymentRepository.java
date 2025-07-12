package com.example.nagoyameshi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.Payment;
import com.example.nagoyameshi.entity.User;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    public Optional<Payment> findByUser(User user);
}
