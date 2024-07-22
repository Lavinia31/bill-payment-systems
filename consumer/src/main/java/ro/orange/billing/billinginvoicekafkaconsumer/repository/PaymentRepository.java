package ro.orange.billing.billinginvoicekafkaconsumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.orange.billing.billinginvoicekafkaconsumer.entity.Payment;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
