package com.ecommerce.paymentservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.paymentservice.exception.InvalidPaymentException;
import com.ecommerce.paymentservice.model.Payment;
import com.ecommerce.paymentservice.model.PaymentRequest;
import com.ecommerce.paymentservice.model.PaymentResponse;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import com.ecommerce.paymentservice.service.PaymentService;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

	@Mock
	private PaymentRepository paymentRepository;

	@InjectMocks
	private PaymentService paymentService;

	@Test
	void processPayment_ShouldSucceed() {
		// Arrange
		PaymentRequest request = new PaymentRequest();
		request.setOrderId(1L);
		request.setAmount(199.99);
		request.setCardNumber("4111111111111111");

		Payment payment = new Payment();
		payment.setId(1L);
		payment.setStatus("SUCCESS");
		payment.setTransactionId("TX12345");

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
		PaymentRequest request = new PaymentRequest();
		request.setOrderId(1L);
		request.setAmount(199.99);
		request.setCardNumber("1234"); // Numéro de carte invalide

		// Act & Assert
		assertThrows(InvalidPaymentException.class, () -> {
			paymentService.processPayment(request);
		});
	}
}