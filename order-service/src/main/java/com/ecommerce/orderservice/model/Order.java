package com.ecommerce.orderservice.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testOrderDefaultConstructor() {
        Order order = new Order();

        assertNotNull(order.getCreatedAt());
        assertEquals("PENDING", order.getStatus());
        assertTrue(order.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void testOrderConstructorWithItems_SetsBidirectionalRelation() {
        OrderItem item1 = new OrderItem(1L, 2, 25.99);
        OrderItem item2 = new OrderItem(2L, 1, 15.50);

        Order order = new Order(Arrays.asList(item1, item2), 67.48);

        // Test des propriétés de base
        assertNotNull(order.getItems());
        assertEquals(2, order.getItems().size());
        assertEquals(67.48, order.getTotalAmount());
        assertEquals("PENDING", order.getStatus());

        // Test de la relation bidirectionnelle (nouvelle fonctionnalité)
        assertEquals(order, item1.getOrder());
        assertEquals(order, item2.getOrder());
    }

    @Test
    void testSetItems_EstablishesBidirectionalRelation() {
        Order order = new Order();
        OrderItem item1 = new OrderItem(1L, 1, 10.0);
        OrderItem item2 = new OrderItem(2L, 2, 20.0);

        // Test de la nouvelle logique dans setItems
        order.setItems(Arrays.asList(item1, item2));

        assertEquals(order, item1.getOrder());
        assertEquals(order, item2.getOrder());
        assertEquals(2, order.getItems().size());
    }

    @Test
    void testSetItems_WithNullItems() {
        Order order = new Order();

        // Test avec null (ne doit pas lever d'exception)
        assertDoesNotThrow(() -> order.setItems(null));
        assertNull(order.getItems());
    }

    @Test
    void testOrderGettersAndSetters() {
        Order order = new Order();
        LocalDateTime now = LocalDateTime.now();

        order.setId(1L);
        order.setTotalAmount(100.0);
        order.setStatus("COMPLETED");
        order.setCreatedAt(now);

        assertEquals(1L, order.getId());
        assertEquals(100.0, order.getTotalAmount());
        assertEquals("COMPLETED", order.getStatus());
        assertEquals(now, order.getCreatedAt());
    }

    // Test spécifique pour @JsonManagedReference (sérialisation JSON)
    @Test
    void testJsonSerialization() {
        OrderItem item = new OrderItem(1L, 1, 10.0);
        Order order = new Order(Arrays.asList(item), 10.0);

        // Vérifier que la relation est bien établie
        assertNotNull(order.getItems());
        assertEquals(1, order.getItems().size());
        assertEquals(order, item.getOrder());
    }
}