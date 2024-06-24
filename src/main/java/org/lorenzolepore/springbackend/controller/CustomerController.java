package org.lorenzolepore.springbackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.bson.Document;
import org.bson.types.ObjectId;

import org.lorenzolepore.springbackend.model.Customer;
import org.lorenzolepore.springbackend.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /* TESTING related */

    @PostMapping("/saveTest")
    public ResponseEntity<Customer> saveTest() {
        return new ResponseEntity<>(customerService.saveCustomer(new Customer(new ObjectId(), "mail@mail.com", "/imageURL.png", "Name")), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Customer Controller is working", HttpStatus.OK);
    }

    /* CRUD methods */

    @PostMapping("/save")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getFilteredAggregation")
    public ResponseEntity<List<Document>> aggregateToInvoices(@RequestParam String query) {
        return new ResponseEntity<>(customerService.aggregateToInvoices(query), HttpStatus.OK);
    }

    @GetMapping("/numberOfCustomers")
    public ResponseEntity<Integer> getNumberOfCustomers() {
        return new ResponseEntity<>(customerService.getNumberOfCustomers(), HttpStatus.OK);
    }

    @GetMapping("/numberOfPages")
    public ResponseEntity<Integer> getNumberOfPages(@RequestParam String query) {
        return new ResponseEntity<>(customerService.getNumberOfPages(query), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(
            @RequestParam String id,
            @RequestParam (required = false) Optional<String> email,
            @RequestParam (required = false) Optional<String> imageUrl,
            @RequestParam (required = false) Optional<String> name
    ) {
        Optional<Customer> existingCustomer = customerService.getCustomerById(id);

        String emailValue = email.orElse(null);
        String imageUrlValue = imageUrl.orElse(null);
        String nameValue = name.orElse(null);

        if (existingCustomer.isPresent()) {
            Customer savedCustomer = customerService.updateCustomer(existingCustomer.get(), emailValue, imageUrlValue, nameValue);
            return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteInvoice(@RequestParam String id) {
        boolean isDeleted = customerService.deleteCustomer(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                         : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
