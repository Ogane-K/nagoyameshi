package com.example.nagoyameshi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Term;

public interface TermRepository extends JpaRepository<Term, Integer> {

    public Optional<Term> findFirstByOrderByIdDesc();
}
