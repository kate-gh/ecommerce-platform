package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderItem;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private Order testOrder;
    private OrderItem testOrderItem;

    @BeforeEach
    void setUp() {
        testOrderItem = new OrderItem(1L, 2, 25.99);
        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setItems(Arrays.asList(testOrderItem));
        testOrder.setTotalAmount(51.98);
        testOrder.setStatus("PENDING");
    }

    // Test pour l'endpoint health (nouvelle méthode ajoutée)
    @Test
    void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/orders/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order Service is running!"));
    }

    // Test pour getAll avec gestion d'erreurs (méthode modifiée)
    @Test
    void testGetAllOrders_Success() throws Exception {
        List<Order> orders = Arrays.asList(testOrder);
        when(orderRepository.findAll()).thenReturn(orders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].totalAmount").value(51.98));
    }

    // Test pour getAll avec exception (nouveau bloc try-catch)
    @Test
    void testGetAllOrders_Exception() throws Exception {
        when(orderRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isInternalServerError());
    }

    // Test pour add avec service (méthode modifiée pour utiliser OrderService)
    @Test
    void testAddOrder_Success() throws Exception {
        when(orderService.createOrder(any(Order.class))).thenReturn(testOrder);

        String orderJson = objectMapper.writeValueAsString(testOrder);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.totalAmount").value(51.98));
    }

    // Test pour add avec exception (nouveau bloc try-catch)
    @Test
    void testAddOrder_Exception() throws Exception {
        when(orderService.createOrder(any(Order.class))).thenThrow(new RuntimeException("Service error"));

        String orderJson = objectMapper.writeValueAsString(testOrder);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isBadRequest());
    }

    // Test pour getById (nouvelle méthode ajoutée)
    @Test
    void testGetOrderById_Found() throws Exception {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.totalAmount").value(51.98));
    }

    // Test pour getById not found (nouvelle méthode ajoutée)
    @Test
    void testGetOrderById_NotFound() throws Exception {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/orders/999"))
                .andExpect(status().isNotFound());
    }

    // Test pour @CrossOrigin annotation
    @Test
    void testCrossOriginEnabled() throws Exception {
        mockMvc.perform(options("/orders")
                        .header("Origin", "http://localhost:3000")
                        .header("Access-Control-Request-Method", "POST"))
                .andExpect(status().isOk());
    }
}