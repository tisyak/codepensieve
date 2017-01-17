package com.medsys.customer.bd;

import java.util.List;
import java.util.UUID;

import com.medsys.customer.model.Customer;

public interface CustomerBD {

	public void addCustomer(Customer customer);
	
	

	public Customer getCustomer(UUID customerId);

	public void updateCustomer(Customer customer);

	public void deleteCustomer(UUID customerId);

	public List<Customer> getAllCustomers();
	
	public List<Customer> searchForCustomers(Customer customer);

}