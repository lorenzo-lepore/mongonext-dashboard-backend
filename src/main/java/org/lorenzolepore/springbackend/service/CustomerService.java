package org.lorenzolepore.springbackend.service;

import java.util.List;
import java.util.Optional;

import org.lorenzolepore.springbackend.model.Customer;

import org.bson.Document;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    List<Customer> getAllCustomers();
    int getNumberOfCustomers();
    int getNumberOfRelatedInvoices(String query);
    List<Document> aggregateToInvoices(String query);
    Optional<Customer> getCustomerById(String id);
    Customer updateCustomer(Customer existingCustomer, String email, String imageURL, String name);
    boolean deleteCustomer(String id);
}
