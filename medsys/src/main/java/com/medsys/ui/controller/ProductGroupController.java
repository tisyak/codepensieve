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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medsys.adminuser.model.Roles;
import com.medsys.common.model.Response;
import com.medsys.product.bd.ProductGroupBD;
import com.medsys.product.model.ProductGroup;
import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.jqgrid.JqgridResponse;

@Controller
@Secured(Roles.MASTER_ADMIN)
public class ProductGroupController {

	static Logger logger = LoggerFactory.getLogger(ProductGroupController.class);

	@Autowired
	private ProductGroupBD productGroupBD;

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LIST_ALL_PRODUCT_GROUP, produces = "application/json")
	public @ResponseBody JqgridResponse<?> records(@RequestParam("_search") Boolean search,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "groupId", required = false) Integer groupId,
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

		List<ProductGroup> productGroups = productGroupBD.getAllProductGroup();

		logger.debug("Products in inventory: " + productGroups);

		JqgridResponse<ProductGroup> response = new JqgridResponse<ProductGroup>();
		response.setRows(productGroups);

		response.setRecords(Integer.valueOf(productGroups.size()).toString());
		// Single page to display all products part of the set chosen for
		// inventory.
		response.setTotal(Integer.valueOf(1).toString());
		response.setPage(Integer.valueOf(1).toString());

		logger.debug("Products already exist in inventory. Loading response from Inventory Product List: " + response);

		return response;

	}

	@RequestMapping(value = UIActions.GET_PRODUCT_GROUP, produces = "application/json")
	public @ResponseBody ProductGroup get(@RequestBody ProductGroup productGroup) {
		
		logger.debug("Getting the product in inventory: " + productGroup);
		return productGroupBD.getProductGroup(productGroup.getGroupId());
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.MANAGE_PRODUCT_GROUP, method = RequestMethod.GET)
	public String loadManageProductGroupPage(Model model) {
		
		logger.info("IN: ProductGroup/manage-GET: ");
		return MedsysUITiles.MANAGE_PRODUCT_GROUP.getTile();
	}

	@RequestMapping(value = UIActions.ADD_PRODUCT_GROUP, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response create(@RequestParam(value = "groupName", required = false) String groupName,
			@RequestParam(value = "groupDesc", required = false) String groupDesc,
			HttpServletResponse httpServletResponse) {

		logger.debug("Call to add product to inventory.");

		ProductGroup newProductGroup = new ProductGroup();
		newProductGroup.setGroupName(groupName);
		newProductGroup.setGroupDesc(groupDesc);
		logger.debug("Adding the product group " + newProductGroup);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		newProductGroup.setUpdateBy(auth.getName());
		newProductGroup.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
		Response response = productGroupBD.addProductGroup(newProductGroup);
		if (!response.isStatus()) {
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return response;
	}

	@RequestMapping(value = UIActions.EDIT_PRODUCT_GROUP, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response update(@RequestParam Integer id,
			@RequestParam(value = "groupName", required = false) String groupName,
			@RequestParam(value = "groupDesc", required = false) String groupDesc,
			HttpServletResponse httpServletResponse) {

		ProductGroup toBeUpdatedProductGroup = new ProductGroup();
		toBeUpdatedProductGroup.setGroupId(id);
		toBeUpdatedProductGroup.setGroupName(groupName);
		toBeUpdatedProductGroup.setGroupDesc(groupDesc);
		logger.debug("Adding the productGroup: " + toBeUpdatedProductGroup);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		toBeUpdatedProductGroup.setUpdateBy(auth.getName());
		toBeUpdatedProductGroup.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
		Response response = productGroupBD.updateProductGroup(toBeUpdatedProductGroup);
		if (!response.isStatus()) {
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return response;
	}

	@RequestMapping(value = UIActions.DELETE_PRODUCT_GROUP, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response delete(@RequestParam Integer id, HttpServletResponse httpServletResponse) {

		logger.debug("Deleting the product in inventory: " + id);
		Response response = productGroupBD.deleteProductGroup(id);
		if (!response.isStatus()) {
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return response;
	}

	public static List<ProductGroup> map(Page<ProductGroup> pageOfProductGroups) {
		List<ProductGroup> productGroups = new ArrayList<ProductGroup>();
		for (ProductGroup productGroup : pageOfProductGroups) {
			productGroups.add(productGroup);
		}
		return productGroups;
	}
}