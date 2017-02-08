package com.medsys.orders.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
	public Response addOrder(Orders order) {
		logger.debug("Saving order to DB.");
		try {
			getCurrentSession().save(order);
		} catch (HibernateException he) {
			logger.debug("Unable to save object. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Saved order: " + order);
		return new Response(true, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Orders getOrder(Integer orderId) {
		logger.debug("OrderDAOImpl.getOrder() - [" + orderId + "]");
		Query<Orders> query = getCurrentSession().createQuery("from Orders where orderId = " + orderId + "");
		// query.setParameter("orderId", orderId.toString());

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No user found.");
			throw new EmptyResultDataAccessException("Order [" + orderId + "] not found", 1);
		} else {

			logger.debug("Orders List Size: " + query.getResultList().size());
			List<Orders> list = (List<Orders>) query.getResultList();
			Orders order = (Orders) list.get(0);

			return order;
		}
	}

	@Override
	public Response deleteOrder(Integer orderId) {
		try{
		Orders order = getOrder(orderId);
		if (order != null) {
			getCurrentSession().delete(order);
			logger.info("Delete of order with orderId: " + orderId + " successful");
			return new Response(true, null);
		} else {
			throw new EmptyResultDataAccessException("Order [" + orderId + "] not found", 1);
		}
		}catch(HibernateException he){
			logger.error("Delete of order with orderId: " + orderId + " failed.");
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Orders> getAllOrders() {
		return getCurrentSession().createQuery("from Orders order by name asc").getResultList();
	}

	@Override
	public Orders updateOrder(Orders order) {
		// TODO: Conditional based on OrderStatus to be included
		Orders orderToUpdate = getOrder(order.getOrderId());
		orderToUpdate.setCustomer(order.getCustomer());
		orderToUpdate.setOrderDate(order.getOrderDate());
		orderToUpdate.setDeliveryDate(order.getDeliveryDate());
		orderToUpdate.setPatientName(order.getPatientName());
		orderToUpdate.setRefSource(order.getRefSource());
		// TODO: Write product query
		// orderToUpdate.setProducts(order.getCity());
		getCurrentSession().update(orderToUpdate);
		return orderToUpdate;
	}

	@Override
	public List<Orders> searchForOrders(Orders order) {
		logger.debug("OrderDAOImpl.searchForOrders() - [" + order.toString() + "]");
		Query<Orders> query = getCurrentSession()
				.createQuery("from Orders where lower(name) like :name OR mobileNo like :mobileNo order by name asc");

		/*
		 * if(order.getName()!=null){ query.setString("name",
		 * "%"+order.getName().toLowerCase()+"%"); }else{
		 * query.setString("name", order.getName()); }
		 * 
		 * if(order.getMobileNo()!=null){ query.setString("mobileNo",
		 * "%"+order.getMobileNo().toLowerCase()+"%"); }else{
		 * query.setString("mobileNo", order.getMobileNo()); }
		 * 
		 * logger.debug(query.toString()); if (query.list().size() == 0) {
		 * logger.debug("No orders found matching current search criteria.");
		 * return null; } else {
		 */

		logger.debug("Search Orders List Size: " + query.getResultList().size());
		List<Orders> list = (List<Orders>) query.getResultList();
		return list;
		/* } */
	}

	@Override
	public List<OrderProductSet> getAllProductsInOrder(Integer orderId) {
		logger.debug("Fetching all products in Order: " + orderId);
		return getCurrentSession().createQuery("from OrderProductSet " + " where orderId = " + orderId
				+ " order by productInv.product.productCode asc ").getResultList();
	}

	@Override
	public Response addProductToOrder(OrderProductSet newOrderProductSet) {
		logger.debug("Adding product to Order: " + newOrderProductSet);
		getCurrentSession().save(newOrderProductSet);
		// TODO: change return appropriately
		return new Response(true, null);
	}

	@Override
	public OrderProductSet getProductInOrder(Integer orderProductSetId) {
		logger.debug("Getting product having orderProductSetId: " + orderProductSetId);

		Query<OrderProductSet> query = getCurrentSession()
				.createQuery("from OrderProductSet where orderProductSetId = " + orderProductSetId.toString() + "");
		// query.setParameter("orderId", orderId.toString());

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("Product not found in order.");
			throw new EmptyResultDataAccessException("OrderProductSet [" + orderProductSetId + "] not found", 1);
		} else {

			logger.debug("OrderProductSet List Size: " + query.getResultList().size());
			List<OrderProductSet> list = (List<OrderProductSet>) query.getResultList();
			OrderProductSet orderProductSet = (OrderProductSet) list.get(0);
			return orderProductSet;
		}
	}

	@Override
	public Response updateProuctInOrder(OrderProductSet orderProductSet) {
		OrderProductSet orderProductSetToUpdate = getProductInOrder(orderProductSet.getOrderProductSetId());
		orderProductSetToUpdate.setQty(orderProductSet.getQty());
		getCurrentSession().update(orderProductSetToUpdate);
		// TODO: change return appropriately
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