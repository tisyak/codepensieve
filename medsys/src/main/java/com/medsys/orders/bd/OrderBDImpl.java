package com.medsys.orders.bd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.common.model.Response;
import com.medsys.exception.SysException;
import com.medsys.orders.dao.OrderDAO;
import com.medsys.orders.model.OrderProductSet;
import com.medsys.orders.model.Orders;
import com.medsys.product.bd.ProductInvBD;
import com.medsys.product.model.ProductInv;
import com.medsys.util.EpSystemError;

@Service
@Transactional
public class OrderBDImpl implements OrderBD {
	static Logger logger = LoggerFactory.getLogger(OrderBDImpl.class);

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private ProductInvBD productInvBD;

	@Override
	@Transactional
	public Response addOrder(Orders order) {
		logger.debug("OrderBD: Adding order.");
		Response response = orderDAO.addOrder(order);
		if (response.isStatus()) {

		}
		return response;

	}

	@Override
	public Orders getOrder(Integer orderId) {
		return orderDAO.getOrder(orderId);
	}

	@Override
	public Orders updateOrder(Orders order) {
		logger.debug("OrderBD: Updating order.");
		return orderDAO.updateOrder(order);
	}

	@Override
	public Response deleteOrder(Integer orderId) {
		return orderDAO.deleteOrder(orderId);
	}

	@Override
	public List<Orders> getAllOrders() {
		return orderDAO.getAllOrders();
	}

	@Override
	public List<Orders> searchForOrders(Orders order) {
		return orderDAO.searchForOrders(order);
	}

	@Override
	public List<OrderProductSet> getAllProductsInOrder(Integer orderId) {
		logger.debug("Get All products in Order: " + orderId);
		List<OrderProductSet> orderProducts = orderDAO.getAllProductsInOrder(orderId);
		for (OrderProductSet product : orderProducts) {
			try {
				ProductInv productInv = productInvBD.getProduct(product.getProduct().getProductId());
				product.setAvailableQty(productInv.getAvailableQty());
			} catch (EmptyResultDataAccessException e) {
				logger.debug("Product " + product.getProduct().getProductCode() + " not found in Inventory");
				product.setAvailableQty(0);
			}

		}

		return orderProducts;

	}

	@Override
	@Transactional
	public Response addProductToOrder(OrderProductSet newOrderProductSet) {
		logger.debug("ADD product to Order: " + newOrderProductSet);
		// Managing product inventory before adding product to the order
		try {
			productInvBD.engageProduct(newOrderProductSet.getProduct().getProductCode(), newOrderProductSet.getQty());
			Response response = orderDAO.addProductToOrder(newOrderProductSet);
			return response;
		} catch (SysException e) {
			return new Response(false, e.getErrorCode());
		}
	}

	@Override
	public OrderProductSet getProductInOrder(Integer orderProductSetId) {
		logger.debug("GET product for orderProductSetId: " + orderProductSetId);
		return orderDAO.getProductInOrder(orderProductSetId);
	}

	@Override
	@Transactional
	public Response updateProductInOrder(OrderProductSet orderProductSet) {

		logger.debug("UPDATE orderProductSet: " + orderProductSet);
		OrderProductSet orgOrderProductSet = null;
		try {
			orgOrderProductSet = getProductInOrder(orderProductSet.getOrderProductSetId());

			if (!orgOrderProductSet.getOrderId().equals(orderProductSet.getOrderId())) {
				logger.error("Order id for orderProductSet: " + orderProductSet.getOrderProductSetId() + " in DB is "
						+ orgOrderProductSet.getOrderId() + " and in the received request is: "
						+ orderProductSet.getOrderId() + ". Unable to update product in order due to this mismatch!");
				return new Response(false, EpSystemError.ORDER_PRODUCT_ID_MISMATCH);
			}

			if (!orgOrderProductSet.getProduct().getProductId().equals(orderProductSet.getProduct().getProductId())) {
				logger.error("Product id for orderProductSet: " + orderProductSet.getOrderProductSetId() + " in DB is "
						+ orgOrderProductSet.getProduct().getProductId() + " and in the received request is: "
						+ orderProductSet.getProduct().getProductId()
						+ ". Unable to update product in order due to this mismatch!");
				return new Response(false, EpSystemError.ORDER_PRODUCT_ID_MISMATCH);
			}
			if (orgOrderProductSet.getQty() != orderProductSet.getQty()) {

				// Managing product inventory before updating product to the
				// order
				try {
					productInvBD.disengageProduct(orgOrderProductSet.getProduct().getProductCode(),
							orgOrderProductSet.getQty());
					productInvBD.engageProduct(orderProductSet.getProduct().getProductCode(), orderProductSet.getQty());
					orgOrderProductSet.setQty(orderProductSet.getQty());
					return orderDAO.updateProductInOrder(orgOrderProductSet);
				} catch (SysException e) {
					logger.error("System Exception: " +e.getMessage() );
					return new Response(false, e.getErrorCode());
				}
			} else {
				logger.debug("Quantities are same ... Nothing to update! Returning success.");
				return new Response(true, null);
			}
		} catch (EmptyResultDataAccessException emptyEx) {
			logger.info("This product does not exist in the order.Hence adding it to the order");
			Response response = this.addProductToOrder(orderProductSet);
			return response;
		}

	}

	@Override
	public Response deleteProductFromOrder(OrderProductSet orderProductSet) {
		logger.debug("DELETE orderProductSet: " + orderProductSet);

		// Managing product inventory before updating product to the order
		try {
			productInvBD.disengageProduct(orderProductSet.getProduct().getProductCode(), orderProductSet.getQty());
			return orderDAO.deleteProductFromOrder(orderProductSet);
		} catch (SysException e) {
			return new Response(false, e.getErrorCode());
		}

	}

}