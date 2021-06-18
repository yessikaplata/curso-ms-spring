package co.com.pragma.customerservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.pragma.customerservice.entity.Customer;
import co.com.pragma.customerservice.entity.Region;

public interface CustomerRepositoryInterface extends JpaRepository<Customer, Long> {

	public Customer findByNumberID(String numberID);

	public List<Customer> findByLastName(String lastName);

	public List<Customer> findByRegion(Region region);
}
