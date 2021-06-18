package co.com.pragma.store.serviceproduct;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.com.pragma.store.serviceproduct.entity.Category;
import co.com.pragma.store.serviceproduct.entity.Product;
import co.com.pragma.store.serviceproduct.repository.ProductRepositoryInteface;
import co.com.pragma.store.serviceproduct.service.ProductService;
import co.com.pragma.store.serviceproduct.service.ProductServiceInterface;

@SpringBootTest
public class ProductServiceMockTest {

	@Autowired
	private ProductServiceInterface productService;

	@Mock
	private ProductRepositoryInteface productRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		productService = new ProductService(productRepository);
		Product computer = Product.builder().id(1L).name("computer")
				.category(Category.builder().id(1L).build()).stock(Double.parseDouble("5"))
				.price(Double.parseDouble("12.5")).build();
		Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(computer));
		Mockito.when(productRepository.save(computer)).thenReturn(computer);
	}

	@Test
	public void whenValidateGetID_ThenReturnProduct() {
		Product found = productService.getProduct(1L);
		Assertions.assertThat(found.getName()).isEqualTo("computer");
	}

	@Test
	public void whenValidateUpdateStock_ThenReturnNewStock() {
		Product newStock = productService.updateStock(1L, Double.parseDouble("8"));
		Assertions.assertThat(newStock.getStock()).isEqualTo(Double.parseDouble("13"));
	}
}
