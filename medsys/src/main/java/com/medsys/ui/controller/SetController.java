package com.medsys.ui.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medsys.common.model.Response;
import com.medsys.orders.model.OrderProductSet;
import com.medsys.product.bd.ProductMasterBD;
import com.medsys.product.bd.SetBD;
import com.medsys.product.model.ProductMaster;
import com.medsys.product.model.Set;
import com.medsys.product.model.SetPdtTemplate;
import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.UIConstants;
import com.medsys.ui.util.jqgrid.JqgridResponse;
import com.medsys.util.EpMessage;

@Controller
public class SetController {

	static Logger logger = LoggerFactory.getLogger(SetController.class);

	@Autowired
	private SetBD setBD;
	
	@Autowired
	private ProductMasterBD productMasterBD;
	
	@RequestMapping(value = { UIActions.FORWARD_SLASH + UIActions.LIST_ALL_SETS }, method = RequestMethod.GET)
	public String listOfSets(Model model) {
		logger.info("IN: Set/list-GET");

		List<Set> lstSet = setBD.getAllSet();
		model.addAttribute("setList", lstSet);

		// if there was an error in /add, we do not want to overwrite
		// the existing Set object containing the errors.
		if (!model.containsAttribute("set")) {
			logger.info("Adding Set object to model");
			Set set = new Set();
			model.addAttribute("set", set);
		}
		return MedsysUITiles.SET_LIST.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_SEARCH_SETS, method = RequestMethod.GET)
	public String loadSearchSet(@ModelAttribute Set set, Model model) {

		logger.info("IN: Set/loadSearchSet-GET");
		if (set == null) {
			set = new Set();
		}
		model.addAttribute("set", set);
		return MedsysUITiles.SEARCH_SET.getTile();
	}

	@RequestMapping(value = { UIActions.FORWARD_SLASH + UIActions.SEARCH_SETS }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public String searchSet(@ModelAttribute Set set, Model model) {
		logger.info("IN: Set/search");
		if (set.getSetName() == null && set.getSetDesc() == null) {
			logger.info("Fetching All set.");
			List<Set> lstSet = setBD.getAllSet();
			model.addAttribute("setList", lstSet);
		} else {
			logger.info("Fetching set as per search criteria.");
			List<Set> lstSet = setBD.searchForSet(set);
			if (lstSet == null) {
				model.addAttribute(UIConstants.MSG_FOR_USER.getValue(),
						EpMessage.MSG_FOR_EMPTY_SEARCH_RESULT.getMsgCode());
			} else {
				model.addAttribute("setList", lstSet);
			}
		}
		model.addAttribute("set", set);
		return MedsysUITiles.SEARCH_SET.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_ADD_SET, method = RequestMethod.GET)
	public String loadAddSet(@ModelAttribute Set set, Model model) {

		logger.info("IN: Set/loadAdd-GET");
		model.addAttribute("set", new Set());
		return MedsysUITiles.ADD_SET.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.ADD_SET, method = RequestMethod.POST)
	public String addSet(@Valid @ModelAttribute Set set, Model model, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		logger.info("IN: Set/add-POST");

		if (result.hasErrors()) {
			logger.info("Set-add error: " + result.toString());
			model.addAttribute(UIConstants.MSG_FOR_USER_ERROR.getValue(), result.getAllErrors());
			model.addAttribute("set", set);
			return  UIActions.FORWARD + UIActions.LOAD_ADD_SET;
		} else {
			logger.info("Set-add: " + set);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			set.setUpdateBy(auth.getName());
			set.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
			setBD.addSet(set);
			String message = "Set " + set.getSetId() + " was successfully added";

			logger.info("Set-add: " + message + "\n Set: " + set + " setting the same in request");
			// Unable to directly update the modelAttribute. Hence, setting
			// setId separately in request
			request.setAttribute("updatedSetId", set.getSetId());
			redirectAttrs.addFlashAttribute("updatedSetId", set.getSetId());
			return UIActions.REDIRECT + UIActions.EDIT_SET;
		}
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.EDIT_SET, method = RequestMethod.GET)
	public String loadEditSetPage(@RequestParam(value = "setId", required = false) Integer setId, Model model) {
		logger.info("IN: Set/edit-GET:  set to query = " + setId);

		if (setId == null) {
			logger.info("Checking in model for updatedSetId = " + model.asMap().get("updatedSetId"));
			setId = (Integer) model.asMap().get("updatedSetId");
		}

		if (!model.containsAttribute("set")) {

			logger.info("Adding Set object to model");
			Set set = setBD.getSet(setId);
			logger.info("Set/edit-GET:  " + set);
			model.addAttribute("set", set);
		}
		return MedsysUITiles.EDIT_SET.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.SAVE_SET, method = RequestMethod.POST)
	public String saveSet(@Valid @ModelAttribute Set set, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		logger.info("IN: Set/save-POST: " + set);

		if (result.hasErrors()) {
			logger.info("Set-edit error: " + result.toString());
			redirectAttrs.addFlashAttribute(UIConstants.MSG_FOR_USER_ERROR.getValue(), result.getAllErrors());
			redirectAttrs.addFlashAttribute("set", set);
			request.setAttribute("setId", set.getSetId());
			return UIActions.REDIRECT + UIActions.EDIT_SET;
		} else {
			logger.info("Set/edit-POST:  " + set.toString());
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			set.setUpdateBy(auth.getName());
			set.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
			setBD.updateSet(set);
			String message = "Set " + set.getSetId() + " was successfully edited";
			redirectAttrs.addFlashAttribute(UIConstants.MSG_FOR_USER.getValue(), message);
		}

		return UIActions.REDIRECT + UIActions.LIST_ALL_SETS;
	}


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
	
	@RequestMapping(value = UIActions.FORWARD_SLASH
			+ UIActions.SEARCH_PRODUCTS_BY_SET_GRP_URL, produces = "application/json")
	public @ResponseBody String searchBySetandGroup(
			@RequestParam(value = "setId", required = false) Integer setId,
			@RequestParam(value = "groupId", required = false) Integer groupId) {

		
		logger.debug("search By Set and Group: " );
		List<SetPdtTemplate> setPdtTemplates;
		List<ProductMaster> pdtMasterList;
		if((setId!=null && !setId.equals(0)) || (groupId!=null && !groupId.equals(0))){
			setPdtTemplates = setBD.getAllProductsInSetAndGroup(setId,groupId);
			pdtMasterList = new ArrayList<>(setPdtTemplates.size());
			for (SetPdtTemplate pdt : setPdtTemplates) {
				pdtMasterList.add(pdt.getProduct());
			}
		}else{
			logger.debug("No search criteria given. Hence, retrieving complete Product Master List: " );
			pdtMasterList = productMasterBD.getAllProductMaster();
		}
	
		final StringWriter sw =new StringWriter();
	    final ObjectMapper mapper = new ObjectMapper();

	    try {
			mapper.writeValue(sw, pdtMasterList);
		} catch (IOException e) {
			logger.error("Unable to convert to JSON. Error: " + e.getMessage());
		}

		String productsList = sw.toString();
		logger.debug("productsList: " + productsList);
		
		return productsList;
	}

	@RequestMapping(value = UIActions.GET_PRODUCT_SET_TEMPLATE, produces = "application/json")
	public @ResponseBody SetPdtTemplate get(@RequestBody SetPdtTemplate setPdtTemplate) {
		logger.debug("Getting the product in set: " + setPdtTemplate);

		return setBD.getProductInSet(setPdtTemplate.getSetPdtId());
	}

	@RequestMapping(value = UIActions.ADD_PRODUCT_SET_TEMPLATE, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response create(@RequestParam(value = "setId", required = true) Integer setId,
			@RequestParam(value = "product.productCode", required = true) String productCode,
			@RequestParam(value = "qty", required = true) Integer qty,
			HttpServletResponse httpServletResponse) {

		ProductMaster product = productMasterBD.getProductByCode(productCode);
		Set parentSet = setBD.getSet(setId);
		SetPdtTemplate newSetPdtTemplate = new SetPdtTemplate(parentSet, null, product, qty);

		logger.debug("Adding the product to set: " + newSetPdtTemplate);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		newSetPdtTemplate.setUpdateBy(auth.getName());
		newSetPdtTemplate.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
		Response response = setBD.addProductToSet(newSetPdtTemplate);
		if(!response.isStatus()){
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return response;
	}

	@RequestMapping(value = UIActions.EDIT_PRODUCT_SET_TEMPLATE, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response update(@RequestParam String id,
			@RequestParam(value = "setId", required = true) Integer setId,
			@RequestParam(value = "product.productCode", required = true) String productCode,
			@RequestParam Integer qty,
			HttpServletResponse httpServletResponse) {

		ProductMaster product = productMasterBD.getProductByCode(productCode);
		Integer convertIdtoInt = null;
		try {
			convertIdtoInt = Integer.parseInt(id);
		} catch (NumberFormatException numEx) {
			logger.debug("Received Id is not an Integer. Hence passing on Null");
		}
		Set parentSet = setBD.getSet(setId);
		SetPdtTemplate toBeUpdatedSetPdtTemplate = new SetPdtTemplate(parentSet, convertIdtoInt, product, qty);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		toBeUpdatedSetPdtTemplate.setUpdateBy(auth.getName());
		toBeUpdatedSetPdtTemplate.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
		logger.debug("Updating the product in set: " + toBeUpdatedSetPdtTemplate);
		Response response = setBD.updateProductInSet(toBeUpdatedSetPdtTemplate);
		if(!response.isStatus()){
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return response;

	}

	@RequestMapping(value = UIActions.DELETE_PRODUCT_SET_TEMPLATE, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response delete(@RequestParam Integer id,
			HttpServletResponse httpServletResponse) {

		SetPdtTemplate setPdtTemplate = setBD.getProductInSet(id);
		logger.debug("Deleting the product in set: " + setPdtTemplate);
		Response response = setBD.deleteProductFromSet(setPdtTemplate);
		if(!response.isStatus()){
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