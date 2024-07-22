package ro.orange.billing.billinginvoicecore.service;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import ro.orange.billing.billinginvoicecore.entity.Client;
import ro.orange.billing.billinginvoicecore.entity.Invoice;
import ro.orange.billing.billinginvoicecore.entity.Payment;
import ro.orange.billing.billinginvoicecore.entity.PaymentMethod;
import ro.orange.billing.billinginvoicecore.kafka.KafkaPayload;
import ro.orange.billing.billinginvoicecore.kafka.KafkaProducerService;
import ro.orange.billing.billinginvoicecore.repository.ClientRepository;
import ro.orange.billing.billinginvoicecore.repository.InvoiceRepository;
import ro.orange.billing.billinginvoicecore.repository.PaymentRepository;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    private final InvoiceRepository invoiceRepository;

    private final PaymentRepository paymentRepository;
    private final KafkaProducerService kafkaProducerService;


    @Autowired
    public ClientService(ClientRepository clientRepository, InvoiceRepository invoiceRepository, PaymentRepository paymentRepository, KafkaProducerService kafkaProducerService) {
        this.clientRepository = clientRepository;
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public void createClient(@Valid @RequestBody Client client) {
        clientRepository.save(client);
    }

    public Client getClientById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found!"));
    }
//    @Transactional
//    public void deleteClientById(Long clientId) {
//        if (clientRepository.existsById(clientId)) {
//            clientRepository.deleteById(clientId);
//        }
//    }

    public void updateClientPaymentMethod(Long clientId, PaymentMethod paymentMethod) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client != null) {
            client.setPaymentMethod(paymentMethod);
            clientRepository.save(client);
        }
    }

    @Transactional
    public void updateClientBalance(Long clientId, BigDecimal additionalBalance) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found!"));
        BigDecimal oldBalance = client.getBalance();
        BigDecimal newBalance = oldBalance.add(additionalBalance);
        client.setBalance(newBalance);
        clientRepository.save(client);
    }

    @Transactional
    public void processInvoicesAndPay(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found!"));
        List<Invoice> invoices = invoiceRepository.findAllByClient(client);

        for (Invoice invoice : invoices) {
            if (invoice.getPaymentStatus() == Invoice.PaymentStatus.SUCCESSFUL) {
                log.info("Invoice already paid");
            } else {
                if (invoice.getInvoiceType() == Invoice.InvoiceType.B2B) {
                    log.info("B2B payments pending development");
                } else {
                    KafkaPayload kafkaPayload = new KafkaPayload(client, invoice);
                    kafkaProducerService.sendMessage(kafkaPayload);
                }
            }
        }
    }
    public List<Invoice> getInvoiceByClient(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found!"));
        return invoiceRepository.findAllByClient(client);
    }
    public List<Payment> getClientPayments(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found!"));
        return paymentRepository.getClientPayments(clientId);
    }
@Transactional
    public void processInvoicesByIdAndPay(Long clientId, Long invoiceId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found!"));
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new RuntimeException("Invoice not found!"));

        if (invoice.getPaymentStatus() == Invoice.PaymentStatus.SUCCESSFUL) {
            log.info("Invoice already paid");
        } else {
            if (invoice.getInvoiceType() == Invoice.InvoiceType.B2B) {
                log.info("B2B payments pending development");
            } else {
                KafkaPayload kafkaPayload = new KafkaPayload(client, invoice);
                kafkaProducerService.sendMessage(kafkaPayload);
            }
        }
    }
}




