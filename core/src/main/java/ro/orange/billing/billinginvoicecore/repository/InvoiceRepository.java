package ro.orange.billing.billinginvoicecore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.orange.billing.billinginvoicecore.entity.Client;
import ro.orange.billing.billinginvoicecore.entity.Invoice;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByInvoiceType(Invoice.InvoiceType invoiceType);
    List<Invoice> findAllByClient(Client client);
}