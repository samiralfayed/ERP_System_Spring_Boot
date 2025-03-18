package com.dreisource.erp_system.controller;

import com.dreisource.erp_system.model.Invoice;
import com.dreisource.erp_system.repository.InvoiceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceRepository invoiceRepository;

    public InvoiceController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    // ✅ Get All Invoices with Handling for No Content
    @GetMapping
    public ResponseEntity<?> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).body("No invoices found.")
                : ResponseEntity.ok(invoices);
    }

    // ✅ Get Invoice by ID (Fixed Type Mismatch, Added Logging)
    @GetMapping("/{id}")
    public ResponseEntity<Object> getInvoiceById(@PathVariable Long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);

        if (invoice.isPresent()) {
            return ResponseEntity.ok(invoice.get());
        } else {
            System.out.println("Invoice not found for ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found");
        }
    }

    // ✅ Get Invoice by Order ID with Logging
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Object> getInvoiceByOrderId(@PathVariable Long orderId) {
        Optional<Invoice> invoice = invoiceRepository.findByOrderId(orderId);

        if (invoice.isPresent()) {
            return ResponseEntity.ok(invoice.get());
        } else {
            System.out.println("Invoice not found for Order ID: " + orderId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found for Order ID: " + orderId);
        }
    }

    // ✅ Update Invoice with Validation & Logging
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateInvoice(@PathVariable Long id, @RequestBody Invoice updatedInvoice) {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(id);

        if (invoiceOpt.isPresent()) {
            Invoice invoice = invoiceOpt.get();
            if (updatedInvoice.getTotalAmount() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Total amount must be greater than zero.");
            }
            invoice.setTotalAmount(updatedInvoice.getTotalAmount());
            invoiceRepository.save(invoice);
            System.out.println("Invoice updated successfully: ID " + id);
            return ResponseEntity.ok().body(invoice);
        } else {
            System.out.println("Failed to update: Invoice not found for ID " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found.");
        }
    }

    // ✅ Delete Invoice by ID with Logging
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteInvoiceById(@PathVariable Long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            invoiceRepository.delete(invoice.get());
            System.out.println("Invoice deleted successfully: ID " + id);
            return ResponseEntity.ok().body("Invoice deleted successfully.");
        } else {
            System.out.println("Failed to delete: Invoice not found for ID " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found.");
        }
    }

    // ✅ Delete All Invoices with Confirmation & Logging
    @DeleteMapping
    public ResponseEntity<Object> deleteAllInvoices() {
        long count = invoiceRepository.count();
        if (count == 0) {
            System.out.println("No invoices to delete.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No invoices to delete.");
        }
        invoiceRepository.deleteAll();
        System.out.println("All invoices deleted successfully. Total deleted: " + count);
        return ResponseEntity.ok().body("All invoices deleted successfully. Total deleted: " + count);
    }
}