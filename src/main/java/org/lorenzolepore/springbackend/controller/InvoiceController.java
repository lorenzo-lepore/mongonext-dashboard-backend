package org.lorenzolepore.springbackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.bson.Document;
import org.bson.types.ObjectId;

import org.lorenzolepore.springbackend.model.Invoice;
import org.lorenzolepore.springbackend.model.AggregationResult;
import org.lorenzolepore.springbackend.service.InvoiceService;

@RestController
@RequestMapping("/invoices")
@CrossOrigin
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    /* TESTING RELATED */

    @PostMapping("/saveTest")
    public ResponseEntity<Invoice> saveTest() {
        return new ResponseEntity<>(invoiceService.saveInvoice(new Invoice(new ObjectId(), 123, "pending")), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Invoice Controller is working", HttpStatus.OK);
    }

    @GetMapping("/aggregate")
    public ResponseEntity<List<Document>> aggregate() {
        return new ResponseEntity<>(invoiceService.aggregate(), HttpStatus.OK);
    }

    /* CRUD METHODS */

    @PostMapping("/save")
    public ResponseEntity<Invoice> saveInvoice(
            @RequestParam String customerId,
            @RequestParam int amount,
            @RequestParam String status
    ) {
        return new ResponseEntity<>(invoiceService.saveInvoice(new Invoice(new ObjectId(customerId), amount, status)), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return new ResponseEntity<>(invoiceService.getAllInvoices(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable String id) {
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);
        if (invoice.isPresent()) {
            return new ResponseEntity<>(invoice.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<List<Invoice>> getLatestInvoice() {
        return new ResponseEntity<>(invoiceService.getLatestInvoices(), HttpStatus.OK);
    }

    @GetMapping("/numberOfInvoices")
    public ResponseEntity<Integer> getInvoicesNumber() {
        return new ResponseEntity<>(invoiceService.getInvoicesNumber(), HttpStatus.OK);
    }

    @GetMapping("/sumOfPaidInvoices")
    public ResponseEntity<Integer> getSumOfPaidInvoices() {
        return new ResponseEntity<>(invoiceService.getSumOfPaidInvoices(), HttpStatus.OK);
    }

    @GetMapping("/sumOfPendingInvoices")
    public ResponseEntity<Integer> getSumOfPendingInvoices() {
        return new ResponseEntity<>(invoiceService.getSumOfPendingInvoices(), HttpStatus.OK);
    }

    @GetMapping("/getFilteredInvoices")
    public ResponseEntity<List<AggregationResult>> getFilteredInvoices(
            @RequestParam String query,
            @RequestParam int currentPage
    ) {
        return new ResponseEntity<>(invoiceService.getFilteredInvoices(query, currentPage), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Invoice> updateInvoice(
            @RequestParam String id,
            @RequestParam (required = false) Optional<String> customerId,
            @RequestParam (required = false) Optional<Integer> amount,
            @RequestParam (required = false) Optional<String> status
    ) {
        Optional<Invoice> existingInvoice = invoiceService.getInvoiceById(id);

        String customerIdValue = customerId.orElse(null);
        Integer amountValue = amount.orElse(null);
        String statusValue = status.orElse(null);

        if (
                (customerIdValue != null && customerIdValue.length() != 24) ||
                (amountValue != null && amountValue < 0) ||
                (statusValue != null && !statusValue.equals("pending") && !statusValue.equals("paid"))
        ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (existingInvoice.isPresent()) {
            Invoice savedInvoice = invoiceService.updateInvoice(existingInvoice.get(), customerIdValue, amountValue, statusValue);
            return new ResponseEntity<>(savedInvoice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteInvoice(@RequestParam String id) {
        boolean isDeleted = invoiceService.deleteInvoice(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                         : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
