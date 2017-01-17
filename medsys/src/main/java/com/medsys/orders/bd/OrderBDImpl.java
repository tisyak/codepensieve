package com.medsys.orders.bd;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.orders.dao.OrderDAO;
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

	/*@Override
	public List<Orders> listOrderswithAvailableDSCs() {
		return orderDAO.listOrderswithAvailableDSCs();
	}
	
	@Override
	public List<Orders>  monthlyOrderListHavingInwardDSCs(){
		return orderDAO.monthlyOrderListHavingInwardDSCs();
	}
	
	@Override
	public List<Orders>  monthlyOrderListHavingOutwardDSCs(){
		return orderDAO.monthlyOrderListHavingOutwardDSCs();
	}
   
*/
   
}