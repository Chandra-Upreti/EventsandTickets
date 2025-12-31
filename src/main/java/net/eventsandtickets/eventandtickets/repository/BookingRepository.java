package net.eventsandtickets.eventandtickets.repository;

import net.eventsandtickets.eventandtickets.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}