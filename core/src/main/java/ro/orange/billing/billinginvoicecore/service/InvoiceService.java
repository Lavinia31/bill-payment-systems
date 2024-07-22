package ro.orange.billing.billinginvoicecore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.orange.billing.billinginvoicecore.entity.Client;
import ro.orange.billing.billinginvoicecore.entity.Invoice;
import ro.orange.billing.billinginvoicecore.repository.ClientRepository;
import ro.orange.billing.billinginvoicecore.repository.InvoiceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static ro.orange.billing.billinginvoicecore.entity.Client.Type.BUSINESS;

@Slf4j
@Service

public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, ClientRepository clientRepository) {
        this.invoiceRepository = invoiceRepository;
        this.clientRepository = clientRepository;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }


    @Transactional
    public void createInvoice(Long clientId, Invoice invoice) {
        log.warn(String.valueOf(clientId));
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException(String.format("Client with id %s not found!", clientId)));
        log.warn(client.toString());
        invoice.setClient(client);
        invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId).orElseThrow(() -> new RuntimeException("Invoice not found!"));
    }

    public List<Invoice> findInvoicesByType(Invoice.InvoiceType invoiceType) {
        return invoiceRepository.findByInvoiceType(invoiceType);
    }
    @Transactional
    public void generateBulkInvoices(Long clientId, int numberOfInvoices) {
        log.warn(String.valueOf(clientId));
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException(String.format("Client with id %s not found!", clientId)));
        log.warn(client.toString());
        List<Invoice> invoices = new ArrayList<>();

        for(int i = 0; i < numberOfInvoices; i++ ) {
            Invoice.InvoiceType invoiceType = (BUSINESS.equals(client.getType())) ? Invoice.InvoiceType.B2B : Invoice.InvoiceType.B2C;
            LocalDateTime currentDate = LocalDateTime.now();
            LocalDateTime issuedDate = currentDate.minusMonths(1).minusDays(ThreadLocalRandom.current().nextInt(30));
            LocalDateTime dueDate = currentDate.plusDays(15).plusDays(ThreadLocalRandom.current().nextInt(16));
            BigDecimal amount = BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(50, 101));
            String[] services = {"Cable/Satellite/Streaming TV Subscription", "Internet Subscription", "Mobile Phone Subscription"};
            String selectedService = services[ThreadLocalRandom.current().nextInt(services.length)];

            Invoice invoice = Invoice.builder()
                    .client(client)
                    .invoiceType(invoiceType)
                    .issuedDate(issuedDate)
                    .dueDate(dueDate)
                    .amount(amount)
                    .services(selectedService)
                    .paymentStatus(Invoice.PaymentStatus.PENDING)
                    .build();

            invoices.add(invoice);
        }
        invoiceRepository.saveAll(invoices);
    }
}
