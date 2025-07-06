package com.example.nagoyameshi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Company;

public interface CompanyRepository extends JpaRepository<Company,Integer>{

    public Optional<Company> findFirstByOrderByIdDesc();
}
