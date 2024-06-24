package org.lorenzolepore.springbackend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import org.lorenzolepore.springbackend.model.Invoice;

@Repository
public interface InvoiceRepository extends MongoRepository<Invoice,String> {
    List<Invoice> findTop5ByOrderByDateDesc();
    List<Invoice> findByStatus(String status);
    void deleteById(String id);
}
