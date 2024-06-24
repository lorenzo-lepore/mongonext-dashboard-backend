package org.lorenzolepore.springbackend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import org.lorenzolepore.springbackend.model.Revenue;

@Repository
public interface RevenueRepository extends MongoRepository<Revenue,String> {
    Optional<Revenue> findByMonth(String month);
    Optional<Revenue> findByMonthAndAmount(String month, int amount);
    void deleteByMonthAndAmount(String month, int amount);
}
