package co.com.pragma.store.serviceproduct.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import co.com.pragma.store.serviceproduct.entity.Category;
import co.com.pragma.store.serviceproduct.entity.Product;
import co.com.pragma.store.serviceproduct.repository.ProductRepositoryInteface;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceInterface {

	private final ProductRepositoryInteface productRepository;

	@Override
	public List<Product> listAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getProduct(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public Product createProduct(Product product) {
		product.setStatus("CREATED");
		product.setCreateAt(new Date());
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		Product productDb = getProduct(product.getId());
		if (productDb == null) {
			return null;
		}
		productDb.setName(product.getName());
		productDb.setDescription(product.getDescription());
		productDb.setCategory(product.getCategory());
		productDb.setPrice(product.getPrice());
		return productRepository.save(productDb);
	}

	@Override
	public Product deleteProduct(Long id) {
		Product productDb = getProduct(id);
		if (productDb == null) {
			return null;
		}
		productDb.setStatus("DELETED");
		return productRepository.save(productDb);
	}

	@Override
	public List<Product> findByCategory(Category category) {
		return productRepository.findByCategory(category);
	}

	@Override
	public Product updateStock(Long id, Double quantity) {
		Product productDb = getProduct(id);
		if (productDb == null) {
			return null;
		}
		productDb.setStock(productDb.getStock() + quantity);
		return productRepository.save(productDb);
	}

}
