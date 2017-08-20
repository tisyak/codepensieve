package com.medsys.orders.dao;

import java.util.Date;
import java.util.List;

import com.medsys.common.model.Response;
import com.medsys.orders.model.OrderProductSet;
import com.medsys.orders.model.Orders;

public interface OrderDAO {

	public Response addOrder(Orders order);

	public Orders getOrder(Integer orderId);

	public Orders updateOrder(Orders order);

	public Response deleteOrder(Integer orderId);

	public List<Orders> getAllOrders();

	public List<Orders> searchForOrders(Orders order);

	public List<Orders> searchForOrdersInDateRange(Date startDate, Date endDate);

	public List<OrderProductSet> getAllProductsInOrder(Integer orderId);

	public Response addProductToOrder(OrderProductSet newOrderProductSet);

	public OrderProductSet getProductInOrder(Integer orderProductSetId);

	public Response updateProductInOrder(OrderProductSet orderProductSet);

	public Response deleteProductFromOrder(OrderProductSet orderProductSet);

	public Response updateOrderStatus(Orders order);

	public int getCountOfTotalOrdersForYear();

	public int getCountOfTotalOrdersInMonth();

	int getCountOfTotalOrdersInDateRange(Date startDate, Date endDate);

	public Orders getOrderWithInstr(Integer orderId);

	public List<Orders> searchForOrdersBeforeGivenDate(Date givenDate);

}