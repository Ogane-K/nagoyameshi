package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Term;
import com.example.nagoyameshi.form.TermEditForm;
import com.example.nagoyameshi.service.TermService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminTermController {

    private final TermService termService;

    public AdminTermController(TermService termService) {
        this.termService = termService;
    }

    // 利用規約画面の表示
    @GetMapping("/admin/terms")
    public String index(RedirectAttributes redirectAttributes, Model model) {

        // 利用規約の雛形を作成
        Term term;

        // 利用規約を取得する できない場合はトップページにリダイレクト
        term = getTermOrRedirect(redirectAttributes);
        if (term == null)
            return "redirect:/admin";

        model.addAttribute("term", term);
        return "admin/terms/index";
    }

    // 利用規約の編集画面の表示
    @GetMapping("/admin/terms/edit")
    public String edit(@ModelAttribute TermEditForm editForm,
            RedirectAttributes redirectAttributes, Model model) {

        // 利用規約の雛形を作成
        Term term;

        // 利用規約を取得する できない場合はトップページにリダイレクト
        term = getTermOrRedirect(redirectAttributes);
        if (term == null)
            return "redirect:/admin";

        // フォームクラスに取得した利用規約情報をセット
        editForm.setContent(term.getContent());

        model.addAttribute("termEditForm", editForm);
        return "admin/terms/edit";
    }

    // 利用規約の編集内容の保存
    @PostMapping("/admin/terms/update")
    public String update(@ModelAttribute @Validated TermEditForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        // バリデーションエラーチェック
        if (bindingResult.hasErrors()) {
            return "admin/terms/edit";
        }

        // 利用規約の雛形を作成
        Term term;

        // 利用規約を取得する できない場合はトップページにリダイレクト
        term = getTermOrRedirect(redirectAttributes);
        if (term == null)
            return "redirect:/admin";

        // termエンティティにフォームクラスの内容をセット
        term.setContent(form.getContent());

        // 利用規約の更新
        termService.updateTerm(term);

        redirectAttributes.addFlashAttribute("successMessage", "利用規約を編集しました。");
        return "redirect:/admin/terms";
    }

    // 利用規約取得の共通化メソッド
    private Term getTermOrRedirect(RedirectAttributes redirectAttributes) {
        try {
            return termService.findFirstTermByOrderByIdDesc();
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "利用規約が見つかりませんでした。");
            return null;
        }
    }

}
