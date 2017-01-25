package com.medsys.orders.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.medsys.common.model.Response;
import com.medsys.orders.model.OrderProductSet;
import com.medsys.orders.model.Orders;
import com.medsys.util.EpSystemError;

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
				"from Orders where orderId = "+orderId + "");
		//query.setParameter("orderId", orderId.toString());

		logger.debug(query.toString());
		if (query.list().size() == 0) {
			logger.debug("No user found.");
			throw new EmptyResultDataAccessException ("Order [" + orderId
					+ "] not found",1);
		} else {
			
			logger.debug("Orders List Size: " + query.list().size());
			List<Orders> list = (List<Orders>) query.list();
			Orders order = (Orders) list.get(0);

			return order;
		}
	}

	@Override
	public void deleteOrder(Integer orderId)  {
		Orders order = getOrder(orderId);
		if (order != null) {
			getCurrentSession().delete(order);
		} else {
			throw new EmptyResultDataAccessException ("Order [" + orderId
					+ "] not found",1);
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

	@Override
	public List<OrderProductSet> getAllProductsInOrder(Integer orderId) {
		logger.debug("Fetching all products in Order: " + orderId);
		return getCurrentSession().createQuery("from OrderProductSet "
				+ " where orderId = " + orderId
				+ " order by productInv.product.productCode asc ").list();
	}

	@Override
	public Response addProductToOrder(OrderProductSet newOrderProductSet) {
		logger.debug("Adding product to Order: " + newOrderProductSet);
		getCurrentSession().save(newOrderProductSet);
		//TODO: change return appropriately
		return new Response(true, null);
	}

	@Override
	public OrderProductSet getProductInOrder(Integer orderProductSetId) {
		logger.debug("Getting product having orderProductSetId: " + orderProductSetId);
		
		Query query = getCurrentSession().createQuery(
				"from OrderProductSet where orderProductSetId = "+orderProductSetId.toString() + "");
		//query.setParameter("orderId", orderId.toString());

		logger.debug(query.toString());
		if (query.list().size() == 0) {
			logger.debug("Product not found in order.");
			throw new EmptyResultDataAccessException ("OrderProductSet [" + orderProductSetId
					+ "] not found",1);
		} else {
			
			logger.debug("OrderProductSet List Size: " + query.list().size());
			List<OrderProductSet> list = (List<OrderProductSet>) query.list();
			OrderProductSet orderProductSet = (OrderProductSet) list.get(0);
			return orderProductSet;
		}
	}

	@Override
	public Response updateProuctInOrder(OrderProductSet orderProductSet) {
		OrderProductSet orderProductSetToUpdate = getProductInOrder(orderProductSet.getOrderProductSetId());
		orderProductSetToUpdate.setQty(orderProductSet.getQty());
		getCurrentSession().update(orderProductSetToUpdate);
		//TODO: change return appropriately
		return new Response(true, null);
	}

	@Override
	public Response deleteProductFromOrder(OrderProductSet orderProductSet) {
		OrderProductSet existingOrderProductSet = getProductInOrder(orderProductSet.getOrderProductSetId());
		
		if (existingOrderProductSet != null) {
			getCurrentSession().delete(existingOrderProductSet);
			return new Response(true, null);
		}
		
		return new Response(false, EpSystemError.NO_RECORD_FOUND);
		
	}
	
	
	
	
}