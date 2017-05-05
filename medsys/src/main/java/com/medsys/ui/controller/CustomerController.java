package com.medsys.ui.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medsys.adminuser.model.Roles;
import com.medsys.common.model.Response;
import com.medsys.customer.bd.CustomerBD;
import com.medsys.customer.model.Customer;
import com.medsys.master.bd.MasterDataBD;
import com.medsys.master.model.MasterData;
import com.medsys.product.model.Set;
import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.UIConstants;
import com.medsys.ui.util.jqgrid.JqgridResponse;
import com.medsys.util.EpSystemError;

@Controller
@Secured(Roles.MASTER_ADMIN)
public class CustomerController {

	static Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerBD customerBD;
	
	@Autowired
	private MasterDataBD masterDataBD;



	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LIST_ALL_CUSTOMERS, produces = "application/json")
	public @ResponseBody JqgridResponse<?> records(@RequestParam("_search") Boolean search,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "customerId", required = false) Integer customerId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord,
			@RequestParam(value = "setId", required = false) Integer setId,
			@RequestParam(value = "inventoryId", required = false) Integer inventoryId) {

		// Pageable pageRequest = new PageRequest(page-1, rows);
		logger.debug("list all products / search in inventory : setId: " + setId + " ,inventoryID: " + inventoryId);
		if (search == true) {
			// TODO: enable search??
			// return getFilteredRecords(filters, pageRequest);

		}

		List<Customer> customers = customerBD.getAllCustomers();

		logger.debug("Customers in inventory: " + customers);

			JqgridResponse<Customer> response = new JqgridResponse<Customer>();
			response.setRows(customers);

			response.setRecords(Integer.valueOf(customers.size()).toString());
			// Single page to display all products part of the set chosen for
			// inventory.
			response.setTotal(Integer.valueOf(1).toString());
			response.setPage(Integer.valueOf(1).toString());

			logger.debug("Customers already exist in inventory. Loading response from Inventory Customer List: " + response);

			return response;
		

	}

	@RequestMapping(value = UIActions.GET_CUSTOMER, produces = "application/json")
	public @ResponseBody Customer get(@RequestBody Customer customer) {
		logger.debug("Getting the product in inventory: " + customer);

		return customerBD.getCustomer(customer.getCustomerId());
	}
	
	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.MANAGE_CUSTOMERS, method = RequestMethod.GET)
	public String loadManageCustomerPage(Model model) {
		logger.info("IN: Customer/manage-GET: ");
		
		return MedsysUITiles.MANAGE_CUSTOMER.getTile();
	}

	@RequestMapping(value = UIActions.ADD_CUSTOMER, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response create(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "mobileNo", required = false) String mobileNo,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "pincode", required = false) String pincode) {
	
		logger.debug("Call to add customer.");
		
		Customer newCustomer = new Customer();
		newCustomer.setName(name);
		newCustomer.setEmail(email);
		newCustomer.setMobileNo(mobileNo);
		newCustomer.setAddress(address);
		newCustomer.setCity(city);
		newCustomer.setPincode(pincode);
		
		logger.debug("Adding the customer: " + newCustomer);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		newCustomer.setUpdateBy(auth.getName());
		newCustomer.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
		Response response = customerBD.addCustomer(newCustomer);
		return response;
	}
	
	

	@RequestMapping(value = UIActions.EDIT_CUSTOMER, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response update(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "mobileNo", required = false) String mobileNo,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "pincode", required = false) String pincode) {

		Customer customer = customerBD.getCustomer(id);
		if (customer.getCustomerId().equals(productId)) {
			Customer toBeUpdatedCustomer = new Customer();
			toBeUpdatedCustomer.setCustomerId(id);
			toBeUpdatedCustomer.setCustomer(customer.getCustomer());
			toBeUpdatedCustomer.setPrice(price);
			toBeUpdatedCustomer.setMrp(mrp);
			logger.debug("Adding the product to inventory: " + toBeUpdatedCustomer);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			toBeUpdatedCustomer.setUpdateBy(auth.getName());
			toBeUpdatedCustomer.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
			Response response = customerBD.updateCustomer(toBeUpdatedCustomer,qtyTobeAdded,qtyTobeDiscarded);
			return response;
		} else {
			logger.debug("Error in updating the product in inventory: " + customer + ".\nThe productCodes in request do not match with System data") ;
			return new Response(false, EpSystemError.SYSTEM_INTERNAL_ERROR);
		}
	}

	@RequestMapping(value = UIActions.DELETE_CUSTOMER, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response delete(@RequestParam Integer customerId) {

		Customer customer = customerBD.getCustomer(customerId);
		logger.debug("Deleting the product in inventory: " + customer);
		Response response = customerBD.deleteCustomer(customer);
		return response;
	}

	public static List<com.medsys.customer.model.Customer> map(Page<Customer> pageOfCustomers) {
		List<Customer> customers = new ArrayList<Customer>();
		for (Customer customer : pageOfCustomers) {
			customers.add(customer);
		}
		return customers;
	}
}