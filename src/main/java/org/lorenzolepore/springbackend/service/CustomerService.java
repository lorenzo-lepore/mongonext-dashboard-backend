package org.lorenzolepore.springbackend.service;

import org.lorenzolepore.springbackend.model.Customer;

import java.util.List;
import java.util.Optional;

import org.bson.Document;

public interface CustomerService {
    public void saveCustomer(Customer customer);
    public List<Customer> getAllCustomers();
    public String getCustomerName(String id);
    public int getNumberOfCustomers();
    public int getNumberOfPages(String query);
    public List<Document> aggregateToInvoices(String query);
    public Optional<Customer> getCustomerById(String id);
}
