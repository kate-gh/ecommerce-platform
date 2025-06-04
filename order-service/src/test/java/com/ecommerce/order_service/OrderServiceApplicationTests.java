package com.ecommerce.order_service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private OrderService orderService;

	@Test
	void createOrder_ShouldCalculateTotal() {
		// Arrange
		OrderItem item = new OrderItem(1L, 2); // Product ID 1, quantity 2
		Order order = new Order(null, Collections.singletonList(item), 0.0);

		when(orderRepository.save(order)).thenAnswer(invocation -> {
			Order saved = invocation.getArgument(0);
			saved.setId(1L);
			return saved;
		});

		// Act
		Order result = orderService.createOrder(order);

		// Assert
		assertNotNull(result.getId());
		assertEquals(1999.98, result.getTotalAmount()); // 2 * 999.99
	}

	@Test
	void getOrderStatus_ShouldReturnStatus() {
		// Arrange
		Order order = new Order(1L, Collections.emptyList(), 0.0);
		order.setStatus("PROCESSING");
		when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

		// Act
		String status = orderService.getOrderStatus(1L);

		// Assert
		assertEquals("PROCESSING", status);
	}
}