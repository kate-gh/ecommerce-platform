package com.ecommerce.payment_service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

	@Mock
	private PaymentRepository paymentRepository;

	@InjectMocks
	private PaymentService paymentService;

	@Test
	void processPayment_ShouldSucceed() {
		// Arrange
		PaymentRequest request = new PaymentRequest(1L, 199.99, "4111111111111111");
		Payment payment = new Payment(1L, "SUCCESS", "TX12345");

		when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

		// Act
		PaymentResponse response = paymentService.processPayment(request);

		// Assert
		assertEquals("SUCCESS", response.getStatus());
		assertEquals("TX12345", response.getTransactionId());
	}

	@Test
	void processPayment_ShouldFailForInvalidCard() {
		// Arrange
		PaymentRequest request = new PaymentRequest(1L, 199.99, "1234");

		// Act & Assert
		assertThrows(InvalidPaymentException.class, () -> {
			paymentService.processPayment(request);
		});
	}
}