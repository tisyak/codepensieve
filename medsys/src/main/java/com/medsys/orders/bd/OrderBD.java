package com.medsys.orders.bd;

import java.util.List;

import com.medsys.common.model.Response;
import com.medsys.orders.model.OrderProductSet;
import com.medsys.orders.model.Orders;

public interface OrderBD {

	public Response addOrder(Orders order);
	
	public Orders getOrder(Integer orderId);

	public Orders updateOrder(Orders order);

	public Response deleteOrder(Integer orderId);

	public List<Orders> getAllOrders();
	
	public List<Orders> searchForOrders(Orders order);
	
	public List<OrderProductSet> getAllProductsInOrder(Integer orderId);

	public Response addProductToOrder(OrderProductSet newOrderProductSet);

	public OrderProductSet getProductInOrder(Integer orderProductSetId);

	public Response updateProductInOrder(OrderProductSet orderProductSet);

	public Response deleteProductFromOrder(OrderProductSet orderProductSet);

	

}