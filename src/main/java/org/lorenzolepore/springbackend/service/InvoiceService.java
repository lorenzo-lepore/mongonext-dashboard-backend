package org.lorenzolepore.springbackend.service;

import java.util.List;
import java.util.Optional;

import org.lorenzolepore.springbackend.model.AggregationResult;
import org.lorenzolepore.springbackend.model.Invoice;

import org.bson.Document;

public interface InvoiceService {
    Invoice saveInvoice(Invoice invoice);
    List<Invoice> getAllInvoices();
    List<Invoice> getLatestInvoices();
    int getInvoicesNumber();
    int getSumOfPaidInvoices();
    int getSumOfPendingInvoices();
    List<Document> aggregate();
    List<AggregationResult> getFilteredInvoices(String query, int currentPage);
    Optional<Invoice> getInvoiceById(String id);
    Invoice updateInvoice(Invoice existingInvoice, String customerId, Integer amount, String status);
    boolean deleteInvoice(String id);
}
