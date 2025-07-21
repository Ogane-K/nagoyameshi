package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Company;
import com.example.nagoyameshi.service.CompanyService;

@Controller
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // 一般会員用の会社概要ページ
    @GetMapping("/company")
    public String index(RedirectAttributes redirectAttributes,
            Model model) {

        Company company = new Company();

        try {
            company = companyService.findFirstCompanyByOrderByIdDesc();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "会社概要情報にアクセスできませんでした。");
            return "redirect:/";
        }

        model.addAttribute("company", company);

        return "company/index";
    }

}
