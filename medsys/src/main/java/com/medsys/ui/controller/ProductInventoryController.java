package com.medsys.ui.controller;

import java.math.BigDecimal;
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
import com.medsys.product.bd.ProductInvBD;
import com.medsys.product.bd.ProductMasterBD;
import com.medsys.product.bd.SetBD;
import com.medsys.product.model.ProductGroup;
import com.medsys.product.model.ProductInv;
import com.medsys.product.model.ProductMaster;
import com.medsys.product.model.Set;
import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.UIConstants;
import com.medsys.ui.util.jqgrid.JqgridResponse;
import com.medsys.util.EpSystemError;

@Controller
@Secured(Roles.MASTER_ADMIN)
public class ProductInventoryController {

	static Logger logger = LoggerFactory.getLogger(ProductInventoryController.class);

	@Autowired
	private ProductInvBD productInvBD;

	@Autowired
	private SetBD setBD;

	@Autowired
	private ProductMasterBD productMasterBD;

	@RequestMapping(value = UIActions.FORWARD_SLASH
			+ UIActions.LIST_ALL_PRODUCT_INVENTORY, produces = "application/json")
	public @ResponseBody JqgridResponse<?> records(@RequestParam("_search") Boolean search,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "productInvId", required = false) Integer productInvId,
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

		List<ProductInv> productInvs = productInvBD.getAllProductInv();

		logger.debug("Products in inventory: " + productInvs);

		JqgridResponse<ProductInv> response = new JqgridResponse<ProductInv>();
		response.setRows(productInvs);

		response.setRecords(Integer.valueOf(productInvs.size()).toString());
		// Single page to display all products part of the set chosen for
		// inventory.
		response.setTotal(Integer.valueOf(1).toString());
		response.setPage(Integer.valueOf(1).toString());

		logger.debug("Products already exist in inventory. Loading response from Inventory Product List: " + response);

		return response;

	}

	@RequestMapping(value = UIActions.GET_PRODUCT_INVENTORY, produces = "application/json")
	public @ResponseBody ProductInv get(@RequestBody ProductInv productInv) {
		logger.debug("Getting the product in inventory: " + productInv);

		return productInvBD.getProduct(productInv.getProductInvId());
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.MANAGE_PRODUCT_INVENTORY, method = RequestMethod.GET)
	public String loadManageProductInventoryPage(Model model) {
		logger.info("IN: ProductInv/manage-GET: ");

		/**
		 * START Of Converting the MasterData into the format of
		 * "Code:DisplayValue" as required by the JQGrid Select Options
		 */

		/** Set Listing for ADD Product Filter **/
		List<Set> setMasterList = setBD.getAllSet();
		String setList = "{";
		for (Set set : setMasterList) {
			setList += "'" + set.getSetId() + "':'" + set.getSetName() + "',";
		}
		setList = setList.substring(0, setList.length() - 1);
		setList += "}";
		// Figure out how to cache all these values
		model.addAttribute("setList", setList);

		/** ProductGroup Listing for ADD Product Filter **/
		List<ProductGroup> productGroupMasterList = null;
		if (setMasterList.size() > 0) {
			productGroupMasterList = setBD.getAllProductGroupForSet(setMasterList.get(0).getSetId());
			String pdtGroupList = "{";
			for (ProductGroup productGroup : productGroupMasterList) {
				pdtGroupList += "'" + productGroup.getGroupId() + "':'" + productGroup.getGroupName() + "',";
			}
			pdtGroupList = pdtGroupList.substring(0, pdtGroupList.length() - 1);
			pdtGroupList += "}";
			// Figure out how to cache all these values
			model.addAttribute("pdtGroupList", pdtGroupList);
		} else {
			logger.error("NO SETs found hence, product group search aborted!");
		}
		
		if (productGroupMasterList != null && productGroupMasterList.size() > 0) {
			List<ProductMaster> productMasterList = setBD.getAllProductsInSetAndGroup(setMasterList.get(0).getSetId(),
					productGroupMasterList.get(0).getGroupId());
			String pdtList = "{";
			for (ProductMaster product : productMasterList) {
				pdtList += "'" + product.getProductId() + "':'" + product.getProductCode() + " - "
						+ product.getProductDesc() + "("+product.getProductId()+")"+"',";
			}
			pdtList = pdtList.substring(0, pdtList.length() - 1);
			pdtList += "}";
			logger.debug("pdtList: "+ pdtList);
			// Figure out how to cache all these values
			model.addAttribute("pdtList", pdtList);
		} else {
			logger.error("NO SETs found hence, products search aborted!");
		}
		
		/**
		 * END Of Converting the MasterData into the format of
		 * "Code:DisplayValue" as required by the JQGrid Select Options
		 */

		return MedsysUITiles.MANAGE_PRODUCT_INV.getTile();
	}

	@RequestMapping(value = UIActions.ADD_PRODUCT_INVENTORY, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response create(@RequestParam(value = "product.productId", required = false) Integer productId,
			@RequestParam(value = "orgQty", required = false) Integer qty,
			@RequestParam(value = "mrp", required = false) BigDecimal mrp,
			@RequestParam(value = "price", required = false) BigDecimal price,
			HttpServletResponse httpServletResponse) {

		logger.debug("Call to add product to inventory.");

		ProductMaster product = productMasterBD.getProduct(productId);
		ProductInv newProductInv = new ProductInv();
		newProductInv.setProduct(product);
		newProductInv.setOrgQty(qty);
		newProductInv.setAvailableQty(qty);

		// Initializing all other Quantity specifiers to Zero!
		Integer initializeToZero = Integer.parseInt(UIConstants.EMPTY_QTY.getValue());
		newProductInv.setDiscardedQty(initializeToZero);
		newProductInv.setEngagedQty(initializeToZero);
		newProductInv.setSoldQty(initializeToZero);

		newProductInv.setMrp(mrp);
		newProductInv.setPrice(price);

		logger.debug("Adding the product to inventory: " + newProductInv);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		newProductInv.setUpdateBy(auth.getName());
		newProductInv.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
		Response response = productInvBD.addProduct(newProductInv);
		if (!response.isStatus()) {
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return response;
	}

	@RequestMapping(value = UIActions.EDIT_PRODUCT_INVENTORY, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response update(@RequestParam Integer id,
			@RequestParam(value = "product.productId", required = false) Integer productId,
			@RequestParam(value = "qtyTobeAdded", required = false) Integer qtyTobeAdded,
			@RequestParam(value = "qtyTobeDiscarded", required = false) Integer qtyTobeDiscarded,
			@RequestParam(value = "price", required = false) BigDecimal price,
			@RequestParam(value = "mrp", required = false) BigDecimal mrp,
			HttpServletResponse httpServletResponse) {

		ProductInv productInv = productInvBD.getProduct(id);
		if (productInv.getProduct().getProductId().equals(productId)) {
			ProductInv toBeUpdatedProductInv = new ProductInv();
			toBeUpdatedProductInv.setProductInvId(id);
			toBeUpdatedProductInv.setProduct(productInv.getProduct());
			toBeUpdatedProductInv.setPrice(price);
			toBeUpdatedProductInv.setMrp(mrp);
			logger.debug("Adding the product to inventory: " + toBeUpdatedProductInv);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			toBeUpdatedProductInv.setUpdateBy(auth.getName());
			toBeUpdatedProductInv.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
			Response response = productInvBD.updateProduct(toBeUpdatedProductInv, qtyTobeAdded, qtyTobeDiscarded);
			if (!response.isStatus()) {
				httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			return response;
		} else {
			logger.debug("Error in updating the product in inventory: " + productInv
					+ ".\nThe productCodes in request do not match with System data");
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return new Response(false, EpSystemError.SYSTEM_INTERNAL_ERROR);
		}
	}

	@RequestMapping(value = UIActions.DELETE_PRODUCT_INVENTORY, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response delete(@RequestParam Integer productInvId,
			HttpServletResponse httpServletResponse) {

		ProductInv productInv = productInvBD.getProduct(productInvId);
		logger.debug("Deleting the product in inventory: " + productInv);
		Response response = productInvBD.deleteProduct(productInv);
		if(!response.isStatus()){
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return response;
	}

	public static List<ProductInv> map(Page<ProductInv> pageOfProductInvs) {
		List<ProductInv> productInvs = new ArrayList<ProductInv>();
		for (ProductInv productInv : pageOfProductInvs) {
			productInvs.add(productInv);
		}
		return productInvs;
	}
}