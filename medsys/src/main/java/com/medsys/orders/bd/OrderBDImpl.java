package com.medsys.orders.bd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.common.model.Response;
import com.medsys.orders.dao.OrderDAO;
import com.medsys.orders.model.OrderProductSet;
import com.medsys.orders.model.Orders;
 
@Service
@Transactional
public class OrderBDImpl implements OrderBD {
    static Logger logger = LoggerFactory.getLogger(OrderBDImpl.class);
     
    @Autowired
    private OrderDAO orderDAO;
 
    @Override
    public void addOrder(Orders user) {
    	logger.debug("OrderBD: Adding order.");
        orderDAO.addOrder(user);
    }
 
    @Override
    public Orders getOrder(Integer orderId)  {
        return orderDAO.getOrder(orderId);
    }
 
    @Override
    public void updateOrder(Orders user)  {
    	logger.debug("OrderBD: Updating order.");
        orderDAO.updateOrder(user);
    }
 
    @Override
    public void deleteOrder(Integer orderId)  {
        orderDAO.deleteOrder(orderId);
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
		return orderDAO.getAllProductsInOrder(orderId);
	}

	@Override
	public Response addProductToOrder(OrderProductSet newOrderProductSet) {
		logger.debug("ADD product to Order: " + newOrderProductSet);
		return orderDAO.addProductToOrder(newOrderProductSet);
	}

	@Override
	public OrderProductSet getProductInOrder(Integer orderProductSetId) {
		logger.debug("GET product for orderProductSetId: " + orderProductSetId);
		return orderDAO.getProductInOrder(orderProductSetId);
	}

	@Override
	public Response updateProuctInOrder(OrderProductSet orderProductSet) {
		logger.debug("UPDATE orderProductSet: " + orderProductSet);
		return orderDAO.updateProuctInOrder(orderProductSet);
	}

	@Override
	public Response deleteProductFromOrder(OrderProductSet orderProductSet) {
		logger.debug("DELETE orderProductSet: " + orderProductSet);
		return orderDAO.deleteProductFromOrder(orderProductSet);
	}


   
}