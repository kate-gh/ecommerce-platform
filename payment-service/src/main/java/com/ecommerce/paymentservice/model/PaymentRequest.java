package com.ecommerce.paymentservice.model;

public class PaymentRequest {
    private Long orderId;
    private double amount;
    private String cardNumber;

    // Getters
    public Long getOrderId() { return orderId; }
    public double getAmount() { return amount; }
    public String getCardNumber() { return cardNumber; }

    // Setters
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
}