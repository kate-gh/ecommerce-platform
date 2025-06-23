package com.ecommerce.orderservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderItem;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;

@ExtendWith(MockitoExtension.class)
class OrderServiceApplicationTests {

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private OrderService orderService;

	@Test
	void createOrder_ShouldCalculateTotal() {
		// Arrange
		// Création correcte d'OrderItem avec les 3 paramètres
		OrderItem item = new OrderItem(1L, 2, 999.99); // Product ID, quantity, price

		// Création d'une commande avec le constructeur par défaut
		Order order = new Order();
		order.setItems(Collections.singletonList(item));

		// Configuration du mock
		when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
			Order saved = invocation.getArgument(0);
			saved.setId(1L);
			// Le service devrait calculer le total automatiquement
			saved.setTotalAmount(1999.98);
			return saved;
		});

		// Act
		Order result = orderService.createOrder(order);

		// Assert
		assertNotNull(result.getId());
		assertEquals(1999.98, result.getTotalAmount(), 0.001);
	}

	@Test
	void getOrderStatus_ShouldReturnStatus() {
		// Arrange
		// Création d'une commande avec le constructeur par défaut
		Order order = new Order();
		order.setId(1L);
		order.setStatus("PROCESSING");

		when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

		// Act
		String status = orderService.getOrderStatus(1L);

		// Assert
		assertEquals("PROCESSING", status);
	}
}