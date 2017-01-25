package com.medsys.orders.bd;

import java.util.List;

import com.medsys.common.model.Response;
import com.medsys.orders.model.OrderProductSet;
import com.medsys.orders.model.Orders;

public interface OrderBD {

	public void addOrder(Orders order);
	
	public Orders getOrder(Integer orderId);

	public void updateOrder(Orders order);

	public void deleteOrder(Integer orderId);

	public List<Orders> getAllOrders();
	
	public List<Orders> searchForOrders(Orders order);
	
	public List<OrderProductSet> getAllProductsInOrder(Integer orderId);

	public Response addProductToOrder(OrderProductSet newOrderProductSet);

	public OrderProductSet getProductInOrder(Integer orderProductSetId);

	public Response updateProuctInOrder(OrderProductSet orderProductSet);

	public Response deleteProductFromOrder(OrderProductSet orderProductSet);

	

}