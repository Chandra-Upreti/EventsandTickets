package net.eventsandtickets.eventandtickets.controller;

import net.eventsandtickets.eventandtickets.model.Booking;
import net.eventsandtickets.eventandtickets.repository.BookingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingRepository repo;

    public BookingController(BookingRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Booking save(@RequestBody Booking booking) {
        return repo.save(booking);
    }

    @GetMapping
    public List<Booking> getAll() {
        return repo.findAll();
    }
}