package org.lorenzolepore.springbackend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import org.lorenzolepore.springbackend.model.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer,String> {
    List<Customer> findAllByOrderByNameDesc();
}
