package ro.orange.billing.billinginvoicekafkaconsumer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.orange.billing.billinginvoicecore.kafka.KafkaPayload;
import ro.orange.billing.billinginvoicekafkaconsumer.entity.Client;
import ro.orange.billing.billinginvoicekafkaconsumer.entity.Invoice;
import ro.orange.billing.billinginvoicekafkaconsumer.entity.Payment;
import ro.orange.billing.billinginvoicekafkaconsumer.repository.ClientRepository;
import ro.orange.billing.billinginvoicekafkaconsumer.repository.InvoiceRepository;
import ro.orange.billing.billinginvoicekafkaconsumer.repository.PaymentRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static ro.orange.billing.billinginvoicekafkaconsumer.entity.Invoice.InvoiceType.B2B;


@Slf4j
@Service
public class KafkaConsumerService {

    public static final String SUCCESSFUL_PAYMENT_STATUS_CODE = "00";
    public static final String SUCCESSFUL_PAYMENT_STATUS_MESSAGE = "Approved or completed successfully";
    public static final String FAILED_PAYMENT_STATUS_CODE = "51";
    public static final String FAILED_PAYMENT_STATUS_MESSAGE = "Not sufficient funds";
    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public KafkaConsumerService( PaymentRepository paymentRepository, InvoiceRepository invoiceRepository, ClientRepository clientRepository) {
        this.paymentRepository = paymentRepository;

        this.invoiceRepository = invoiceRepository;
        this.clientRepository = clientRepository;
    }


   @Transactional
    @KafkaListener(topics = "#{'${kafka.topics}'.split(',')}", containerFactory = "kafkaListenerContainerFactory")
    public void consumeAll(@Payload KafkaPayload message) {
        Client client = clientRepository.findById(message.getClient().getClientId()).orElseThrow(( ) -> new RuntimeException("Client not found.")) ;
        Invoice invoice = invoiceRepository.findById(message.getInvoice().getInvoiceId()).orElseThrow(() -> new RuntimeException("Invoice not found"));

        log.info("Received KafkaPayload: ");
        log.info("Client: " + client);
        log.info("Invoice: " + invoice);

        if (B2B.equals(invoice.getInvoiceType())) {
            log.info("Processing B2B Invoice for Client: " + client.getClientId());
        } else {
            BigDecimal invoiceAmount = invoice.getAmount();
            BigDecimal clientBalance = client.getBalance();

            if (clientBalance.compareTo(invoiceAmount) >= 0) {

                updateClientBalance(client, invoiceAmount, clientBalance);
                createSuccessfulPayment(client, invoice);
                updateInvoicePaymentStatus(invoice);

            } else {
                log.info("Insufficient balance for Invoice ID: " + invoice.getInvoiceId() + "\nBalance: " + clientBalance + "\nInvoice amount: " + invoiceAmount);
                createFailedPayment(client, invoice);
                updateFailedInvoicePaymentStatus(invoice);
            }
        }
    }

    @Transactional
    public void updateFailedInvoicePaymentStatus(Invoice invoice) {
        invoice.setPaymentStatus(Invoice.PaymentStatus.FAILED);
        invoiceRepository.save(invoice);
        log.info("Invoice payment status: " + invoice.getPaymentStatus().toString());
    }

    private void createFailedPayment(Client client, Invoice invoice) {
        Payment payment = Payment.builder()
                .invoice(invoice)
                .paymentDate(LocalDateTime.now())
                .cardDigits(client.getPaymentMethod().getCardDigits())
                .statusCode(FAILED_PAYMENT_STATUS_CODE)
                .statusMessage(FAILED_PAYMENT_STATUS_MESSAGE)
                .build();
        paymentRepository.save(payment);
        log.info("Payment saved");
    }

    @Transactional
    public void updateInvoicePaymentStatus(Invoice invoice) {
        invoice.setPaymentStatus(Invoice.PaymentStatus.SUCCESSFUL);
        invoiceRepository.save(invoice);
        log.info("Invoice payment status: " + invoice.getPaymentStatus().toString());
    }

    private void createSuccessfulPayment(Client client, Invoice invoice) {
        Payment payment = Payment.builder()
                .invoice(invoice)
                .paymentDate(LocalDateTime.now())
                .cardDigits(client.getPaymentMethod().getCardDigits())
                .statusCode(SUCCESSFUL_PAYMENT_STATUS_CODE)
                .statusMessage(SUCCESSFUL_PAYMENT_STATUS_MESSAGE)
                .build();

        paymentRepository.save(payment);
        log.info("Payment processed for Invoice ID: " + invoice.getInvoiceId());
    }

    @Transactional
    public void updateClientBalance(Client client, BigDecimal invoiceAmount, BigDecimal clientBalance) {
        BigDecimal newBalance = clientBalance.subtract(invoiceAmount);
        client.setBalance(newBalance);
        clientRepository.save(client);
        log.info("Client balance updated" );
        log.info("New Balance: " + newBalance);
    }


}
