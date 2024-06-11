package org.lorenzolepore.springbackend.service;

import org.lorenzolepore.springbackend.model.Revenue;

import java.util.List;

public interface RevenueService {
    public void saveRevenue(Revenue revenue);
    public List<Revenue> getAllRevenues();
}
