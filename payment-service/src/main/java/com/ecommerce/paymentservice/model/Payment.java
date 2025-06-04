package com.ecommerce.paymentservice.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(name = "transaction_id") // Ajoutez cette annotation
    private String transactionId;    // Ajoutez ce nouveau champ

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String status;

    @Column
    private String paymentMethod;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Constructors
    public Payment() {
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";
    }

    public Payment(Long orderId, Double amount, String paymentMethod, String transactionId) {
        this();
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId; // Initialisation du nouveau champ
    }

    public Payment(Long orderId, Double amount, String paymentMethod) {
        this();
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
