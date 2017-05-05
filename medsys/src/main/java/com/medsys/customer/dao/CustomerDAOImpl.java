package com.medsys.customer.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.medsys.common.model.Response;
import com.medsys.customer.model.Customer;
import com.medsys.util.EpSystemError;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	static Logger logger = LoggerFactory.getLogger(CustomerDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Response addCustomer(Customer customer) {
		logger.debug("Saving customer to DB.");
		try {
			getCurrentSession().save(customer);
		} catch (HibernateException he) {
			logger.debug("Unable to save object. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Saved customer: " + customer);
		return new Response(true, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Customer getCustomer(UUID customerId) throws UsernameNotFoundException {
		logger.debug("CustomerDAOImpl.getCustomer() - [" + customerId + "]");
		Query<Customer> query = getCurrentSession()
				.createQuery("from Customer where customer_id = '" + customerId.toString() + "'");
		// query.setParameter("customerId", customerId.toString());

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No user found.");
			throw new UsernameNotFoundException("Customer [" + customerId + "] not found");
		} else {

			logger.debug("Customer List Size: " + query.getResultList().size());
			List<Customer> list = query.getResultList();
			Customer userObject = (Customer) list.get(0);

			return userObject;
		}
	}

	@Override
	public Response deleteCustomer(UUID customerId) throws UsernameNotFoundException {
		Customer customer = getCustomer(customerId);
		try {
			if (customer != null) {
				getCurrentSession().delete(customer);
			} else {
				throw new UsernameNotFoundException("Customer Username : [" + customerId + "] not found");
			}
		} catch (HibernateException he) {
			logger.debug("Unable to delete object. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Deleted Successfully: " + customer);
		return new Response(true, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> getAllCustomers() {
		return getCurrentSession().createQuery("from Customer order by name asc").getResultList();
	}

	@Override
	public Response updateCustomer(Customer customer) throws UsernameNotFoundException {
		Customer customerToUpdate = getCustomer(customer.getCustomerId());
		customerToUpdate.setEmail(customer.getEmail());
		customerToUpdate.setMobileNo(customer.getMobileNo());
		customerToUpdate.setName(customer.getName());
		customerToUpdate.setAddress(customer.getAddress());
		customerToUpdate.setCity(customer.getCity());
		customerToUpdate.setPincode(customer.getPincode());
		try {
			getCurrentSession().update(customerToUpdate);
		} catch (HibernateException he) {
			logger.debug("Unable to save object. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Saved customer: " + customer);
		return new Response(true, null);
	}

	@Override
	public List<Customer> searchForCustomers(Customer customer) {
		logger.debug("CustomerDAOImpl.searchForCustomers() - [" + customer.toString() + "]");
		Query<Customer> query = getCurrentSession()
				.createQuery("from Customer where lower(name) like :name OR mobileNo like :mobileNo order by name asc",Customer.class);

		if (customer.getName() != null) {
			query.setParameter("name", "%" + customer.getName().toLowerCase() + "%");
		} else {
			query.setParameter("name", customer.getName());
		}

		if (customer.getMobileNo() != null) {
			query.setParameter("mobileNo", "%" + customer.getMobileNo().toLowerCase() + "%");
		} else {
			query.setParameter("mobileNo", customer.getMobileNo());
		}

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No customers found matching current search criteria.");
			return null;
		} else {

			logger.debug("Search Customer List Size: " + query.getResultList().size());
			List<Customer> list = (List<Customer>) query.getResultList();
			return list;
		}
	}

	/*
	 * @Override public List<Customer> listCustomerswithAvailableDSCs() {
	 * logger.debug("CustomerDAOImpl.listCustomerswithAvailableDSCs()"); Query
	 * query = getCurrentSession().createQuery(
	 * "select cdi.customerInfo from CustomerDSCInfo cdi where cdi.dscAvailable='true' order by cdi.customerInfo.companyName asc "
	 * );
	 * 
	 * logger.debug(query.toString()); if (query.list().size() == 0) {
	 * logger.debug("No customers found matching current search criteria.");
	 * return null; } else {
	 * 
	 * logger.debug("Search Customer List Size: " + query.list().size());
	 * 
	 * @SuppressWarnings("unchecked") List<Customer> list = (List<Customer>)
	 * query.list(); return list; } }
	 * 
	 * @Override public List<Customer> monthlyCustomerListHavingInwardDSCs() {
	 * logger.debug("CustomerDAOImpl.monthlyInwardDSCs()");
	 * 
	 * @SuppressWarnings("unchecked") List<Customer> customers =
	 * getCurrentSession().
	 * createQuery("select customerDSCInfo.customerInfo from DscTransferInfo dti "
	 * + " where dti.customerDSCInfo.dscAvailable=true" +
	 * " and to_char(dti.transferDate,'YYYY/MM') = '"+
	 * Calendar.getInstance().get(Calendar.YEAR) +"/"+
	 * (Calendar.getInstance().get(Calendar.MONTH)+1)
	 * +"'  order by customerDSCInfo.customerInfo.companyName asc ") .list();
	 * logger.debug("Result: " + customers); return customers; }
	 * 
	 * 
	 * @Override public List<Customer> monthlyCustomerListHavingOutwardDSCs() {
	 * logger.debug("CustomerDAOImpl.monthlyOutwardDSCs()");
	 * 
	 * @SuppressWarnings("unchecked") List<Customer> customers =
	 * getCurrentSession().
	 * createQuery("select customerDSCInfo.customerInfo from DscTransferInfo dti "
	 * + " where dti.customerDSCInfo.dscAvailable=false" +
	 * " and to_char(dti.dscReturnDate,'YYYY/MM') = '"+
	 * Calendar.getInstance().get(Calendar.YEAR) +"/"+
	 * (Calendar.getInstance().get(Calendar.MONTH)+1)
	 * +"'  order by customerDSCInfo.customerInfo.companyName asc ") .list();
	 * logger.debug("Result: " + customers); return customers; }
	 */
}