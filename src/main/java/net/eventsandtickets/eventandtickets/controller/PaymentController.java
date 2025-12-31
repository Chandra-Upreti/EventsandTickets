package net.eventsandtickets.eventandtickets.controller;

import net.eventsandtickets.eventandtickets.model.Payment;
import net.eventsandtickets.eventandtickets.repository.PaymentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentRepository repo;

    public PaymentController(PaymentRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Payment save(@RequestBody Payment payment) {
        return repo.save(payment);
    }

    @GetMapping
    public List<Payment> getAll() {
        return repo.findAll();
    }
}