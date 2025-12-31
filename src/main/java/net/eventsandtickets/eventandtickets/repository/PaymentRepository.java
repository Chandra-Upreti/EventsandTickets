package net.eventsandtickets.eventandtickets.repository;

import net.eventsandtickets.eventandtickets.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}