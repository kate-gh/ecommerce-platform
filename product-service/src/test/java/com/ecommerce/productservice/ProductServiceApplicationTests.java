/*package com.ecommerce.productservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

// Supprimez les imports problématiques
// Utilisez les imports normaux maintenant que la structure est corrigée
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = ProductServiceApplication.class) // Une seule annotation @SpringBootTest
class ProductServiceApplicationTests {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

	@Test
	void createProduct_ShouldSucceed() {
		// Arrange
		Product product = new Product();
		product.setName("Test Product");
		product.setPrice(19.99);

		when(productRepository.save(any(Product.class))).thenReturn(product);

		// Act
		Product savedProduct = productService.saveProduct(product);

		// Assert
		assertNotNull(savedProduct);
		assertEquals("Test Product", savedProduct.getName());
		assertEquals(19.99, savedProduct.getPrice());
	}

	@Test
	void getProductById_ShouldReturnProduct() {
		// Arrange
		Long productId = 1L;
		Product product = new Product();
		product.setId(productId);
		product.setName("Test Product");

		when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));

		// Act
		Product foundProduct = productService.getProductById(productId);

		// Assert
		assertNotNull(foundProduct);
		assertEquals(productId, foundProduct.getId());
		assertEquals("Test Product", foundProduct.getName());
	}
}*/
package com.ecommerce.productservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductService;
import com.ecommerce.productservice.model.Product;

@ExtendWith(MockitoExtension.class)
public class ProductServiceApplicationTests {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

	@Test
	void createProduct_ShouldSucceed() {
		// Arrange
		Product product = new Product();
		product.setName("Test Product");
		product.setPrice(19.99);

		when(productRepository.save(any(Product.class))).thenReturn(product);

		// Act
		Product savedProduct = productService.saveProduct(product);

		// Assert
		assertNotNull(savedProduct);
		assertEquals("Test Product", savedProduct.getName());
		assertEquals(19.99, savedProduct.getPrice());
	}

	@Test
	void getProductById_ShouldReturnProduct() {
		// Arrange
		Long productId = 1L;
		Product product = new Product();
		product.setId(productId);
		product.setName("Test Product");

		when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));

		// Act
		Product foundProduct = productService.getProductById(productId);

		// Assert
		assertNotNull(foundProduct);
		assertEquals(productId, foundProduct.getId());
		assertEquals("Test Product", foundProduct.getName());
	}
}