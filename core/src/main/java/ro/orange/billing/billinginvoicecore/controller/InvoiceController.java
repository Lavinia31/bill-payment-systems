package ro.orange.billing.billinginvoicecore.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.orange.billing.billinginvoicecore.entity.Invoice;
import ro.orange.billing.billinginvoicecore.service.InvoiceService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Validated
@RequestMapping(value = "/billing/v1")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @GetMapping(value = "/invoices/{invoiceId}", produces = {"application/json"})
    public Invoice getInvoiceById(@PathVariable("invoiceId") Long invoiceId) {
        return invoiceService.getInvoiceById(invoiceId);
    }

    @GetMapping(value = "/invoices", produces = {"application/json"})
    public List<Invoice> findInvoicesByType(@RequestParam(name = "invoiceType") Invoice.InvoiceType invoiceType) {
        if (invoiceType != null) {
            return invoiceService.findInvoicesByType(invoiceType);
        } else {
            return invoiceService.getAllInvoices();
        }
    }
}
