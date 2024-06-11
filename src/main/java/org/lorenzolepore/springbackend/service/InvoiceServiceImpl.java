package org.lorenzolepore.springbackend.service;

import org.bson.types.ObjectId;
import org.lorenzolepore.springbackend.model.AggregationResult;
import org.lorenzolepore.springbackend.model.Invoice;
import org.lorenzolepore.springbackend.repository.InvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final int ITEMS_PER_PAGE = 6;

    @Override
    public void saveInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(String id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public void updateInvoice(String id, String customerId, int amount, String status) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);

        if (invoice.isPresent()) {
            Invoice extractedInvoice = invoice.get();

            extractedInvoice.setCustomerId(new ObjectId(customerId));
            extractedInvoice.setAmount(amount);
            extractedInvoice.setStatus(status);

            invoiceRepository.save(extractedInvoice);
        } else {
            throw new RuntimeException("Invoice not found");
        }
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Optional<Invoice> getInvoiceById(String id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public List<Invoice> getLatestInvoices() {
        return invoiceRepository.findTop5ByOrderByDateDesc();
    }

    @Override
    public int getInvoicesNumber() {
        return invoiceRepository.findAll().size();
    }

    @Override
    public int getSumOfPaidInvoices() {
        int sum = 0;
        List<Invoice> paidInvoices = invoiceRepository.findByStatus("paid");

        for (Invoice invoice: paidInvoices) sum += invoice.getAmount();

        return sum;
    }

    @Override
    public int getSumOfPendingInvoices() {
        int sum = 0;
        List<Invoice> paidInvoices = invoiceRepository.findByStatus("pending");

        for (Invoice invoice: paidInvoices) sum += invoice.getAmount();

        return sum;
    }

    @Override
    public List<Document> aggregate() {
        List<AggregationOperation> operations = new ArrayList<>();

        operations.add(Aggregation.lookup("customers", "customer_id", "_id", "customer"));
        operations.add(Aggregation.match(
                new Criteria().orOperator(
                        Criteria.where("customer.name").regex("delba", "i")
                )
        ));

        Aggregation aggregation = Aggregation.newAggregation(operations);

        return mongoTemplate.aggregate(aggregation, "invoices", Document.class).getMappedResults();
    }

    public List<AggregationResult> getFilteredInvoices(String query, int currentPage) {
        int offset = (currentPage - 1) * ITEMS_PER_PAGE;

        List<AggregationOperation> operations = new ArrayList<>();

        operations.add(Aggregation.lookup("customers", "customer_id", "_id", "customer"));
        operations.add(Aggregation.match(
                new Criteria().orOperator(
                        Criteria.where("customer.name").regex(query, "i")
                )
        ));
        operations.add(Aggregation.sort(Sort.Direction.DESC, "date"));
        operations.add(Aggregation.skip((long) offset));
        operations.add(Aggregation.limit(ITEMS_PER_PAGE));

        Aggregation aggregation = Aggregation.newAggregation(operations);

        return mongoTemplate.aggregate(aggregation, "invoices", AggregationResult.class).getMappedResults();
    }
}
