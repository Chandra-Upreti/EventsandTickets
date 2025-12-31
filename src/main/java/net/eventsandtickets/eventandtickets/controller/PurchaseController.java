package net.eventsandtickets.eventandtickets.controller;

import jakarta.transaction.Transactional;
import net.eventsandtickets.eventandtickets.dto.PurchaseRequest;
import net.eventsandtickets.eventandtickets.model.*;
import net.eventsandtickets.eventandtickets.repository.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final UserRepository userRepo;
    private final EventRepository eventRepo;
    private final BookingRepository bookingRepo;
    private final PaymentRepository paymentRepo;
    private final TicketRepository ticketRepo;

    public PurchaseController(UserRepository userRepo,
                              EventRepository eventRepo,
                              BookingRepository bookingRepo,
                              PaymentRepository paymentRepo,
                              TicketRepository ticketRepo) {
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
        this.bookingRepo = bookingRepo;
        this.paymentRepo = paymentRepo;
        this.ticketRepo = ticketRepo;
    }

    @PostMapping("/confirm")
    @Transactional
    public Tickets confirmPurchase(@RequestBody PurchaseRequest req) {

        Users user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepo.findById(req.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getAvailableTickets() < req.getQuantity()) {
            throw new RuntimeException("Not enough tickets available");
        }

        // 1️⃣ BOOKING
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setEvent(event);
        booking.setStatus("CONFIRMED");
        booking.setPrice((float) req.getAmount());
        bookingRepo.save(booking);

        // 2️⃣ PAYMENT
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(req.getAmount());
        payment.setPaymentMethod(req.getPaymentMethod());
        payment.setStatus("SUCCESS");
        paymentRepo.save(payment);

        // 3️⃣ TICKET
        Tickets ticket = new Tickets();
        ticket.setBooking(booking);
        ticket.setPayment(payment);
        ticket.setEvent(event);
        ticket.setQuantity(req.getQuantity());
        ticket.setValidDate(event.getEventDate());
        ticketRepo.save(ticket);

        // 4️⃣ UPDATE EVENT TICKETS
        event.setAvailableTickets(
                event.getAvailableTickets() - req.getQuantity()
        );
        eventRepo.save(event);

        return ticket;
    }
}