package org.lorenzolepore.springbackend.controller;

import org.bson.types.ObjectId;
import org.lorenzolepore.springbackend.model.AggregationResult;
import org.lorenzolepore.springbackend.model.Invoice;
import org.lorenzolepore.springbackend.service.InvoiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.bson.Document;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/invoices")
@CrossOrigin
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/test")
    public String test() {
        return "Invoice Controller is working";
    }

    @GetMapping("/savetest")
    public String saveTest() {
        Invoice invoice = new Invoice(new ObjectId(), 123, "pending");
        invoiceService.saveInvoice(invoice);
        return "Invoice saved.";
    }

    @GetMapping("/aggregate")
    public List<Document> aggregate() {
        return invoiceService.aggregate();
    }

    @GetMapping("/all")
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public Optional<Invoice> getInvoiceById(@PathVariable String id) {
        return invoiceService.getInvoiceById(id);
    }

    @GetMapping("/save")
    public String saveInvoice(
            @RequestParam String customerId,
            @RequestParam int amount,
            @RequestParam String status
    ) {
        Invoice invoice = new Invoice(new ObjectId(customerId), amount, status);
        invoiceService.saveInvoice(invoice);
        return "Invoice saved.";
    }

    @GetMapping("/delete")
    public String deleteInvoice(@RequestParam String id) {
        invoiceService.deleteInvoice(id);
        return "Invoice deleted.";
    }

    @GetMapping("/update")
    public String updateInvoice(
            @RequestParam String id,
            @RequestParam String customerId,
            @RequestParam int amount,
            @RequestParam String status
    ) {
        invoiceService.updateInvoice(id, customerId, amount, status);
        return "Invoice updated.";
    }

    @GetMapping("/latest")
    public List<Invoice> getLatestInvoice() {
        return invoiceService.getLatestInvoices();
    }

    @GetMapping("/numberOfInvoices")
    public int getInvoicesNumber() {
        return invoiceService.getInvoicesNumber();
    }

    @GetMapping("/sumOfPaidInvoices")
    public int getSumOfPaidInvoices() {
        return invoiceService.getSumOfPaidInvoices();
    }

    @GetMapping("/sumOfPendingInvoices")
    public int getSumOfPendingInvoices() {
        return invoiceService.getSumOfPendingInvoices();
    }

    @GetMapping("/getFilteredInvoices")
    public List<AggregationResult> getFilteredInvoices(
            @RequestParam String query,
            @RequestParam int currentPage
    ) {
        return invoiceService.getFilteredInvoices(query, currentPage);
    }
}
