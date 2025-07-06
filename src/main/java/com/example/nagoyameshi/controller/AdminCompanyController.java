package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Company;
import com.example.nagoyameshi.form.CompanyEditForm;
import com.example.nagoyameshi.service.CompanyService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminCompanyController {

    private final CompanyService companyService;

    public AdminCompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // 管理者用の会社概要ページ
    @GetMapping("/admin/company")
    public String index(RedirectAttributes redirectAttributes, Model model) {

        // 会社概要の雛形を作成
        Company company;

        // 会社概要を取得 存在しなければ管理者トップページにリダイレクト
        company = getCompanyOrRedirect(redirectAttributes);
        if (company == null)
            return "redirect:/admin";

        model.addAttribute("company", company);
        return "admin/company/index";
    }

    // 管理者用の編集ページ
    @GetMapping("/admin/company/edit")
    public String edit(@ModelAttribute CompanyEditForm editForm,
            RedirectAttributes redirectAttributes,
            Model model) {

        // 会社概要の雛形を作成
        Company company;

        // 会社概要を取得 存在しなければ管理者トップページにリダイレクト
        company = getCompanyOrRedirect(redirectAttributes);
        if (company == null)
            return "redirect:/admin";

        editForm.setName(company.getName());
        editForm.setAddress(company.getAddress());
        editForm.setPostalCode(company.getPostalCode());
        editForm.setRepresentative(company.getRepresentative());
        editForm.setEstablishmentDate(company.getEstablishmentDate());
        editForm.setCapital(company.getCapital());
        editForm.setBusiness(company.getBusiness());
        editForm.setNumberOfEmployees(company.getNumberOfEmployees());

        model.addAttribute("companyEditForm", editForm);

        return "admin/company/edit";
    }

    @PostMapping("/admin/company/update")
    public String postMethodName(@ModelAttribute @Validated CompanyEditForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        // バリデーションエラーチェック
        if (bindingResult.hasErrors()) {
            return "admin/company/edit";
        }

        // 会社概要の雛形を作成
        Company company;

        // 会社概要を取得 存在しなければ管理者トップページにリダイレクト
        company = getCompanyOrRedirect(redirectAttributes);
        if (company == null)
            return "redirect:/admin";

        company.setName(form.getName());
        company.setPostalCode(form.getPostalCode());
        company.setAddress(form.getAddress());
        company.setRepresentative(form.getRepresentative());
        company.setEstablishmentDate(form.getEstablishmentDate());
        company.setCapital(form.getCapital());
        company.setBusiness(form.getBusiness());
        company.setNumberOfEmployees(form.getNumberOfEmployees());

        companyService.updateCompany(company);
        redirectAttributes.addFlashAttribute("successMessage", "会社概要を編集しました。");
        return "redirect:/admin/company";
    }

    // 会社取得の共通化メソッド
    private Company getCompanyOrRedirect(RedirectAttributes redirectAttributes) {
        try {
            return companyService.findFirstCompanyByOrderByIdDesc();
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "会社情報が見つかりませんでした。");
            return null;
        }
    }
}