package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Term;
import com.example.nagoyameshi.service.TermService;

@Controller
public class TermController {

    private final TermService termService;

    public TermController(TermService termService) {
        this.termService = termService;
    }

    @GetMapping("/terms")
    public String index(RedirectAttributes redirectAttributes,
            Model model) {

        Term term = new Term();

        try {
            term = termService.findFirstTermByOrderByIdDesc();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMassage", "利用規約情報の取得に失敗しました。");
            return "redirect:/";
        }

        model.addAttribute("term", term);

        return "terms/index";
    }

}
