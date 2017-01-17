package com.medsys.ui.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medsys.adminuser.model.Roles;
import com.medsys.customer.bd.CustomerBD;
import com.medsys.customer.model.Customer;
import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.UIConstants;
import com.medsys.util.EpMessage;

@Controller
@Secured(Roles.MASTER_ADMIN)
// @PreAuthorize("denyAll")
public class CustomerController  extends SuperController {
	static Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerBD customerBD;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = { UIActions.FORWARD_SLASH
			+ UIActions.LIST_ALL_CUSTOMERS }, method = RequestMethod.GET)
	public String listOfcustomerInfos(Model model) {
		logger.info("IN: customerInfo/list-GET");

		List<Customer> customers = customerBD.getAllCustomers();
		model.addAttribute("customerInfos", customers);

		// if there was an error in /add, we do not want to overwrite
		// the existing customerInfo object containing the errors.
		if (!model.containsAttribute("customerInfos")) {
			logger.info("Adding customerInfo object to model");
			Customer customer = new Customer();
			model.addAttribute("customerInfo", customer);
		}
		return MedsysUITiles.CUSTOMER_LIST.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH
			+ UIActions.LOAD_SEARCH_CUSTOMER, method = RequestMethod.GET)
	public String loadSearchCustomer(@ModelAttribute Customer customer,
			Model model) {

		logger.info("IN: customerInfo/loadSearchCustomer-GET");
		model.addAttribute("customerInfo", customer);
		return MedsysUITiles.SEARCH_CUSTOMER.getTile();
	}

	@RequestMapping(value = { UIActions.FORWARD_SLASH + UIActions.SEARCH_CUSTOMER }, method = {
			RequestMethod.GET, RequestMethod.POST })
	public String searchCustomers(@ModelAttribute Customer customer,
			Model model) {
		logger.info("IN: customerInfo/search");
		if (customer.getName() == null) {
			logger.info("Fetching All customers.");
			List<Customer> customers = customerBD.getAllCustomers();
			model.addAttribute("customerInfos", customers);
		}else{
			logger.info("Fetching customers as per search criteria.");
			List<Customer> customers = customerBD.searchForCustomers(customer);
			if (customers == null) {
				model.addAttribute(UIConstants.MSG_FOR_USER.getValue(),EpMessage.MSG_FOR_EMPTY_SEARCH_RESULT.getMsgCode());
			}else{
				model.addAttribute("customerInfos", customers);
			}
		}

		return MedsysUITiles.SEARCH_CUSTOMER.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_ADD_CUSTOMER, method = RequestMethod.GET)
	public String loadAddCustomer(@ModelAttribute Customer customer,
			Model model) {

		logger.info("IN: customerInfo/loadAdd-GET");
		model.addAttribute("customerInfo", customer);
		return MedsysUITiles.ADD_CUSTOMER.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.ADD_CUSTOMER, method = RequestMethod.POST)
	public String addCustomer(@Valid @ModelAttribute Customer customer,
			BindingResult result, RedirectAttributes redirectAttrs) {

		logger.info("IN: customerInfo/add-POST");

		if (result.hasErrors()) {
			logger.info("customerInfo-add error: " + result.toString());
			redirectAttrs.addFlashAttribute(
					"org.springframework.validation.BindingResult.customerInfo",
					result);
			redirectAttrs.addFlashAttribute("customerInfo", customer);
			return MedsysUITiles.ADD_CUSTOMER.getTile();
		} else {
			customerBD.addCustomer(customer);
			String message = "Admin Customer " + customer.getCustomerId()
					+ " was successfully added";
			redirectAttrs.addFlashAttribute("message", message);
			return UIActions.REDIRECT + UIActions.LIST_ALL_CUSTOMERS;
		}
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.EDIT_CUSTOMER, method = RequestMethod.GET)
	public String loadEditCustomerPage(
			@RequestParam(value = "customerId", required = true) String customerId,
			Model model) {

		logger.info("IN: customerInfo/edit-GET:  username to query = " + customerId);

		if (!model.containsAttribute("customerInfo")) {
			logger.info("Adding customerInfo object to model");
			Customer customer = customerBD.getCustomer(UUID
					.fromString(customerId));
			logger.info("customerInfo/edit-GET:  " + customer.toString());
			model.addAttribute("customerInfo", customer);
		}

		return MedsysUITiles.EDIT_CUSTOMER.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.SAVE_CUSTOMER, method = RequestMethod.POST)
	public String saveCustomer(@Valid @ModelAttribute Customer customer,
			BindingResult result, RedirectAttributes redirectAttrs) {

		logger.info("IN: customerInfo/save-POST: " + customer);

		if (result.hasErrors()) {
			logger.info("customerInfo-edit error: " + result.toString());
			redirectAttrs.addFlashAttribute(
					"org.springframework.validation.BindingResult.customerInfo",
					result);
			redirectAttrs.addFlashAttribute("customerInfo", customer);
			return UIActions.REDIRECT + UIActions.EDIT_CUSTOMER + "?customerId="
					+ customer.getCustomerId();
		} else {
			logger.info("customerInfo/edit-POST:  " + customer.toString());
			customerBD.updateCustomer(customer);
			String message = "customerInfo " + customer.getCustomerId()
					+ " was successfully edited";
			redirectAttrs.addFlashAttribute("message", message);
		}

		return UIActions.REDIRECT + UIActions.LIST_ALL_CUSTOMERS;
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH
			+ UIActions.LOAD_DELETE_CUSTOMER, method = RequestMethod.GET)
	public String loadDeleteCustomerPage(
			@RequestParam(value = "customerId", required = true) String customerId,
			Model model) {

		logger.info("IN: customerInfo/delete-GET:  username to query = "
				+ customerId);
		Customer customer = customerBD.getCustomer(UUID
				.fromString(customerId));
		logger.info("customerInfo/delete-GET:  " + customer.toString());
		model.addAttribute("customerInfo", customer);

		return MedsysUITiles.CONFIRM_DELETE_CUSTOMER.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH
			+ UIActions.DELETE_CUSTOMER, method = RequestMethod.POST)
	public String deleteCustomerPage(
			@RequestParam(value = "customerId", required = true) String customerId,
			Model model) {

		logger.info("IN: customerInfo/delete-POST | customerId = " + customerId);
		
		customerBD.deleteCustomer(UUID.fromString(customerId));
		String message = "customerInfo with customerId: " + customerId
				+ " was successfully deleted";
		model.addAttribute("message", message);
		return UIActions.REDIRECT + UIActions.LIST_ALL_CUSTOMERS;
	}
	

	
}