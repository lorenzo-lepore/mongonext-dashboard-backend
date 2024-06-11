package org.lorenzolepore.springbackend.controller;

import org.lorenzolepore.springbackend.model.Revenue;
import org.lorenzolepore.springbackend.service.RevenueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/revenues")
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    @GetMapping("/test")
    public String test() {
        return "Revenue Controller is working";
    }

    @GetMapping("/savetest")
    public String saveTest() {
        Revenue revenue = new Revenue("Dec", 12345);
        revenueService.saveRevenue(revenue);
        return "Revenue saved.";
    }

    @GetMapping("/all")
    public List<Revenue> getAllRevenues() {
        return revenueService.getAllRevenues();
    }
}
