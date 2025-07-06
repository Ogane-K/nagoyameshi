package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Company;
import com.example.nagoyameshi.repository.CompanyRepository;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // もっともIDの大きい会社概要情報を取得
    public Company findFirstCompanyByOrderByIdDesc() {
        return companyRepository.findFirstByOrderByIdDesc()
                .orElseThrow(() -> new IllegalStateException("会社概要情報が存在しません"));
    }

    // 会社概要情報を更新
    @Transactional
    public void updateCompany(Company company) {
        companyRepository.save(company);
    }

}
