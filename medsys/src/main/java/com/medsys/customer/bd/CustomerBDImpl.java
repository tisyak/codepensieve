package com.medsys.customer.bd;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.common.model.Response;
import com.medsys.customer.dao.CustomerDAO;
import com.medsys.customer.model.Customer;
 
@Service
@Transactional
public class CustomerBDImpl implements CustomerBD {
    static Logger logger = LoggerFactory.getLogger(CustomerBDImpl.class);
     
    @Autowired
    private CustomerDAO customerDAO;
 
    @Override
    public Response addCustomer(Customer customer) {
    	logger.debug("CustomerBD: Adding customer.");
        return customerDAO.addCustomer(customer);
    }
 
    @Override
    public Customer getCustomer(UUID customerId)  {
        return customerDAO.getCustomer(customerId);
    }
 
    @Override
    public Response updateCustomer(Customer customer)  {
    	logger.debug("CustomerBD: Updating customer.");
    	return customerDAO.updateCustomer(customer);
    }
 
    @Override
    public Response deleteCustomer(UUID customerId)  {
    	return customerDAO.deleteCustomer(customerId);
    }
 
    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

	@Override
	public List<Customer> searchForCustomers(Customer customer) {
		 return customerDAO.searchForCustomers(customer);
	}

	/*@Override
	public List<Customer> listCustomerswithAvailableDSCs() {
		return customerDAO.listCustomerswithAvailableDSCs();
	}
	
	@Override
	public List<Customer>  monthlyCustomerListHavingInwardDSCs(){
		return customerDAO.monthlyCustomerListHavingInwardDSCs();
	}
	
	@Override
	public List<Customer>  monthlyCustomerListHavingOutwardDSCs(){
		return customerDAO.monthlyCustomerListHavingOutwardDSCs();
	}
   
*/
   
}