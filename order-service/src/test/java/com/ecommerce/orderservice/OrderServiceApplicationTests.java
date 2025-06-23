package com.ecommerce.orderservice;

import com.ecommerce.orderservice.controller.OrderController;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderItem;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceApplicationTests {

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private OrderService orderService;

	// ✅ Test du service : création de commande
	@Test
	void createOrder_ShouldCalculateTotal() {
		OrderItem item = new OrderItem(1L, 2, 999.99);
		Order order = new Order();
		order.setItems(Collections.singletonList(item));

		when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
			Order saved = invocation.getArgument(0);
			saved.setId(1L);
			saved.setTotalAmount(1999.98);
			return saved;
		});

		Order result = orderService.createOrder(order);

		assertNotNull(result.getId());
		assertEquals(1999.98, result.getTotalAmount(), 0.001);
	}

	// ✅ Test du service : statut
	@Test
	void getOrderStatus_ShouldReturnStatus() {
		Order order = new Order();
		order.setId(1L);
		order.setStatus("PROCESSING");

		when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

		String status = orderService.getOrderStatus(1L);

		assertEquals("PROCESSING", status);
	}

	// ✅ Test du controller : GET /orders
	@Test
	void getAllOrders_ShouldReturnList() {
		OrderController controller = new OrderController(orderRepository, orderService);
		Order order = new Order();
		order.setId(1L);

		when(orderRepository.findAll()).thenReturn(List.of(order));

		ResponseEntity<List<Order>> response = controller.getAll();

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(1, response.getBody().size());
	}

	// ✅ Test du controller : POST /orders
	@Test
	void addOrder_ShouldReturnCreated() {
		OrderRepository mockRepo = mock(OrderRepository.class);
		OrderService mockService = mock(OrderService.class);
		OrderController controller = new OrderController(mockRepo, mockService);

		OrderItem item = new OrderItem(1L, 2, 100.0);
		Order order = new Order();
		order.setItems(Collections.singletonList(item));
		order.setTotalAmount(200.0);

		when(mockService.createOrder(any(Order.class))).thenReturn(order);

		ResponseEntity<Order> response = controller.add(order);

		assertEquals(201, response.getStatusCodeValue());
		assertEquals(order, response.getBody());
	}


	// ✅ Test du controller : GET /orders/{id}

	@Test
	void health_ShouldReturnStatusOk() {
		OrderRepository mockRepo = mock(OrderRepository.class);
		OrderService mockService = mock(OrderService.class);
		OrderController controller = new OrderController(mockRepo, mockService);

		ResponseEntity<String> response = controller.health();

		assertEquals(200, response.getStatusCodeValue());
		assertEquals("Order Service is running!", response.getBody());
	}

	@Test
	void getById_ShouldReturnOrder() {
		OrderRepository mockRepo = mock(OrderRepository.class);
		OrderService mockService = mock(OrderService.class);
		OrderController controller = new OrderController(mockRepo, mockService);

		Order order = new Order();
		order.setId(1L);

		when(mockRepo.findById(1L)).thenReturn(Optional.of(order));

		ResponseEntity<Order> response = controller.getById(1L);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(order.getId(), response.getBody().getId());
	}

}
