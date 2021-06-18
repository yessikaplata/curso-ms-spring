package co.com.pragma.store.serviceproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.pragma.store.serviceproduct.entity.Category;
import co.com.pragma.store.serviceproduct.entity.Product;
import java.util.*;

public interface ProductRepositoryInteface extends JpaRepository<Product, Long> {

	public List<Product> findByCategory(Category category);
}
