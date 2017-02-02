package com.medsys.customer.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.medsys.customer.model.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	static Logger logger = LoggerFactory.getLogger(CustomerDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void addCustomer(Customer customer) {
		logger.debug("Saving customer to DB.");
		getCurrentSession().save(customer);
	}


	@SuppressWarnings("unchecked")
	@Override
	public Customer getCustomer(UUID customerId) throws UsernameNotFoundException {
		logger.debug("CustomerDAOImpl.getCustomer() - [" + customerId + "]");
		Query query = getCurrentSession().createQuery(
				"from Customer where customer_id = '"+customerId.toString() + "'");
		//query.setParameter("customerId", customerId.toString());

		logger.debug(query.toString());
		if (query.list().size() == 0) {
			logger.debug("No user found.");
			throw new UsernameNotFoundException("Customer [" + customerId
					+ "] not found");
		} else {
			
			logger.debug("Customer List Size: " + query.list().size());
			List<Customer> list = (List<Customer>) query.list();
			Customer userObject = (Customer) list.get(0);

			return userObject;
		}
	}

	@Override
	public void deleteCustomer(UUID customerId) throws UsernameNotFoundException {
		Customer customer = getCustomer(customerId);
		if (customer != null) {
			getCurrentSession().delete(customer);
		} else {
			throw new UsernameNotFoundException("Customer Username : [" + customerId
					+ "] not found");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> getAllCustomers() {
		return getCurrentSession().createQuery("from Customer order by name asc").list();
	}

	@Override
	public void updateCustomer(Customer customer) throws UsernameNotFoundException {
		Customer customerToUpdate = getCustomer(customer.getCustomerId());
		customerToUpdate.setEmail(customer.getEmail());
		customerToUpdate.setMobileNo(customer.getMobileNo());
		customerToUpdate.setName(customer.getName());
		customerToUpdate.setAddress(customer.getAddress());
		customerToUpdate.setCity(customer.getCity());
	
		customerToUpdate.setPincode(customer.getPincode());
		getCurrentSession().update(customerToUpdate);
	}

	
	
	@Override
	public List<Customer> searchForCustomers(Customer customer) {
		logger.debug("CustomerDAOImpl.searchForCustomers() - [" + customer.toString() + "]");
		Query query = getCurrentSession().createQuery(
				"from Customer where lower(name) like :name OR mobileNo like :mobileNo order by name asc");

		if(customer.getName()!=null){
			query.setString("name", "%"+customer.getName().toLowerCase()+"%");
		}else{
			query.setString("name", customer.getName());
		}
	
		if(customer.getMobileNo()!=null){
			query.setString("mobileNo", "%"+customer.getMobileNo().toLowerCase()+"%");
		}else{
			query.setString("mobileNo", customer.getMobileNo());
		}
		
		logger.debug(query.toString());
		if (query.list().size() == 0) {
			logger.debug("No customers found matching current search criteria.");
			return null;
		} else {
			
			logger.debug("Search Customer List Size: " + query.list().size());
			List<Customer> list = (List<Customer>) query.list();
			return list;
		}
	}
	

	/*@Override
	public List<Customer> listCustomerswithAvailableDSCs() {
		logger.debug("CustomerDAOImpl.listCustomerswithAvailableDSCs()");
		Query query = getCurrentSession().createQuery(
				"select cdi.customerInfo from CustomerDSCInfo cdi where cdi.dscAvailable='true' order by cdi.customerInfo.companyName asc ");

		logger.debug(query.toString());
		if (query.list().size() == 0) {
			logger.debug("No customers found matching current search criteria.");
			return null;
		} else {
			
			logger.debug("Search Customer List Size: " + query.list().size());
			@SuppressWarnings("unchecked")
			List<Customer> list = (List<Customer>) query.list();
			return list;
		}
	}
	
	@Override
	public List<Customer> monthlyCustomerListHavingInwardDSCs() {
		logger.debug("CustomerDAOImpl.monthlyInwardDSCs()");
		@SuppressWarnings("unchecked")
		List<Customer> customers =  getCurrentSession().createQuery("select customerDSCInfo.customerInfo from DscTransferInfo dti "
				+ " where dti.customerDSCInfo.dscAvailable=true"
				+ " and to_char(dti.transferDate,'YYYY/MM') = '"+ 
				Calendar.getInstance().get(Calendar.YEAR) +"/"+ (Calendar.getInstance().get(Calendar.MONTH)+1) +"'  order by customerDSCInfo.customerInfo.companyName asc ")
				.list();
		logger.debug("Result: " + customers);
		return customers;
	}

	
	@Override
	public List<Customer> monthlyCustomerListHavingOutwardDSCs() {
		logger.debug("CustomerDAOImpl.monthlyOutwardDSCs()");
		@SuppressWarnings("unchecked")
		List<Customer> customers =   getCurrentSession().createQuery("select customerDSCInfo.customerInfo from DscTransferInfo dti "
				+ " where dti.customerDSCInfo.dscAvailable=false"
				+ " and to_char(dti.dscReturnDate,'YYYY/MM') = '"+ 
				Calendar.getInstance().get(Calendar.YEAR) +"/"+ (Calendar.getInstance().get(Calendar.MONTH)+1) +"'  order by customerDSCInfo.customerInfo.companyName asc ")
				.list();
		logger.debug("Result: " + customers);
		return customers;
	}
*/
}