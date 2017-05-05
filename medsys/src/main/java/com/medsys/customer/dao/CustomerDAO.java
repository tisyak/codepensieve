package com.medsys.customer.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.medsys.common.model.Response;
import com.medsys.customer.model.Customer;
 
public interface CustomerDAO {
 
    public Response addCustomer(Customer customer);
 
    public Customer getCustomer(UUID customerId) throws UsernameNotFoundException;
 
    public Response updateCustomer(Customer customer) throws UsernameNotFoundException;
 
    public Response deleteCustomer(UUID customerId) throws UsernameNotFoundException;
 
    public List<Customer> getAllCustomers();

	public List<Customer> searchForCustomers(Customer customer);
	
	
	
	
}