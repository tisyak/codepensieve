package com.medsys.orders.bd;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medsys.orders.model.Orders;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class OrderJasperDatasourceService {

	@Autowired
	private OrderBD orderBD;
	
	/**
	 * Returns a data source that's wrapped within {@link JRDataSource}
	 * @return
	 */
	public JRDataSource getOrderData(Integer orderId) {
		//Change the order ID
		Orders order = orderBD.getOrder(orderId);
		List<Orders> lstOrder = new ArrayList<Orders>();
		lstOrder.add(order);
		// Return wrapped collection
		return new JRBeanCollectionDataSource(lstOrder);
	}
}