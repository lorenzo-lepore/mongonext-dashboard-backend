package org.lorenzolepore.springbackend.repository;

import org.lorenzolepore.springbackend.model.Revenue;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueRepository extends MongoRepository<Revenue,String> {
}
