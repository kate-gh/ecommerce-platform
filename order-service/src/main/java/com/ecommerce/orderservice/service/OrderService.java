package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        // Calculer le montant total
        double total = order.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();

        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    public String getOrderStatus(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(Order::getStatus).orElse("NOT_FOUND");
    }
}