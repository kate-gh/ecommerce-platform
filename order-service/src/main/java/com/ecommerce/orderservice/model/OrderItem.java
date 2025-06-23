package com.ecommerce.orderservice.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    void testOrderItemDefaultConstructor() {
        OrderItem item = new OrderItem();

        assertNull(item.getId());
        assertNull(item.getProductId());
        assertNull(item.getQuantity());
        assertNull(item.getPrice());
        assertNull(item.getOrder());
    }

    @Test
    void testOrderItemConstructorWithParameters() {
        OrderItem item = new OrderItem(1L, 5, 29.99);

        assertEquals(1L, item.getProductId());
        assertEquals(5, item.getQuantity());
        assertEquals(29.99, item.getPrice());
        assertNull(item.getOrder()); // Order n'est pas défini dans ce constructeur
    }

    @Test
    void testOrderItemGettersAndSetters() {
        OrderItem item = new OrderItem();
        Order order = new Order();

        item.setId(1L);
        item.setProductId(100L);
        item.setQuantity(3);
        item.setPrice(15.75);
        item.setOrder(order);

        assertEquals(1L, item.getId());
        assertEquals(100L, item.getProductId());
        assertEquals(3, item.getQuantity());
        assertEquals(15.75, item.getPrice());
        assertEquals(order, item.getOrder());
    }

    // Test spécifique pour @JsonBackReference (évite la sérialisation cyclique)
    @Test
    void testBidirectionalRelationWithOrder() {
        Order order = new Order();
        OrderItem item = new OrderItem(1L, 2, 25.0);

        // Établir la relation
        item.setOrder(order);
        order.setItems(java.util.Arrays.asList(item));

        // Vérifier la relation bidirectionnelle
        assertEquals(order, item.getOrder());
        assertTrue(order.getItems().contains(item));
        assertEquals(1, order.getItems().size());
    }

    @Test
    void testOrderItemWithNullValues() {
        OrderItem item = new OrderItem();

        // Test avec des valeurs null
        item.setProductId(null);
        item.setQuantity(null);
        item.setPrice(null);
        item.setOrder(null);

        assertNull(item.getProductId());
        assertNull(item.getQuantity());
        assertNull(item.getPrice());
        assertNull(item.getOrder());
    }
}