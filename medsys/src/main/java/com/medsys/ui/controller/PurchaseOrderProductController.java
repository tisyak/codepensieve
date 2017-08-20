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
import com.medsys.orders.bd.PurchaseOrderBD;
import com.medsys.orders.model.PurchaseOrderProductSet;
import com.medsys.product.bd.ProductMasterBD;
import com.medsys.product.model.ProductMaster;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.jqgrid.JqgridResponse;
import com.medsys.util.EpSystemError;

@Controller
@Secured(Roles.MASTER_ADMIN)
public class PurchaseOrderProductController {

	static Logger logger = LoggerFactory.getLogger(PurchaseOrderProductController.class);

	@Autowired
	private PurchaseOrderBD purchaseOrderBD;

	@Autowired
	private ProductMasterBD productMasterBD;

	@RequestMapping(value = UIActions.FORWARD_SLASH
			+ UIActions.LIST_ALL_PRODUCT_PURCHASE_ORDER, produces = "application/json")
	public @ResponseBody JqgridResponse<?> records(@RequestParam("_search") Boolean search,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "purchaseOrderProductSetId", required = false) Integer purchaseOrderProductSetId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord,
			@RequestParam(value = "setId", required = false) Integer setId,
			@RequestParam(value = "purchaseOrderId", required = false) Integer purchaseOrderId) {

		// Pageable pageRequest = new PageRequest(page-1, rows);
		logger.debug("list all products / search in purchaseOrder : setId: " + setId + " ,purchaseOrderID: " + purchaseOrderId);
		if (search == true) {
			// TODO: enable search??
			// return getFilteredRecords(filters, pageRequest);

		}

		List<PurchaseOrderProductSet> purchaseOrderProducts = purchaseOrderBD.getAllProductsInPurchaseOrder(purchaseOrderId);

		logger.debug("Products in purchaseOrder: " + purchaseOrderProducts);

		JqgridResponse<PurchaseOrderProductSet> response = new JqgridResponse<PurchaseOrderProductSet>();
		response.setRows(purchaseOrderProducts);

		response.setRecords(Integer.valueOf(purchaseOrderProducts.size()).toString());
		// Single page to display all products part of the set chosen for
		// purchaseOrder.
		response.setTotal(Integer.valueOf(1).toString());
		response.setPage(Integer.valueOf(1).toString());

		logger.debug("Products already exist in purchaseOrder. Loading response from PurchaseOrder Product List: " + response);

		return response;

	}

	@RequestMapping(value = UIActions.GET_PRODUCT_PURCHASE_ORDER, produces = "application/json")
	public @ResponseBody PurchaseOrderProductSet get(@RequestBody PurchaseOrderProductSet purchaseOrderProductSet) {
		logger.debug("Getting the product in purchaseOrder: " + purchaseOrderProductSet);

		return purchaseOrderBD.getProductInPurchaseOrder(purchaseOrderProductSet.getProductSetId());
	}

	@RequestMapping(value = UIActions.ADD_PRODUCT_PURCHASE_ORDER, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response create(@RequestParam(value = "purchaseOrderId", required = false) Integer purchaseOrderId,
			@RequestParam(value = "product.productId", required = false) Integer productId,
			@RequestParam(value = "qty", required = false) Integer qty,
			HttpServletResponse httpServletResponse) {

		logger.debug("Call to add product to purchaseOrder.");

		ProductMaster product = productMasterBD.getProduct(productId);
		PurchaseOrderProductSet newPurchaseOrderProductSet = new PurchaseOrderProductSet();
		newPurchaseOrderProductSet.setPurchaseOrderId(purchaseOrderId);
		newPurchaseOrderProductSet.setProduct(product);
		newPurchaseOrderProductSet.setQty(qty);
		
		logger.debug("Adding the product to purchaseOrder: " + newPurchaseOrderProductSet);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		newPurchaseOrderProductSet.setUpdateBy(auth.getName());
		newPurchaseOrderProductSet.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
		Response response = purchaseOrderBD.addProductToPurchaseOrder(newPurchaseOrderProductSet);
		if (!response.isStatus()) {
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return response;
	}

	@RequestMapping(value = UIActions.EDIT_PRODUCT_PURCHASE_ORDER, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response update(@RequestParam Integer id,
			@RequestParam(value = "purchaseOrderId", required = false) Integer purchaseOrderId,
			@RequestParam(value = "product.productId", required = false) Integer productId,
			@RequestParam(value = "qty", required = false) Integer qty,
			HttpServletResponse httpServletResponse) {

		logger.debug("purchaseOrderId in request: " + purchaseOrderId);
		logger.debug("productId in request: " + productId);
		PurchaseOrderProductSet purchaseOrderProduct = purchaseOrderBD.getProductInPurchaseOrder(id);
		logger.debug("PurchaseOrderProductSet from DB: " + purchaseOrderProduct);
		
		
		
		if (purchaseOrderProduct.getPurchaseOrderId().equals(purchaseOrderId)
				&& purchaseOrderProduct.getProduct().getProductId().equals(productId)) {
			PurchaseOrderProductSet toBeUpdatedPurchaseOrderProductSet = new PurchaseOrderProductSet();
			toBeUpdatedPurchaseOrderProductSet.setProductSetId(id);
			toBeUpdatedPurchaseOrderProductSet.setPurchaseOrderId(purchaseOrderId);
			toBeUpdatedPurchaseOrderProductSet.setProduct(purchaseOrderProduct.getProduct());
			toBeUpdatedPurchaseOrderProductSet.setQty(qty);
			logger.debug("Updating the product to purchaseOrder: " + toBeUpdatedPurchaseOrderProductSet);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			toBeUpdatedPurchaseOrderProductSet.setUpdateBy(auth.getName());
			toBeUpdatedPurchaseOrderProductSet.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
			Response response = purchaseOrderBD.updateProductInPurchaseOrder(toBeUpdatedPurchaseOrderProductSet);
			logger.debug("Checking the update status: " + response);
			if (!response.isStatus()) {
				httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			return response;
		} else {
			logger.debug("Error in updating the product in purchaseOrder: " + purchaseOrderProduct
					+ ".\nThe purchaseOrderId and productCodes in request do not match with System data");
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return new Response(false, EpSystemError.SYSTEM_INTERNAL_ERROR);
		}
	}

	@RequestMapping(value = UIActions.DELETE_PRODUCT_PURCHASE_ORDER, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response delete(@RequestParam Integer purchaseOrderProductSetId,
			HttpServletResponse httpServletResponse) {

		PurchaseOrderProductSet purchaseOrderProductSet = purchaseOrderBD.getProductInPurchaseOrder(purchaseOrderProductSetId);
		logger.debug("Deleting the product in purchaseOrder: " + purchaseOrderProductSet);
		Response response = purchaseOrderBD.deleteProductFromPurchaseOrder(purchaseOrderProductSet);
		if (!response.isStatus()) {
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return response;
	}

	public static List<PurchaseOrderProductSet> map(Page<PurchaseOrderProductSet> pageOfPurchaseOrderProductSets) {
		List<PurchaseOrderProductSet> purchaseOrderProducts = new ArrayList<PurchaseOrderProductSet>();
		for (PurchaseOrderProductSet purchaseOrderProduct : pageOfPurchaseOrderProductSets) {
			purchaseOrderProducts.add(purchaseOrderProduct);
		}
		return purchaseOrderProducts;
	}
}