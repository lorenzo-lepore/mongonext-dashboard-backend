package org.lorenzolepore.springbackend.service;

import org.lorenzolepore.springbackend.model.Revenue;
import org.lorenzolepore.springbackend.repository.RevenueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevenueServiceImpl implements RevenueService {

    @Autowired
    private RevenueRepository revenueRepository;

    @Override
    public void saveRevenue(Revenue revenue) {
        revenueRepository.save(revenue);
    }

    @Override
    public List<Revenue> getAllRevenues() {
        return revenueRepository.findAll();
    }
}
