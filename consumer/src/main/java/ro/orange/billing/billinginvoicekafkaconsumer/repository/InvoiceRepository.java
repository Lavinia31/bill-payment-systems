package ro.orange.billing.billinginvoicekafkaconsumer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.orange.billing.billinginvoicekafkaconsumer.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}