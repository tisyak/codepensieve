package com.medsys.customer.bd;

import java.util.List;
import java.util.UUID;

import com.medsys.common.model.Response;
import com.medsys.customer.model.Customer;

public interface CustomerBD {

	public Response addCustomer(Customer customer);
	
	public Customer getCustomer(UUID customerId);

	public Response updateCustomer(Customer customer);

	public Response deleteCustomer(UUID customerId);

	public List<Customer> getAllCustomers();
	
	public List<Customer> searchForCustomers(Customer customer);

}