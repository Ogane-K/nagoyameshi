package com.example.nagoyameshi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.Subscription;
import com.example.nagoyameshi.entity.User;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    public Optional<Subscription> findFirstByUserOrderByStartDateDesc(User user);
}
