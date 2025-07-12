package com.example.nagoyameshi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {

    public Optional<Plan> findByPriceId(String priceId);
}
