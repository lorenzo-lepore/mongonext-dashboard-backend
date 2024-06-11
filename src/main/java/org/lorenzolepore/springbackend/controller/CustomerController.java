package org.lorenzolepore.springbackend.controller;

import org.bson.types.ObjectId;
import org.lorenzolepore.springbackend.model.Customer;
import org.lorenzolepore.springbackend.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.bson.Document;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/test")
    public String test() {
        return "Customer Controller is working";
    }

    @GetMapping("/savetest")
    public String saveTest() {
        Customer customer = new Customer(new ObjectId(), "mail@mail.com", "/imageURL.png", "Name");
        customerService.saveCustomer(customer);
        return "Customer saved.";
    }

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/id/{id}")
    public Optional<Customer> getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("numberOfCustomers")
    public int getNumberOfCustomers() {
        return customerService.getNumberOfCustomers();
    }

    @GetMapping("numberOfPages")
    public int getNumberOfPages(@RequestParam String query) {
        return customerService.getNumberOfPages(query);
    }

    @GetMapping("aggregate")
    public List<Document> aggregateToInvoices(@RequestParam String query) {
        return customerService.aggregateToInvoices(query);
    }
}
