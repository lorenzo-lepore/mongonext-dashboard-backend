package org.lorenzolepore.springbackend.service;

import org.lorenzolepore.springbackend.model.Customer;
import org.lorenzolepore.springbackend.repository.CustomerRepository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import org.bson.Document;

import java.util.*;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAllByOrderByNameDesc();
    }

    @Override
    public String getCustomerName(String id) {
        Customer customer = customerRepository.findById(id).get();
        return customer.getName();
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    @Override
    public int getNumberOfCustomers() {
        return customerRepository.findAll().size();
    }

    @Override
    public int getNumberOfPages(String query) {
        List<AggregationOperation> operations = new ArrayList<>();

        operations.add(Aggregation.lookup("customers", "customer_id", "_id", "customer"));
        operations.add(Aggregation.match(
                new Criteria().orOperator(
                        Criteria.where("customer.name").regex(query, "i")
                )
        ));

        Aggregation aggregation = newAggregation(operations);

        return mongoTemplate.aggregate(aggregation, "invoices", Document.class).getMappedResults().size();
    }

    @Override
    public List<Document> aggregateToInvoices(String query) {
        List<AggregationOperation> totalPaidOperations = new ArrayList<>();
        List<AggregationOperation> totalPendingOperations = new ArrayList<>();
        List<AggregationOperation> totalInvoicesOperations = new ArrayList<>();

        Map<Object, Integer> totalPaidMap = new HashMap<>();
        Map<Object, Integer> totalPendingMap = new HashMap<>();

        // Aggregazione per ottenere il totale degli importi delle fatture in stato "paid"
        totalPaidOperations.add(Aggregation.match(Criteria.where("status").is("paid")));
        totalPaidOperations.add(Aggregation.group("customer_id").sum("amount").as("total_paid"));

        Aggregation aggregation1 = Aggregation.newAggregation(totalPaidOperations);
        AggregationResults<Document> results1 = mongoTemplate.aggregate(aggregation1, "invoices", Document.class);

        for (Document document : results1.getMappedResults()) {
            totalPaidMap.put(document.get("_id"), document.getInteger("total_paid"));
        }

        // Aggregazione per ottenere il totale degli importi delle fatture in stato "pending"
        totalPendingOperations.add(Aggregation.match(Criteria.where("status").is("pending")));
        totalPendingOperations.add(Aggregation.group("customer_id").sum("amount").as("total_pending"));

        Aggregation aggregation2 = Aggregation.newAggregation(totalPendingOperations);
        AggregationResults<Document> results2 = mongoTemplate.aggregate(aggregation2, "invoices", Document.class);

        for (Document document : results2.getMappedResults()) {
            totalPendingMap.put(document.get("_id"), document.getInteger("total_pending"));
        }

        // Aggregazione finale
        if (!query.isEmpty()) {
            totalInvoicesOperations.add(Aggregation.match(
                    new Criteria().orOperator(
                            Criteria.where("name").regex(String.valueOf(query), "i"),
                            Criteria.where("email").regex(String.valueOf(query), "i")
                    )
            ));
        }

        totalInvoicesOperations.add(Aggregation.project("id", "name", "email", "image_url"));
        totalInvoicesOperations.add(Aggregation.lookup("invoices", "_id", "customer_id", "invoices"));
        totalInvoicesOperations.add(Aggregation.project("id", "name", "email", "image_url")
                .and("invoices").size().as("total_invoices")
        );
        totalInvoicesOperations.add(Aggregation.sort(Sort.Direction.ASC, "name"));

        Aggregation aggregation3 = newAggregation(totalInvoicesOperations);
        List<Document> results3 = mongoTemplate.aggregate(aggregation3, "customers", Document.class).getMappedResults();

        for (Document document : results3) {
            Object customerId = document.get("_id");

            document.put("total_paid", totalPaidMap.getOrDefault(customerId, 0));
            document.put("total_pending", totalPendingMap.getOrDefault(customerId, 0));
        }
        return results3;
    }
}
