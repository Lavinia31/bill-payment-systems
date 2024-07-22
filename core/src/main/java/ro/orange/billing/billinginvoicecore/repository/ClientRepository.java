package ro.orange.billing.billinginvoicecore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.orange.billing.billinginvoicecore.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}