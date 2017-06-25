package com.medsys.orders.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.common.model.Response;
import com.medsys.master.bd.MasterDataBD;
import com.medsys.master.model.SeedMaster;
import com.medsys.master.model.SeedMasterKey;
import com.medsys.orders.bd.OrderNoGenerator;
import com.medsys.orders.model.OrderProductSet;
import com.medsys.orders.model.Orders;
import com.medsys.util.CalendarUtility;
import com.medsys.util.EpSystemError;

@Repository
public class OrderDAOImpl implements OrderDAO {

	static Logger logger = LoggerFactory.getLogger(OrderDAOImpl.class);

	@Autowired
	private MasterDataBD masterDataBD;

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	@Transactional
	public Response addOrder(Orders order) {
		logger.debug("Saving order to DB.");
		try {
			SeedMaster lastOrderNo = (SeedMaster) masterDataBD.getbyCode(SeedMaster.class,
					SeedMasterKey.LATEST_ORDER_NO);
			String nextOrderNo = OrderNoGenerator.getNextOrderNumber(lastOrderNo.getSeedValue());
			order.setOrderNumber(nextOrderNo);
			getCurrentSession().save(order);
			lastOrderNo.setSeedValue(nextOrderNo);
			masterDataBD.update(SeedMaster.class, lastOrderNo);
		} catch (Exception he) {
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
			logger.debug("No order found.");
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
		try {
			Orders order = getOrder(orderId);
			if (order != null) {
				getCurrentSession().delete(order);
				logger.info("Delete of order with orderId: " + orderId + " successful");
				return new Response(true, null);
			} else {
				throw new EmptyResultDataAccessException("Order [" + orderId + "] not found", 1);
			}
		} catch (HibernateException he) {
			logger.error("Delete of order with orderId: " + orderId + " failed.");
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Orders> getAllOrders() {
		return getCurrentSession().createQuery("from Orders order by orderDate desc").getResultList();
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
		orderToUpdate.setUpdateBy(order.getUpdateBy());
		orderToUpdate.setUpdateTimestamp(order.getUpdateTimestamp());
		getCurrentSession().update(orderToUpdate);
		return orderToUpdate;
	}

	@Override
	public Response updateOrderStatus(Orders order) {
		try {
			Orders orderToUpdate = getOrder(order.getOrderId());

			if (orderToUpdate != null) {
				orderToUpdate.setOrderStatus(order.getOrderStatus());
				getCurrentSession().update(orderToUpdate);
				logger.info("Status update of order with orderId: " + order.getOrderId() + " successful");
				return new Response(true, null);
			} else {
				return new Response(false, EpSystemError.NO_RECORD_FOUND);
			}
		} catch (HibernateException he) {
			logger.error("Status update of order with orderId:  " + order.getOrderId() + " failed.");
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
	}

	@Override
	public List<Orders> searchForOrders(Orders order) {
		logger.debug("OrderDAOImpl.searchForOrders() - [" + order.toString() + "]");
		Query<Orders> query = getCurrentSession().createQuery(
				"from Orders where lower(orderNumber) like :orderNo OR lower(customer.name) like :custName "
						+ " OR orderDate = :orderDate " + " order by orderNumber asc",
				Orders.class);

		if (order.getOrderNumber() != null) {
			query.setParameter("orderNo", "%" + order.getOrderNumber().toLowerCase() + "%");
		} else {
			query.setParameter("orderNo", order.getOrderNumber());
		}

		if (order.getOrderDate() != null) {
			query.setParameter("orderDate", order.getOrderDate(), TemporalType.DATE);
		} else {
			query.setParameter("orderDate", null);
		}

		if (order.getCustomer() != null && order.getCustomer().getName() != null) {
			query.setParameter("custName", "%" + order.getCustomer().getName().toLowerCase() + "%");
		} else {
			query.setParameter("custName", null);
		}

		logger.debug(query.toString());

		if (query.getResultList().size() == 0) {
			logger.debug("No orders found matching current search criteria.");
			return null;

		} else {

			logger.debug("Search Orders List Size: " + query.getResultList().size());
			List<Orders> list = (List<Orders>) query.getResultList();
			return list;
		}
	}

	@Override
	public List<Orders> searchForOrdersInDateRange(Date startDate, Date endDate) {
		logger.debug(
				"OrderDAOImpl.searchForOrdersInDateRange() for the Year - [" + startDate + " - " + endDate + "]");

		Query<Orders> searchQuery = getCurrentSession()
				.createQuery("from Orders WHERE orderDate BETWEEN :stDate AND :edDate ", Orders.class);

		searchQuery.setParameter("stDate", startDate, TemporalType.DATE);
		searchQuery.setParameter("edDate", endDate, TemporalType.DATE);

		logger.debug(searchQuery.toString());

		if (searchQuery.getResultList().size() == 0) {
			logger.debug("No orders found matching current search criteria.");
			return null;

		} else {

			logger.debug("Date Search Orders List Size: " + searchQuery.getResultList().size());
			List<Orders> list = (List<Orders>) searchQuery.getResultList();
			return list;
		}
	}

	@Override
	public List<OrderProductSet> getAllProductsInOrder(Integer orderId) {
		logger.debug("Fetching all products in Order: " + orderId);
		getCurrentSession().flush();
		return getCurrentSession().createQuery(
				"from OrderProductSet " + " where orderId = " + orderId + " order by product.productCode asc ",
				OrderProductSet.class).getResultList();
	}

	@Override
	public Response addProductToOrder(OrderProductSet newOrderProductSet) {
		logger.debug("Adding product to Order: " + newOrderProductSet);
		getCurrentSession().save(newOrderProductSet);
		logger.debug("Saved product in order: " + newOrderProductSet);
		return new Response(true, null);
	}

	@Override
	public OrderProductSet getProductInOrder(Integer orderProductSetId) {
		logger.debug("Getting product having orderProductSetId: " + orderProductSetId);

		Query<OrderProductSet> query = getCurrentSession().createQuery(
				"from OrderProductSet where orderProductSetId = " + orderProductSetId + "", OrderProductSet.class);
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
	public Response updateProductInOrder(OrderProductSet orderProductSet) {
		OrderProductSet orderProductSetToUpdate = getProductInOrder(orderProductSet.getOrderProductSetId());
		orderProductSetToUpdate.setQty(orderProductSet.getQty());
		orderProductSetToUpdate.setUpdateBy(orderProductSet.getUpdateBy());
		orderProductSetToUpdate.setUpdateTimestamp(orderProductSet.getUpdateTimestamp());
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

	@Override
	public int getCountOfTotalOrdersForYear() {

		Date startDate = CalendarUtility.getFirstDateOfYear();
		Date endDate = CalendarUtility.getLastDateOfYear();
		logger.debug(
				"OrderDAOImpl.getCountOfTotalOrdersForYear() for the Year - [" + startDate + " - " + endDate + "]");
		return getCountOfTotalOrdersInDateRange(startDate, endDate);

	}

	@Override
	public int getCountOfTotalOrdersInMonth() {
		Date begining, end;

		begining = CalendarUtility.getFirstDateOfMonth();
		end = CalendarUtility.getLastDateOfMonth();

		logger.debug("OrderDAOImpl.getCountOfTotalOrdersINMonth() for the Month - [" + begining + " - " + end + "]");
		return getCountOfTotalOrdersInDateRange(begining, end);
	}

	@Override
	public int getCountOfTotalOrdersInDateRange(Date startDate, Date endDate) {

		logger.debug(
				"OrderDAOImpl.getCountOfTotalOrdersInDateRange() for the Year - [" + startDate + " - " + endDate + "]");

		Query<Long> countQuery = getCurrentSession()
				.createQuery("select count(*) from Orders WHERE orderDate BETWEEN :stDate AND :edDate ", Long.class);

		countQuery.setParameter("stDate", startDate, TemporalType.DATE);
		countQuery.setParameter("edDate", endDate, TemporalType.DATE);

		logger.debug(countQuery.toString());

		return countQuery.getSingleResult().intValue();

	}

}