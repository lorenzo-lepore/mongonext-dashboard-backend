package org.lorenzolepore.springbackend.repository;

import org.lorenzolepore.springbackend.model.Invoice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends MongoRepository<Invoice,String> {
    List<Invoice> findTop5ByOrderByDateDesc();
    List<Invoice> findByStatus(String status);
    Invoice findInvoicesById(String id);
    void deleteById(String id);
}
