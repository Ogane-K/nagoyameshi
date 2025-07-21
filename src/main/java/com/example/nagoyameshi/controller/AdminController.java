package com.example.nagoyameshi.controller;

import org.springframework.boot.autoconfigure.task.TaskExecutionProperties.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.service.IndexOfAdminService;

@Controller
public class AdminController {

    private final IndexOfAdminService indexOfAdminService;

    public AdminController(IndexOfAdminService indexOfAdminService) {
        this.indexOfAdminService = indexOfAdminService;
    }

    @GetMapping("/admin")
    public String index(Model model) {

        // ビューに渡すオブジェクトの作成

        Long totalFreeMembers = safeLong(indexOfAdminService.getNumberOfPeopleByAnyRole("ROLE_FREE_MEMBER"));
        Long totalPremiumMembers = safeLong(indexOfAdminService.getNumberOfPeopleByAnyRole("ROLE_PREMIUM_MEMBER"));
        Long totalSuperPremiumMembers = safeLong(
                indexOfAdminService.getNumberOfPeopleByAnyRole("ROLE_SUPER_PREMIUM_MEMBER"));

        Long totalPaidMembers = totalPremiumMembers + totalSuperPremiumMembers;

        Long totalMembers = totalPaidMembers + totalFreeMembers;

        Long totalRestaurants = safeLong(indexOfAdminService.getTotalRestaurants());
        Long totalReservations = safeLong(indexOfAdminService.getTotalReservations());

        Long salesForThisMonth = (300 * totalPremiumMembers) + (500 * totalSuperPremiumMembers);

        model.addAttribute("totalFreeMembers", totalFreeMembers);
        model.addAttribute("totalPaidMembers", totalPaidMembers);
        model.addAttribute("totalMembers", totalMembers);
        model.addAttribute("totalRestaurants", totalRestaurants);
        model.addAttribute("totalReservations", totalReservations);
        model.addAttribute("salesForThisMonth", salesForThisMonth);

        return "admin/index";
    }

    private Long safeLong(Long value) {
        return value != null ? value : -1;
    }

}
