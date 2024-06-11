package org.lorenzolepore.springbackend.service;

import org.lorenzolepore.springbackend.model.AggregationResult;
import org.lorenzolepore.springbackend.model.Invoice;

import org.bson.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    public void saveInvoice(Invoice invoice);
    public void deleteInvoice(String id);
    public void updateInvoice(String id, String customerId, int amount, String status);
    public List<Invoice> getAllInvoices();
    public List<Invoice> getLatestInvoices();
    public int getInvoicesNumber();
    public int getSumOfPaidInvoices();
    public int getSumOfPendingInvoices();
    public List<Document> aggregate();
    public List<AggregationResult> getFilteredInvoices(String query, int currentPage);
    public Optional<Invoice> getInvoiceById(String id);
}
