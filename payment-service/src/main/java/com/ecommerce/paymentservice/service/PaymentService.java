package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.exception.InvalidPaymentException;
import com.ecommerce.paymentservice.model.Payment;
import com.ecommerce.paymentservice.model.PaymentRequest;
import com.ecommerce.paymentservice.model.PaymentResponse;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public PaymentResponse processPayment(PaymentRequest request) {
        // Validation basique de la carte
        if (request.getCardNumber() == null || request.getCardNumber().length() < 16) {
            throw new InvalidPaymentException("Numéro de carte invalide");
        }

        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setStatus("SUCCESS");
        payment.setTransactionId("TX-" + System.currentTimeMillis());

        Payment savedPayment = repository.save(payment);

        return new PaymentResponse(
                savedPayment.getStatus(),
                savedPayment.getTransactionId()
        );
    }
}