package com.ecommerce.paymentservice.model;

public class PaymentResponse {
    private final String status;
    private final String transactionId;

    public PaymentResponse(String status, String transactionId) {
        this.status = status;
        this.transactionId = transactionId;
    }

    // Getters
    public String getStatus() { return status; }
    public String getTransactionId() { return transactionId; }
}