package com.example.nagoyameshi.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "companies")
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String address;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(nullable = false)
    private String representative;

    @Column(name = "establishment_date", nullable = false)
    private String establishmentDate;

    @Column(nullable = false)
    private String capital;

    @Column(nullable = false)
    private String business;

    @Column(name = "number_of_employees", nullable = false)
    private String numberOfEmployees;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
