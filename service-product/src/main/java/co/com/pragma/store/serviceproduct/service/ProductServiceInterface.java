package co.com.pragma.store.serviceproduct.service;

import java.util.List;

import co.com.pragma.store.serviceproduct.entity.Category;
import co.com.pragma.store.serviceproduct.entity.Product;

public interface ProductServiceInterface {
	public List<Product> listAllProducts();

	public Product getProduct(Long id);

	public Product createProduct(Product product);

	public Product updateProduct(Product product);

	public Product deleteProduct(Long id);

	public List<Product> findByCategory(Category category);

	public Product updateStock(Long id, Double quantity);
}
