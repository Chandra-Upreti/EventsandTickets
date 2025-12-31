package net.eventsandtickets.eventandtickets.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Random;

@Entity
@Table(name = "tickets")
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long ticketId;   // DB primary key

    @Column(name = "ticket_code", unique = true, nullable = false)
    private String ticketCode;   // 6-digit random ticket code



    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private LocalDate purchaseDate;
    private LocalDate validDate;
    private int quantity;

    @Column(nullable = false)
    private String status;

    // ✅ Auto-generate ticket code & purchase date
    @PrePersist
    protected void onCreate() {
        this.purchaseDate = LocalDate.now();
        this.ticketCode = String.valueOf(100000 + new Random().nextInt(900000));
        this.status = "UNREAD";
    }

    // Getters and setters
    public Long getTicketId() {
        return ticketId;
    }
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketCode() {
        return ticketCode;
    }
    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Payment getPayment() {
        return payment;
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }
    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getValidDate() {
        return validDate;
    }
    public void setValidDate(LocalDate validDate) {
        this.validDate = validDate;
    }
    public int getQuantity(){
        return quantity;

    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getStatus() {
        return status; }

    public void setStatus(String status) {
        this.status = status; }
}