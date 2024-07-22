package ro.orange.billing.billinginvoicecore.controller;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.orange.billing.billinginvoicecore.entity.Client;
import ro.orange.billing.billinginvoicecore.entity.Invoice;
import ro.orange.billing.billinginvoicecore.entity.Payment;
import ro.orange.billing.billinginvoicecore.entity.PaymentMethod;
import ro.orange.billing.billinginvoicecore.service.ClientService;
import ro.orange.billing.billinginvoicecore.service.InvoiceService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/billing/v1")
public class ClientController {

    private final ClientService clientService;
    private final InvoiceService invoiceService;

    @Autowired
    public ClientController(ClientService clientService, InvoiceService invoiceService) {
        this.clientService = clientService;
        this.invoiceService = invoiceService;
    }


    @PostMapping(value = "/clients", produces = {"application/json"}, consumes = {"application/json"})
    public void createClient(@Valid @RequestBody Client client) {
        clientService.createClient(client);
    }

    @GetMapping(value = "/clients/{clientId}", produces = {"application/json"})
    public Client getClientById(@PathVariable("clientId") Long clientId) {
        return clientService.getClientById(clientId);
    }
    @GetMapping(value ="/clients/{clientId}/getinvoices",produces = {"application/json"} )
    public List<Invoice> getInvoicesByClient(@PathVariable("clientId") Long clientId) {
        return clientService.getInvoiceByClient(clientId);
    }
    @GetMapping(value = "/clients/{clientId}/payments",produces = {"application/json"})
    public List<Payment> getClientPayments(@PathVariable("clientId") Long clientId) {
        return clientService.getClientPayments(clientId);
    }



//    @DeleteMapping(value = "/clients/{clientId}", produces = {"application/json"}, consumes = {"application/json"})
//    public void deleteClient(@PathVariable("clientId") Long clientId) {
//        clientService.deleteClientById(clientId);
//    }

    @PutMapping(value ="/clients/{clientId}/paymentMethod", produces = {"application/json"}, consumes = {"application/json"})
    public void updateClient(@PathVariable("clientId") Long clientId, @Valid @RequestBody PaymentMethod paymentMethod){
        clientService.updateClientPaymentMethod(clientId, paymentMethod);
    }

    @PostMapping(value = "/clients/{clientId}/invoices", produces = {"application/json"}, consumes = {"application/json"})
    public void createInvoice(@PathVariable("clientId") Long clientId, @RequestBody Invoice invoice) {
        invoice.setPaymentStatus(Invoice.PaymentStatus.PENDING);
        invoiceService.createInvoice(clientId, invoice);
    }

    @PutMapping(value = "/clients/{clientId}", produces = {"application/json"}, consumes = {"application/json"})
    public void updateClientBalance(@PathVariable("clientId") Long clientId, @RequestParam(name = "additionalBalance") BigDecimal additionalBalance) {
        clientService.updateClientBalance(clientId, additionalBalance);
    }

    @PostMapping(value = "/clients/{clientId}/invoices/pay", produces = {"application/json"}, consumes = {"application/json"})
    public void processInvoicesAndPay(@PathVariable("clientId") Long clientId) {
        clientService.processInvoicesAndPay(clientId);
    }

    @PostMapping(value = "/clients/{clientId}/invoices/{invoiceId}", produces = {"application/json"}, consumes = {"application/json"})
    public void processInvoicesByIdAndPay(@PathVariable("clientId") Long clientId, @PathVariable("invoiceId") Long invoiceId) {
        clientService.processInvoicesByIdAndPay(clientId, invoiceId);
    }

    @PostMapping(value = "/clients/{clientId}/invoices/bulk", produces = {"application/json"}, consumes = {"application/json"})
    public void generateBulkInvoices(@PathVariable("clientId") Long clientId, @RequestParam(name = "numberOfInvoices") int numberOfInvoices){
        invoiceService.generateBulkInvoices(clientId, numberOfInvoices);
    }



}
