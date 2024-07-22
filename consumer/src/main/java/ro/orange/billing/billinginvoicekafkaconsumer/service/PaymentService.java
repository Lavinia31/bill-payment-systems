package ro.orange.billing.billinginvoicekafkaconsumer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.orange.billing.billinginvoicekafkaconsumer.entity.Invoice;
import ro.orange.billing.billinginvoicekafkaconsumer.entity.Payment;
import ro.orange.billing.billinginvoicekafkaconsumer.repository.InvoiceRepository;
import ro.orange.billing.billinginvoicekafkaconsumer.repository.PaymentRepository;

@Slf4j
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, InvoiceRepository invoiceRepository) {
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
    }

//    @Transactional
//    public void createPayment(Long invoiceId, Payment payment) {
//        log.warn(String.valueOf(invoiceId));
//        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new RuntimeException(String.format("Invoice with id %s not found!", invoiceId)));
//        log.warn(invoice.toString());
//        payment.setInvoice(invoice);
//        paymentRepository.save(payment);
//    }

}
