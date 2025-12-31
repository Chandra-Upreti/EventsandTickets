package net.eventsandtickets.eventandtickets.repository;

import net.eventsandtickets.eventandtickets.model.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Tickets, Long> {
    Optional<Tickets> findByTicketCode(String ticketCode);
}