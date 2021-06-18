package co.com.pragma.store.serviceproduct.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.pragma.store.serviceproduct.entity.Category;
import co.com.pragma.store.serviceproduct.entity.Product;
import co.com.pragma.store.serviceproduct.service.ProductServiceInterface;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductServiceInterface service;

	@GetMapping
	public ResponseEntity<List<Product>> listProducts(
			@RequestParam(name = "categoryId", required = false) Long categoryId) {
		List<Product> products = null;
		if (categoryId == null) {
			products = service.listAllProducts();
			if (products.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
		} else {
			products = service.findByCategory(Category.builder().id(categoryId).build());
			if (products.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.ok(products);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
		Product product = service.getProduct(id);
		if (product == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(product);
	}

	@PostMapping
	public ResponseEntity<Product> createProduct(@Valid @RequestBody(required = true) Product product,
			BindingResult result) {
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result));
		}
		Product productCreated = service.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
		product.setId(id);
		Product productUpdated = service.updateProduct(product);
		if (productUpdated == null) {
			ResponseEntity.notFound();
		}
		return ResponseEntity.ok(productUpdated);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
		Product productDeleted = service.deleteProduct(id);
		if (productDeleted == null) {
			ResponseEntity.notFound();
		}
		return ResponseEntity.ok(productDeleted);
	}

	@GetMapping(value = "/{id}/stock")
	public ResponseEntity<Product> udpateStockProduct(@PathVariable("id") Long id,
			@RequestParam(name = "quantity", required = true) Double quantity) {
		Product product = service.updateStock(id, quantity);
		if (product == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(product);
	}

	private String formatMessage(BindingResult result) {
		List<Map<String, String>> errors = result.getFieldErrors().stream().map(err -> {
			Map<String, String> error = new HashMap<String, String>();
			error.put(err.getField(), err.getDefaultMessage());
			return error;
		}).collect(Collectors.toList());
		ErrorMessage errorMessage = ErrorMessage.builder().code("01").messages(errors).build();
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(errorMessage);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

}
