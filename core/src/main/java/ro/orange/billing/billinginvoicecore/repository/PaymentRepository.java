package ro.orange.billing.billinginvoicecore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.orange.billing.billinginvoicecore.entity.Invoice;
import ro.orange.billing.billinginvoicecore.entity.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query(
            value = "SELECT p " +
                    "FROM Payment p " +
                    "WHERE p.invoice.client.clientId = :clientId "
    )
    List<Payment> getClientPayments(@Param("clientId") Long clientId);
}
