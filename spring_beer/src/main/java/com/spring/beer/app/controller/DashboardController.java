package com.spring.beer.app.controller;

import com.spring.beer.app.service.DashboardService;
import com.spring.beer.dom.Dashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public String getUserDashboard(Model model) {
        Dashboard dashboard = dashboardService.getUserDashboard();
        model.addAttribute("dashboard", dashboard);
        return "dashboard";
    }

}
