package net.eventsandtickets.eventandtickets.controller;

import net.eventsandtickets.eventandtickets.model.Tickets;
import net.eventsandtickets.eventandtickets.repository.TicketRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@CrossOrigin("*")
public class TicketController {

    private final TicketRepository repo;

    public TicketController(TicketRepository repo) {
        this.repo = repo;
    }

    // 🔍 VERIFY TICKET BY QR CODE
    @GetMapping("/verify/{code}")
    public String verifyTicket(@PathVariable String code) {

        Tickets ticket = repo.findByTicketCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid ticket"));

        if ("READ".equals(ticket.getStatus())) {
            return "❌ Ticket already used";
        }

        ticket.setStatus("READ");
        repo.save(ticket);

        return "✅ Ticket verified\n"
                + "Event: " + ticket.getEvent().getEventTitle()
                + "\nQuantity: " + ticket.getQuantity();
    }
}