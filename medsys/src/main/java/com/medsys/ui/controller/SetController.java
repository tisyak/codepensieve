package com.medsys.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medsys.orders.model.OrderProductSet;
import com.medsys.product.bd.SetBD;
import com.medsys.product.model.SetPdtTemplate;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.UIConstants;
import com.medsys.ui.util.jqgrid.JqgridResponse;

@Controller
public class SetController {

	static Logger logger = LoggerFactory.getLogger(SetController.class);

	@Autowired
	private SetBD setBD;

	@RequestMapping(value = UIActions.FORWARD_SLASH
			+ UIActions.LIST_ALL_PRODUCT_SET_TEMPLATE, produces = "application/json")
	public @ResponseBody JqgridResponse<SetPdtTemplate> records(@RequestParam("_search") Boolean search,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "orderProductSetId", required = false) Integer orderProductSetId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord,
			@RequestParam(value = "setId", required = false) Integer setId) {

		// Pageable pageRequest = new PageRequest(page-1, rows);
		logger.debug("list all products / search : setId: " + setId);
		try {

			if (search == true) {
				// TODO: enable search??
				// return getFilteredRecords(filters, pageRequest);

			}

			List<SetPdtTemplate> setProducts = setBD.getAllProductsInSet(setId);

			JqgridResponse<SetPdtTemplate> response = new JqgridResponse<SetPdtTemplate>();
			response.setRows(setProducts);

			logger.debug("response: " + response);
			response.setRecords(Integer.valueOf(setProducts.size()).toString());
			response.setTotal(Integer.valueOf(setProducts.size()).toString());
			response.setPage(UIConstants.PAGE_SINGLE.getValue());
			return response;

		} catch (Exception e) {
			logger.error("Exception in AJAX call for product list: " + e.getMessage());
			JqgridResponse<SetPdtTemplate> response = new JqgridResponse<SetPdtTemplate>();

			response.setRows(null);
			response.setRecords(UIConstants.RECORDS_ZERO.getValue());
			response.setTotal(UIConstants.TOTAL_ZERO.getValue());
			response.setPage(UIConstants.PAGE_SINGLE.getValue());

			logger.debug("Exception sending null response: " + response);
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
	
	public static List<OrderProductSet> map(Page<OrderProductSet> pageOfOrderProducts) {
		List<OrderProductSet> orderProducts = new ArrayList<OrderProductSet>();
		for (OrderProductSet orderProduct : pageOfOrderProducts) {
			orderProducts.add(orderProduct);
		}
		return orderProducts;
	}
}