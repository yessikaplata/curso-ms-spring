package co.com.pragma.customerservice.service;

import java.util.List;

import co.com.pragma.customerservice.entity.Customer;
import co.com.pragma.customerservice.entity.Region;

public interface CustomerServiceInterface {
	
	public List<Customer> findCustomerAll();

	public List<Customer> findCustomersByRegion(Region region);

	public Customer createCustomer(Customer customer);

	public Customer updateCustomer(Customer customer);

	public Customer deleteCustomer(Customer customer);

	public Customer getCustomer(Long id);

}
