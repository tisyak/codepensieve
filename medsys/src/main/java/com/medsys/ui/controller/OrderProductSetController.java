package com.medsys.ui.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medsys.common.model.Response;
import com.medsys.orders.bd.OrderBD;
import com.medsys.orders.model.OrderProductSet;
import com.medsys.ui.util.jqgrid.JqgridResponse;

//TODO: Remove hardcoding!!
@Controller
@RequestMapping("/orderproduct")
public class OrderProductSetController {
	
	@Autowired
	private OrderBD orderBD;
	
	@RequestMapping
	public String getOrderProductSetPage() {
		//TODO: Change to constant
		return "orderProductSet";
	}
	
	@RequestMapping(value="/records", produces="application/json")
	public @ResponseBody JqgridResponse<OrderProductSet> records(
    		@RequestParam("_search") Boolean search,
    		@RequestParam(value="filters", required=false) String filters,
    		@RequestParam(value="orderId", required=false) Integer orderId,
    		@RequestParam(value="page", required=false) Integer page,
    		@RequestParam(value="rows", required=false) Integer rows,
    		@RequestParam(value="sidx", required=false) String sidx,
    		@RequestParam(value="sord", required=false) String sord) {

		//Pageable pageRequest = new PageRequest(page-1, rows);
		
		if (search == true) {
			//TODO: enable search??
			//return getFilteredRecords(filters, pageRequest);
			
		} 
			
		List<OrderProductSet> orderProducts = orderBD.getAllProductsInOrder(orderId);
		
		JqgridResponse<OrderProductSet> response = new JqgridResponse<OrderProductSet>();
		response.setRows(orderProducts);
		/*
		 	response.setRecords(Long.valueOf(pageOfOrderProducts.getTotalElements()).toString());
			response.setTotal(Integer.valueOf(pageOfOrderProducts.getTotalPages()).toString());
			response.setPage(Integer.valueOf(pageOfOrderProducts.getNumber()+1).toString());
		 */
		
		response.setRecords(Integer.valueOf(orderProducts.size()).toString());
		response.setTotal(Integer.valueOf(orderProducts.size()).toString());
		response.setPage(Integer.valueOf(1).toString());
		
		return response;
	}
	
	/**
	 * Helper method for returning filtered records
	 */
	/*public JqgridResponse<OrderProductSet> getFilteredRecords(String filters, Pageable pageRequest) {
		String qUsername = null;
		String qFirstName = null;
		String qLastName = null;
		Integer qRole = null;
		
		JqgridFilter jqgridFilter = JqgridObjectMapper.map(filters);
		for (JqgridFilter.Rule rule: jqgridFilter.getRules()) {
			if (rule.getField().equals("username"))
				qUsername = rule.getData();
			else if (rule.getField().equals("firstName"))
				qFirstName = rule.getData();
			else if (rule.getField().equals("lastName"))
				qLastName = rule.getData();
			else if (rule.getField().equals("role"))
				qRole = Integer.valueOf(rule.getData());
		}
		
		Page<User> users = null;
		if (qUsername != null) 
			users = repository.findByUsernameLike("%"+qUsername+"%", pageRequest);
		if (qFirstName != null && qLastName != null) 
			users = repository.findByFirstNameLikeAndLastNameLike("%"+qFirstName+"%", "%"+qLastName+"%", pageRequest);
		if (qFirstName != null) 
			users = repository.findByFirstNameLike("%"+qFirstName+"%", pageRequest);
		if (qLastName != null) 
			users = repository.findByLastNameLike("%"+qLastName+"%", pageRequest);
		if (qRole != null) 
			users = repository.findByRole(qRole, pageRequest);
		
		List<UserDto> userDtos = UserMapper.map(users);
		JqgridResponse<UserDto> response = new JqgridResponse<UserDto>();
		response.setRows(userDtos);
		response.setRecords(Long.valueOf(users.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(users.getTotalPages()).toString());
		response.setPage(Integer.valueOf(users.getNumber()+1).toString());
		return response;
	}*/
	
	@RequestMapping(value="/get", produces="application/json")
	public @ResponseBody OrderProductSet get(@RequestBody OrderProductSet orderProductSet) {
		return orderBD.getProductInOrder(orderProductSet.getOrderProductSetId());
	}

	@RequestMapping(value="/create", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody Response create(
			@RequestParam Integer setId,
			@RequestParam String productCode,
			@RequestParam String lotNo,
			@RequestParam Integer qty) {
		
		OrderProductSet newOrderProductSet = new OrderProductSet(setId, productCode, lotNo, qty);
		Response response = orderBD.addProductToOrder(newOrderProductSet);
		return response;
	}
	
	@RequestMapping(value="/update", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody Response update(
			@RequestParam Integer orderProductSetId,
			@RequestParam Integer qty) {
		
		OrderProductSet orderProductSet = orderBD.getProductInOrder(orderProductSetId);
		Response response = orderBD.updateProuctInOrder(orderProductSet);
		return response;
	}
	
	@RequestMapping(value="/delete", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody Response delete(
			@RequestParam Integer orderProductSetId) {

		OrderProductSet orderProductSet = orderBD.getProductInOrder(orderProductSetId);
		Response response = orderBD.deleteProductFromOrder(orderProductSet);
		return response;
	}
	
	public static List<OrderProductSet> map(Page<OrderProductSet> pageOfOrderProducts) {
		List<OrderProductSet> orderProducts = new ArrayList<OrderProductSet>();
		for (OrderProductSet orderProduct: pageOfOrderProducts) {
			orderProducts.add(orderProduct);
		}
		return orderProducts;
	}
}