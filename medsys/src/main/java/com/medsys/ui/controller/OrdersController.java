package com.medsys.ui.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medsys.adminuser.model.Roles;
import com.medsys.customer.bd.CustomerBD;
import com.medsys.master.bd.MasterDataBD;
import com.medsys.master.model.OrderStatusCode;
import com.medsys.master.model.OrderStatusMaster;
import com.medsys.orders.bd.OrderBD;
import com.medsys.orders.model.Orders;
import com.medsys.product.bd.SetBD;
import com.medsys.ui.jasper.service.OrdersReportDownloadService;
import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.UIConstants;
import com.medsys.util.EpMessage;

@Controller
@Secured(Roles.MASTER_ADMIN)
// @PreAuthorize("denyAll")
public class OrdersController extends SuperController {
	static Logger logger = LoggerFactory.getLogger(OrdersController.class);

	@Autowired
	private OrderBD ordersBD;

	@Autowired
	private CustomerBD customerBD;

	@Autowired
	private SetBD setBD;
	
	@Autowired
	private MasterDataBD masterDataBD;

	@Autowired
	private OrdersReportDownloadService ordersReportDownloadService;

	@RequestMapping(value = { UIActions.FORWARD_SLASH + UIActions.LIST_ALL_ORDERS }, method = RequestMethod.GET)
	public String listOfOrders(Model model) {
		logger.info("IN: Order/list-GET");

		List<Orders> orders = ordersBD.getAllOrders();
		model.addAttribute("ordersList", orders);

		// if there was an error in /add, we do not want to overwrite
		// the existing Order object containing the errors.
		if (!model.containsAttribute("order")) {
			logger.info("Adding Order object to model");
			Orders order = new Orders();
			model.addAttribute("order", order);
		}
		return MedsysUITiles.ORDERS_LIST.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_SEARCH_ORDERS, method = RequestMethod.GET)
	public String loadSearchOrders(@ModelAttribute Orders order, Model model) {

		logger.info("IN: Order/loadSearchOrders-GET");
		if (order == null) {
			order = new Orders();
		}
		model.addAttribute("order", order);
		return MedsysUITiles.SEARCH_ORDERS.getTile();
	}

	@RequestMapping(value = { UIActions.FORWARD_SLASH + UIActions.SEARCH_ORDERS }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public String searchOrders(@ModelAttribute Orders order, Model model) {
		logger.info("IN: Order/search");
		if (order.getOrderNumber() == null && order.getCustomer().getName() == null && order.getOrderDate() == null
				&& order.getOrderStatus() == null) {
			logger.info("Fetching All orders.");
			List<Orders> orders = ordersBD.getAllOrders();
			model.addAttribute("ordersList", orders);
		} else {
			logger.info("Fetching orders as per search criteria.");
			List<Orders> orders = ordersBD.searchForOrders(order);
			if (orders == null) {
				model.addAttribute(UIConstants.MSG_FOR_USER.getValue(),
						EpMessage.MSG_FOR_EMPTY_SEARCH_RESULT.getMsgCode());
			} else {
				model.addAttribute("ordersList", orders);
			}
		}
		model.addAttribute("order", order);
		return MedsysUITiles.SEARCH_ORDERS.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_ADD_ORDER, method = RequestMethod.GET)
	public String loadAddOrders(@ModelAttribute Orders order, Model model) {

		logger.info("IN: Order/loadAdd-GET");
		model.addAttribute("customerList", customerBD.getAllCustomers());
		model.addAttribute("setList", setBD.getAllSet());
		order = new Orders(true);
		model.addAttribute("order", order);
		order.setOrderStatus((OrderStatusMaster) masterDataBD.getbyCode(OrderStatusMaster.class, OrderStatusCode.ACTIVE.getCode()));
		return MedsysUITiles.ADD_ORDER.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.ADD_ORDER, method = RequestMethod.POST)
	public String addOrders(@Valid @ModelAttribute Orders order, Model model, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		logger.info("IN: Order/add-POST");

		if (result.hasErrors()) {
			logger.info("Order-add error: " + result.toString());
			redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.Order", result);
			redirectAttrs.addFlashAttribute("order", order);
			return MedsysUITiles.ADD_ORDER.getTile();
		} else {
			logger.info("Order-add: " + order);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			order.setUpdateBy(auth.getName());
			order.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
			ordersBD.addOrder(order);
			String message = "Order " + order.getOrderId() + " was successfully added";

			logger.info("Order-add: " + message + "\n Order: " + order + " setting the same in request");
			// Unable to directly update the modelAttribute. Hence, setting
			// orderId separately in request
			request.setAttribute("updatedOrderId", order.getOrderId());
			redirectAttrs.addFlashAttribute("updatedOrderId",order.getOrderId());
			return UIActions.REDIRECT + UIActions.LOAD_ADD_PRODUCT_ORDER;
		}
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_ADD_PRODUCT_ORDER, method = {RequestMethod.POST, RequestMethod.GET})
	public String loadAddProductsToOrder(
			@RequestParam(value = "updatedOrderId", required = false) Integer orderId,
			Model model) {
		
		if(orderId == null){
			logger.info("Checking in model for updatedOrderId = " + model.asMap().get("updatedOrderId"));
			orderId = (Integer) model.asMap().get("updatedOrderId");
		}
		
		
		logger.info(" IN: Order/loadAddProductsToOrder-POST orderId : " + orderId);
		model.addAttribute("order", ordersBD.getOrder(orderId));
		return MedsysUITiles.ADD_PRODUCTS_IN_ORDER.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.EDIT_ORDER, method = RequestMethod.GET)
	public String loadEditOrdersPage(@RequestParam(value = "orderId", required = false) Integer orderId, Model model) {
		logger.info("IN: Order/edit-GET:  order to query = " + orderId);
		if (!model.containsAttribute("order")) {

			logger.info("Adding Order object to model");
			Orders order = ordersBD.getOrder(orderId);
			logger.info("Order/edit-GET:  " + order);
			model.addAttribute("order", order);
		}
		model.addAttribute("customerList", customerBD.getAllCustomers());
		model.addAttribute("setList", setBD.getAllSet());

		return MedsysUITiles.EDIT_ORDER.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.SAVE_ORDER, method = RequestMethod.POST)
	public String saveOrders(@Valid @ModelAttribute Orders order, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		logger.info("IN: Order/save-POST: " + order);

		if (result.hasErrors()) {
			logger.info("Order-edit error: " + result.toString());
			redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.Order", result);
			redirectAttrs.addFlashAttribute("order", order);
			request.setAttribute("orderId", order.getOrderId());
			return UIActions.REDIRECT + UIActions.EDIT_ORDER;
		} else {
			logger.info("Order/edit-POST:  " + order.toString());
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			order.setUpdateBy(auth.getName());
			order.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
			ordersBD.updateOrder(order);
			String message = "Order " + order.getOrderId() + " was successfully edited";
			redirectAttrs.addFlashAttribute("message", message);
		}

		return UIActions.REDIRECT + UIActions.LIST_ALL_ORDERS;
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_DELETE_ORDER, method = RequestMethod.GET)
	public String loadDeleteOrdersPage(@RequestParam(value = "orderId", required = true) Integer orderId, Model model) {

		logger.info("IN: Order/delete-GET:  username to query = " + orderId);
		Orders order = ordersBD.getOrder(orderId);
		logger.info("Order/delete-GET:  " + order.toString());
		model.addAttribute("order", order);

		return MedsysUITiles.CONFIRM_DELETE_ORDER.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.DELETE_ORDER, method = RequestMethod.POST)
	public String deleteOrdersPage(Integer orderId, Model model) {

		logger.info("IN: Order/delete-POST | orderId = " + orderId);

		ordersBD.deleteOrder(orderId);
		String message = "Order with orderId: " + orderId + " was successfully deleted";
		model.addAttribute("message", message);
		return UIActions.REDIRECT + UIActions.LIST_ALL_ORDERS;
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.GET_ORDER_REPORT)
	public void download(@RequestParam String type, @RequestParam String token, @RequestParam Integer orderId,
			HttpServletResponse response) {
		logger.debug("Requesting download of type: " + type + " with token: " + token);
		ordersReportDownloadService.download(type, token, orderId, response);
	}
}