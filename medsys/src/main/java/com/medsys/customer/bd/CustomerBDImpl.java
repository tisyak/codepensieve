package com.medsys.customer.bd;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.customer.dao.CustomerDAO;
import com.medsys.customer.model.Customer;
 
@Service
@Transactional
public class CustomerBDImpl implements CustomerBD {
    static Logger logger = LoggerFactory.getLogger(CustomerBDImpl.class);
     
    @Autowired
    private CustomerDAO customerDAO;
 
    @Override
    public void addCustomer(Customer user) {
    	logger.debug("CustomerBD: Adding customer.");
        customerDAO.addCustomer(user);
    }
 
    @Override
    public Customer getCustomer(UUID customerId)  {
        return customerDAO.getCustomer(customerId);
    }
 
    @Override
    public void updateCustomer(Customer user)  {
    	logger.debug("CustomerBD: Updating customer.");
        customerDAO.updateCustomer(user);
    }
 
    @Override
    public void deleteCustomer(UUID customerId)  {
        customerDAO.deleteCustomer(customerId);
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