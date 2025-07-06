package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Term;
import com.example.nagoyameshi.repository.TermRepository;



@Service
public class TermService {

    private final TermRepository termRepository;

    public TermService(TermRepository termRepository) {
        this.termRepository = termRepository;
    }

    // もっともIDの大きい利用規約情報を取得
    public Term findFirstTermByOrderByIdDesc() {
        return termRepository.findFirstByOrderByIdDesc()
                .orElseThrow(() -> new IllegalStateException("利用規約が存在しません"));
    }

    // 利用規約情報の更新
    @Transactional
    public void updateTerm(Term term) {

        termRepository.save(term);
    }

}
