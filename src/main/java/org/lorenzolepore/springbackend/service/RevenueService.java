package org.lorenzolepore.springbackend.service;

import java.util.List;
import java.util.Optional;

import org.lorenzolepore.springbackend.model.Revenue;

public interface RevenueService {
    Revenue saveRevenue(Revenue revenue);
    Optional<Revenue> getRevenueById(String id);
    List<Revenue> getAllRevenues();
    Revenue updateRevenue(Revenue existingRevenue, int amount);
    boolean deleteRevenue(String id);
}
