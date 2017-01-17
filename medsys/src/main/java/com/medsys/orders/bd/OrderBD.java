package com.medsys.orders.bd;

import java.util.List;
import java.util.UUID;

import com.medsys.orders.model.Orders;

public interface OrderBD {

	public void addOrder(Orders order);
	
	public Orders getOrder(Integer orderId);

	public void updateOrder(Orders order);

	public void deleteOrder(Integer orderId);

	public List<Orders> getAllOrders();
	
	public List<Orders> searchForOrders(Orders order);

}