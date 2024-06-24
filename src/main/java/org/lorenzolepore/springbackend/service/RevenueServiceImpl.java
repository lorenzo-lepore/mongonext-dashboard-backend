package org.lorenzolepore.springbackend.service;

import org.lorenzolepore.springbackend.model.Revenue;
import org.lorenzolepore.springbackend.repository.RevenueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RevenueServiceImpl implements RevenueService {

    @Autowired
    private RevenueRepository revenueRepository;

    @Override
    public Revenue saveRevenue(Revenue revenue) {
        return revenueRepository.save(revenue);
    }

    @Override
    public List<Revenue> getAllRevenues() {
        return revenueRepository.findAll();
    }

    @Override
    public Optional<Revenue> getRevenueById(String id) {
        return revenueRepository.findById(id);
    }

    @Override
    public Revenue updateRevenue(Revenue existingRevenue, int amount) {
        existingRevenue.setAmount(amount);
        return revenueRepository.save(existingRevenue);
    }

    @Override
    public boolean deleteRevenue(String id) {
        Optional<Revenue> revenue = revenueRepository.findById(id);

        if (revenue.isPresent()) {
            revenueRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
