package net.eventsandtickets.eventandtickets.controller;

import net.eventsandtickets.eventandtickets.model.Event;
import net.eventsandtickets.eventandtickets.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    // ================= GET =================

    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ================= CREATE =================

    @PostMapping
    public Event createEvent(@RequestBody Event event) {

        // Initialize available tickets = capacity
        event.setAvailableTickets(event.getCapacity());

        return eventRepository.save(event);
    }

    // ================= UPDATE =================

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long id,
            @RequestBody Event updatedEvent) {

        return eventRepository.findById(id).map(event -> {

            event.setEventTitle(updatedEvent.getEventTitle());
            event.setDescription(updatedEvent.getDescription());
            event.setStatus(updatedEvent.getStatus());
            event.setEventDate(updatedEvent.getEventDate());
            event.setLocation(updatedEvent.getLocation());
            event.setPrice(updatedEvent.getPrice());
            event.setCategory(updatedEvent.getCategory());

            // If capacity changes, adjust available tickets safely
            int soldTickets = event.getCapacity() - event.getAvailableTickets();
            event.setCapacity(updatedEvent.getCapacity());
            event.setAvailableTickets(
                    Math.max(updatedEvent.getCapacity() - soldTickets, 0)
            );

            return ResponseEntity.ok(eventRepository.save(event));

        }).orElse(ResponseEntity.notFound().build());
    }

    // ================= PURCHASE TICKETS =================

    @PutMapping("/{id}/purchase/{qty}")
    public ResponseEntity<Event> purchaseTickets(
            @PathVariable Long id,
            @PathVariable int qty) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (qty <= 0) {
            return ResponseEntity.badRequest().build();
        }

        if (qty > event.getAvailableTickets()) {
            return ResponseEntity.badRequest().build();
        }

        event.setAvailableTickets(event.getAvailableTickets() - qty);

        return ResponseEntity.ok(eventRepository.save(event));
    }

    // ================= DELETE =================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {

        if (!eventRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        eventRepository.deleteById(id);
        return ResponseEntity.ok("Event deleted successfully");
    }
}