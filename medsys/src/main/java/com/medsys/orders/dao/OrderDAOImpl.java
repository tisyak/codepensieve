package com.medsys.orders.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.medsys.orders.model.Orders;

@Repository
public class OrderDAOImpl implements OrderDAO {

	static Logger logger = LoggerFactory.getLogger(OrderDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void addOrder(Orders order) {
		logger.debug("Saving order to DB.");
		getCurrentSession().save(order);
	}


	@SuppressWarnings("unchecked")
	@Override
	public Orders getOrder(Integer orderId) {
		logger.debug("OrderDAOImpl.getOrder() - [" + orderId + "]");
		Query query = getCurrentSession().createQuery(
				"from Orders where order_id = '"+orderId.toString() + "'");
		//query.setParameter("orderId", orderId.toString());

		logger.debug(query.toString());
		if (query.list().size() == 0) {
			logger.debug("No user found.");
			throw new UsernameNotFoundException("Orders [" + orderId
					+ "] not found");
		} else {
			
			logger.debug("Orders List Size: " + query.list().size());
			List<Orders> list = (List<Orders>) query.list();
			Orders userObject = (Orders) list.get(0);

			return userObject;
		}
	}

	@Override
	public void deleteOrder(Integer orderId)  {
		Orders order = getOrder(orderId);
		if (order != null) {
			getCurrentSession().delete(order);
		} else {
			throw new UsernameNotFoundException("Orders Username : [" + orderId
					+ "] not found");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Orders> getAllOrders() {
		return getCurrentSession().createQuery("from Orders order by name asc").list();
	}

	@Override
	public void updateOrder(Orders order) {
		//TODO: Conditional based on OrderStatus to be included
		Orders orderToUpdate = getOrder(order.getOrderId());
		orderToUpdate.setCustomer(order.getCustomer());
		orderToUpdate.setOrderDate(order.getOrderDate());
		orderToUpdate.setDeliveryDate(order.getDeliveryDate());
		orderToUpdate.setPatientName(order.getPatientName());
		orderToUpdate.setRefSource(order.getRefSource());
		//TODO: Write product query
	//	orderToUpdate.setProducts(order.getCity());
		getCurrentSession().update(orderToUpdate);
	}

	
	
	@Override
	public List<Orders> searchForOrders(Orders order) {
		logger.debug("OrderDAOImpl.searchForOrders() - [" + order.toString() + "]");
		Query query = getCurrentSession().createQuery(
				"from Orders where lower(name) like :name OR mobileNo like :mobileNo order by name asc");

		/*if(order.getName()!=null){
			query.setString("name", "%"+order.getName().toLowerCase()+"%");
		}else{
			query.setString("name", order.getName());
		}
	
		if(order.getMobileNo()!=null){
			query.setString("mobileNo", "%"+order.getMobileNo().toLowerCase()+"%");
		}else{
			query.setString("mobileNo", order.getMobileNo());
		}
		
		logger.debug(query.toString());
		if (query.list().size() == 0) {
			logger.debug("No orders found matching current search criteria.");
			return null;
		} else {*/
			
			logger.debug("Search Orders List Size: " + query.list().size());
			List<Orders> list = (List<Orders>) query.list();
			return list;
		/*}*/
	}
	

	/*@Override
	public List<Orders> listOrderswithAvailableDSCs() {
		logger.debug("OrderDAOImpl.listOrderswithAvailableDSCs()");
		Query query = getCurrentSession().createQuery(
				"select cdi.orderInfo from OrderDSCInfo cdi where cdi.dscAvailable='true' order by cdi.orderInfo.companyName asc ");

		logger.debug(query.toString());
		if (query.list().size() == 0) {
			logger.debug("No orders found matching current search criteria.");
			return null;
		} else {
			
			logger.debug("Search Orders List Size: " + query.list().size());
			@SuppressWarnings("unchecked")
			List<Orders> list = (List<Orders>) query.list();
			return list;
		}
	}
	
	@Override
	public List<Orders> monthlyOrderListHavingInwardDSCs() {
		logger.debug("OrderDAOImpl.monthlyInwardDSCs()");
		@SuppressWarnings("unchecked")
		List<Orders> orders =  getCurrentSession().createQuery("select orderDSCInfo.orderInfo from DscTransferInfo dti "
				+ " where dti.orderDSCInfo.dscAvailable=true"
				+ " and to_char(dti.transferDate,'YYYY/MM') = '"+ 
				Calendar.getInstance().get(Calendar.YEAR) +"/"+ (Calendar.getInstance().get(Calendar.MONTH)+1) +"'  order by orderDSCInfo.orderInfo.companyName asc ")
				.list();
		logger.debug("Result: " + orders);
		return orders;
	}

	
	@Override
	public List<Orders> monthlyOrderListHavingOutwardDSCs() {
		logger.debug("OrderDAOImpl.monthlyOutwardDSCs()");
		@SuppressWarnings("unchecked")
		List<Orders> orders =   getCurrentSession().createQuery("select orderDSCInfo.orderInfo from DscTransferInfo dti "
				+ " where dti.orderDSCInfo.dscAvailable=false"
				+ " and to_char(dti.dscReturnDate,'YYYY/MM') = '"+ 
				Calendar.getInstance().get(Calendar.YEAR) +"/"+ (Calendar.getInstance().get(Calendar.MONTH)+1) +"'  order by orderDSCInfo.orderInfo.companyName asc ")
				.list();
		logger.debug("Result: " + orders);
		return orders;
	}
*/
}