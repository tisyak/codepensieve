package com.medsys.orders.dao;

import java.util.List;

import com.medsys.orders.model.Orders;
 
public interface OrderDAO {
 
    public void addOrder(Orders order);
 
    public Orders getOrder(Integer orderId);
 
    public void updateOrder(Orders order);
 
    public void deleteOrder(Integer orderId);
 
    public List<Orders> getAllOrders();

	public List<Orders> searchForOrders(Orders order);
	
	
	
	
}