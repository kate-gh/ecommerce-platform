package com.ecommerce.product_service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

	@Test
	void getProductById_ShouldReturnProduct() {
		// Arrange
		Product mockProduct = new Product(1L, "Laptop", 999.99);
		when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

		// Act
		Product result = productService.getProductById(1L);

		// Assert
		assertNotNull(result);
		assertEquals("Laptop", result.getName());
	}

	@Test
	void getProductById_ShouldThrowException() {
		// Arrange
		when(productRepository.findById(100L)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(ProductNotFoundException.class, () -> {
			productService.getProductById(100L);
		});
	}

	@Test
	void createProduct_ShouldSaveProduct() {
		// Arrange
		Product newProduct = new Product(null, "Smartphone", 599.99);
		Product savedProduct = new Product(2L, "Smartphone", 599.99);
		when(productRepository.save(newProduct)).thenReturn(savedProduct);

		// Act
		Product result = productService.createProduct(newProduct);

		// Assert
		assertNotNull(result.getId());
		assertEquals("Smartphone", result.getName());
	}
}