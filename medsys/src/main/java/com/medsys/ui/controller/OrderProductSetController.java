package com.medsys.ui.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medsys.adminuser.model.Roles;
import com.medsys.common.model.Response;
import com.medsys.master.model.OrderStatusCode;
import com.medsys.orders.bd.OrderBD;
import com.medsys.orders.model.OrderProductSet;
import com.medsys.product.bd.ProductMasterBD;
import com.medsys.product.bd.SetBD;
import com.medsys.product.model.ProductMaster;
import com.medsys.product.model.SetPdtTemplate;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.jqgrid.JqgridResponse;

@Controller
@Secured(Roles.MASTER_ADMIN)
public class OrderProductSetController {

	static Logger logger = LoggerFactory.getLogger(OrderProductSetController.class);

	@Autowired
	private OrderBD orderBD;

	@Autowired
	private SetBD setBD;

	@Autowired
	private ProductMasterBD productMasterBD;

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LIST_ALL_PRODUCT_ORDERS, produces = "application/json")
	public @ResponseBody JqgridResponse<?> records(@RequestParam("_search") Boolean search,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "orderProductSetId", required = false) Integer orderProductSetId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord,
			@RequestParam(value = "setId", required = false) Integer setId,
			@RequestParam(value = "orderId", required = false) Integer orderId,
			@RequestParam(value = "orderStatusCode", required = false) String orderStatusCode) {

		// Pageable pageRequest = new PageRequest(page-1, rows);
		logger.debug("list all products / search in order : setId: " + setId + " ,orderID: " + orderId);
		if (search == true) {
			// TODO: enable search??
			// return getFilteredRecords(filters, pageRequest);

		}

		List<OrderProductSet> orderProducts = orderBD.getAllProductsInOrder(orderId);

		logger.debug("Products in order: " + orderProducts);

		logger.debug("size: " + orderProducts.size() + "orderStatusCode: " + orderStatusCode);
		logger.debug(
				"(orderProducts == null || orderProducts.size() == 0) && OrderStatusCode.ACTIVE.getCode().equals(orderStatusCode) "
						+ ((orderProducts == null || orderProducts.size() == 0)
								&& OrderStatusCode.ACTIVE.getCode().equals(orderStatusCode)));

		if ((orderProducts == null || orderProducts.size() == 0)
				&& OrderStatusCode.ACTIVE.getCode().equals(orderStatusCode)) {
			List<SetPdtTemplate> setProducts = setBD.getAllProductsInSet(setId);

			JqgridResponse<SetPdtTemplate> response = new JqgridResponse<SetPdtTemplate>();
			response.setRows(setProducts);
			response.setRecords(Integer.valueOf(orderProducts.size()).toString());
			// Single page to display all products part of the set chosen for
			// order.
			response.setTotal(Integer.valueOf(1).toString());
			response.setPage(Integer.valueOf(1).toString());

			logger.debug("First Time. Loading response from Set Template: " + response);

			return response;

		} else {

			JqgridResponse<OrderProductSet> response = new JqgridResponse<OrderProductSet>();
			response.setRows(orderProducts);

			response.setRecords(Integer.valueOf(orderProducts.size()).toString());
			// Single page to display all products part of the set chosen for
			// order.
			response.setTotal(Integer.valueOf(1).toString());
			response.setPage(Integer.valueOf(1).toString());

			logger.debug(
					"Products already exist in order OR order status is NOT ACTIVE. Loading response from Order Product List: "
							+ response);

			return response;
		}

	}

	/**
	 * Helper method for returning filtered records
	 */
	/*
	 * public JqgridResponse<OrderProductSet> getFilteredRecords(String filters,
	 * Pageable pageRequest) { String qUsername = null; String qFirstName =
	 * null; String qLastName = null; Integer qRole = null;
	 * 
	 * JqgridFilter jqgridFilter = JqgridObjectMapper.map(filters); for
	 * (JqgridFilter.Rule rule: jqgridFilter.getRules()) { if
	 * (rule.getField().equals("username")) qUsername = rule.getData(); else if
	 * (rule.getField().equals("firstName")) qFirstName = rule.getData(); else
	 * if (rule.getField().equals("lastName")) qLastName = rule.getData(); else
	 * if (rule.getField().equals("role")) qRole =
	 * Integer.valueOf(rule.getData()); }
	 * 
	 * Page<User> users = null; if (qUsername != null) users =
	 * repository.findByUsernameLike("%"+qUsername+"%", pageRequest); if
	 * (qFirstName != null && qLastName != null) users =
	 * repository.findByFirstNameLikeAndLastNameLike("%"+qFirstName+"%",
	 * "%"+qLastName+"%", pageRequest); if (qFirstName != null) users =
	 * repository.findByFirstNameLike("%"+qFirstName+"%", pageRequest); if
	 * (qLastName != null) users =
	 * repository.findByLastNameLike("%"+qLastName+"%", pageRequest); if (qRole
	 * != null) users = repository.findByRole(qRole, pageRequest);
	 * 
	 * List<UserDto> userDtos = UserMapper.map(users); JqgridResponse<UserDto>
	 * response = new JqgridResponse<UserDto>(); response.setRows(userDtos);
	 * response.setRecords(Long.valueOf(users.getTotalElements()).toString());
	 * response.setTotal(Integer.valueOf(users.getTotalPages()).toString());
	 * response.setPage(Integer.valueOf(users.getNumber()+1).toString()); return
	 * response; }
	 */

	@RequestMapping(value = UIActions.GET_PRODUCT_ORDER, produces = "application/json")
	public @ResponseBody OrderProductSet get(@RequestBody OrderProductSet orderProductSet) {
		logger.debug("Getting the product in order: " + orderProductSet);

		return orderBD.getProductInOrder(orderProductSet.getOrderProductSetId());
	}

	@RequestMapping(value = UIActions.ADD_PRODUCT_ORDER, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response create(@RequestParam(value = "orderId", required = true) Integer orderId,
			@RequestParam(value = "product.productCode", required = true) String productCode,
			@RequestParam(value = "qty", required = true) Integer qty, HttpServletResponse httpServletResponse) {

		ProductMaster product = productMasterBD.getProductByCode(productCode);
		OrderProductSet newOrderProductSet = new OrderProductSet(orderId, null, product, qty);

		logger.debug("Adding the product to order: " + newOrderProductSet);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		newOrderProductSet.setUpdateBy(auth.getName());
		newOrderProductSet.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
		Response response = orderBD.addProductToOrder(newOrderProductSet);
		if (!response.isStatus()) {
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return response;
	}

	@RequestMapping(value = UIActions.EDIT_PRODUCT_ORDER, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response update(@RequestParam String id,
			@RequestParam(value = "orderId", required = true) Integer orderId,
			@RequestParam(value = "product.productCode", required = true) String productCode, @RequestParam Integer qty,
			HttpServletResponse httpServletResponse) {

		ProductMaster product = productMasterBD.getProductByCode(productCode);
		Integer convertIdtoInt = null;
		try {
			convertIdtoInt = Integer.parseInt(id);
		} catch (NumberFormatException numEx) {
			logger.debug("Received Id is not an Integer. Hence passing on Null");
		}

		OrderProductSet toBeUpdatedOrderProductSet = new OrderProductSet(orderId, convertIdtoInt, product, qty);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		toBeUpdatedOrderProductSet.setUpdateBy(auth.getName());
		toBeUpdatedOrderProductSet.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
		logger.debug("Updating the product in order: " + toBeUpdatedOrderProductSet);
		Response response = orderBD.updateProductInOrder(toBeUpdatedOrderProductSet);
		if (!response.isStatus()) {
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return response;

	}

	@RequestMapping(value = UIActions.DELETE_PRODUCT_ORDER, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response delete(@RequestParam Integer id, HttpServletResponse httpServletResponse) {

		OrderProductSet orderProductSet = orderBD.getProductInOrder(id);
		logger.debug("Deleting the product in order: " + orderProductSet);
		Response response = orderBD.deleteProductFromOrder(orderProductSet);
		if (!response.isStatus()) {
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return response;
	}

	public static List<OrderProductSet> map(Page<OrderProductSet> pageOfOrderProducts) {
		List<OrderProductSet> orderProducts = new ArrayList<OrderProductSet>();
		for (OrderProductSet orderProduct : pageOfOrderProducts) {
			orderProducts.add(orderProduct);
		}
		return orderProducts;
	}
}