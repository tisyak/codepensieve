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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medsys.adminuser.model.Roles;
import com.medsys.common.model.Response;
import com.medsys.master.bd.MasterDataBD;
import com.medsys.master.model.PurchaseOrderStatusCode;
import com.medsys.master.model.PurchaseOrderStatusMaster;
import com.medsys.orders.bd.PurchaseOrderBD;
import com.medsys.orders.model.PurchaseOrder;
import com.medsys.product.bd.SetBD;
import com.medsys.product.model.ProductGroup;
import com.medsys.product.model.ProductMaster;
import com.medsys.product.model.Set;
import com.medsys.supplier.bd.SupplierBD;
import com.medsys.ui.excel.service.POExcelDownloadService;
import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.UIConstants;
import com.medsys.util.EpMessage;

@Controller
@Secured(Roles.MASTER_ADMIN)
public class PurchaseOrderController extends SuperController {
	static Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);

	@Autowired
	private PurchaseOrderBD purchaseOrderBD;

	@Autowired
	private SupplierBD supplierBD;

	@Autowired
	private SetBD setBD;

	@Autowired
	private MasterDataBD masterDataBD;

	@Autowired
	private POExcelDownloadService poExcelDownloadService;

	@RequestMapping(value = { UIActions.FORWARD_SLASH + UIActions.LIST_ALL_PURCHASE_ORDER }, method = RequestMethod.GET)
	public String listOfPurchaseOrder(Model model) {
		logger.info("IN: PurchaseOrder/list-GET");

		List<PurchaseOrder> purchaseOrders = purchaseOrderBD.getAllPurchaseOrder();
		model.addAttribute("purchaseOrders", purchaseOrders);

		// if there was an error in /add, we do not want to overwrite
		// the existing PurchaseOrder object containing the errors.
		if (!model.containsAttribute("purchaseOrder")) {
			logger.info("Adding PurchaseOrder object to model");
			PurchaseOrder purchaseOrder = new PurchaseOrder();
			model.addAttribute("purchaseOrder", purchaseOrder);
		}
		return MedsysUITiles.PURCHASE_ORDER_LIST.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_SEARCH_PURCHASE_ORDER, method = RequestMethod.GET)
	public String loadSearchPurchaseOrder(@ModelAttribute PurchaseOrder purchaseOrder, Model model) {

		logger.info("IN: PurchaseOrder/loadSearchPurchaseOrder-GET");
		purchaseOrder = new PurchaseOrder();
		model.addAttribute("purchaseOrder", purchaseOrder);
		return MedsysUITiles.SEARCH_PURCHASE_ORDER.getTile();
	}

	@RequestMapping(value = { UIActions.FORWARD_SLASH + UIActions.SEARCH_PURCHASE_ORDER }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public String searchPurchaseOrder(@ModelAttribute PurchaseOrder purchaseOrder, Model model) {
		logger.info("IN: PurchaseOrder/search");
		if (purchaseOrder.getPurchaseOrderNumber() == null && purchaseOrder.getSupplier().getName() == null
				&& purchaseOrder.getPurchaseOrderDate() == null && purchaseOrder.getReceiveDate() == null
				&& purchaseOrder.getPurchaseOrderStatus() == null) {
			logger.info("Fetching All purchaseOrders.");
			List<PurchaseOrder> purchaseOrders = purchaseOrderBD.getAllPurchaseOrder();
			model.addAttribute("purchaseOrders", purchaseOrders);
		} else {
			logger.info("Fetching purchaseOrders as per search criteria.");
			List<PurchaseOrder> purchaseOrders = purchaseOrderBD.searchForPurchaseOrder(purchaseOrder);
			if (purchaseOrders == null) {
				model.addAttribute(UIConstants.MSG_FOR_USER.getValue(),
						EpMessage.MSG_FOR_EMPTY_SEARCH_RESULT.getMsgCode());
			} else {
				model.addAttribute("purchaseOrders", purchaseOrders);
			}
		}
		model.addAttribute("purchaseOrder", purchaseOrder);
		return MedsysUITiles.SEARCH_PURCHASE_ORDER.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_ADD_PURCHASE_ORDER, method = { RequestMethod.GET,
			RequestMethod.POST })
	public String loadAddPurchaseOrder(@ModelAttribute PurchaseOrder purchaseOrder, Model model) {

		logger.info("IN: PurchaseOrder/loadAdd-GET");
		model.addAttribute("supplierList", supplierBD.getAllSuppliers());
		purchaseOrder.setPurchaseOrderStatus((PurchaseOrderStatusMaster) masterDataBD
				.getbyCode(PurchaseOrderStatusMaster.class, PurchaseOrderStatusCode.ACTIVE.getCode()));
		model.addAttribute("purchaseOrder", purchaseOrder);
		return MedsysUITiles.ADD_PURCHASE_ORDER.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.ADD_PURCHASE_ORDER, method = RequestMethod.POST)
	public String addPurchaseOrder(@Valid @ModelAttribute PurchaseOrder purchaseOrder, Model model,
			BindingResult result, RedirectAttributes redirectAttrs, HttpServletRequest request) {

		logger.info("IN: PurchaseOrder/add-POST");

		if (result.hasErrors()) {
			logger.error("Bindingresult PurchaseOrder-add error: " + result.hasErrors() + ".Error: "
					+ result.getAllErrors() + result.getErrorCount());
			model.addAttribute(UIConstants.MSG_FOR_USER_ERROR.getValue(), result);
			model.addAttribute("purchaseOrder", purchaseOrder);
			return UIActions.FORWARD + UIActions.LOAD_ADD_PURCHASE_ORDER;
		} else {
			logger.info("PurchaseOrder-add: " + purchaseOrder);
			try {

				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				purchaseOrder.setUpdateBy(auth.getName());
				purchaseOrder.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
				purchaseOrder.setPurchaseOrderStatus((PurchaseOrderStatusMaster) masterDataBD
						.getbyCode(PurchaseOrderStatusMaster.class, PurchaseOrderStatusCode.ACTIVE.getCode()));
				Response response = purchaseOrderBD.addPurchaseOrder(purchaseOrder);
				if (response.isStatus()) {
					String message = "PurchaseOrder " + purchaseOrder.getPurchaseOrderId() + " was successfully added";

					logger.info("PurchaseOrder-add: " + message + "\n PurchaseOrder: " + purchaseOrder
							+ " setting the same in request");
					// Unable to directly update the modelAttribute. Hence,
					// setting
					// purchaseOrderId separately in request
					redirectAttrs.addFlashAttribute(UIConstants.MSG_FOR_USER.getValue(), message);
					redirectAttrs.addFlashAttribute("purchaseOrderId", purchaseOrder.getPurchaseOrderId());

					return UIActions.REDIRECT + UIActions.EDIT_PURCHASE_ORDER;
				} else {
					logger.info("Unable to add PurchaseOrder. PurchaseOrder-add error: "
							+ response.getError().getErrorCode());
					model.addAttribute(UIConstants.MSG_FOR_USER_ERROR.getValue(), response.getError().getErrorCode());
					model.addAttribute("purchaseOrder", purchaseOrder);
					return UIActions.FORWARD + UIActions.LOAD_ADD_PURCHASE_ORDER;
				}
			} catch (Exception e) {
				logger.info("PurchaseOrder-add error: " + e.getMessage());
				model.addAttribute(UIConstants.MSG_FOR_USER_ERROR.getValue(), e.getMessage());
				model.addAttribute("purchaseOrder", purchaseOrder);
				return UIActions.FORWARD + UIActions.LOAD_ADD_PURCHASE_ORDER;
			}

		}
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.EDIT_PURCHASE_ORDER, method = RequestMethod.GET)
	@Transactional
	public String loadEditPurchaseOrderPage(
			@RequestParam(value = "purchaseOrderId", required = false) Integer purchaseOrderId, Model model) {
		logger.info("IN: PurchaseOrder/edit-GET:  purchaseOrder to query = " + purchaseOrderId);

		if (purchaseOrderId == null) {
			logger.info("Checking in model for purchaseOrderId = " + model.asMap().get("purchaseOrderId"));
			purchaseOrderId = (Integer) model.asMap().get("purchaseOrderId");
		}

		PurchaseOrder purchaseOrder = null;

		if (!model.containsAttribute("purchaseOrder")) {

			logger.info("Adding PurchaseOrder object to model");
			purchaseOrder = purchaseOrderBD.getPurchaseOrder(purchaseOrderId);
			logger.info("PurchaseOrder/edit-GET:  " + purchaseOrder);
			model.addAttribute("purchaseOrder", purchaseOrder);
		} else {
			purchaseOrder = (PurchaseOrder) model.asMap().get("purchaseOrder");
		}
		model.addAttribute("supplierList", supplierBD.getAllSuppliers());

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
						+ product.getProductDesc() + "',";
			}
			pdtList = pdtList.substring(0, pdtList.length() - 1);
			pdtList += "}";
			// Figure out how to cache all these values
			model.addAttribute("pdtList", pdtList);
		} else {
			logger.error("NO SETs found hence, products search aborted!");
		}

		return MedsysUITiles.EDIT_PURCHASE_ORDER.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.SAVE_PURCHASE_ORDER, method = RequestMethod.POST)
	public String savePurchaseOrder(@Valid @ModelAttribute PurchaseOrder purchaseOrder, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		logger.info("IN: PurchaseOrder/save-POST: " + purchaseOrder);

		if (result.hasErrors()) {
			logger.info("PurchaseOrder-edit error: " + result.toString());
			redirectAttrs.addFlashAttribute(UIConstants.MSG_FOR_USER_ERROR.getValue(), result);
			redirectAttrs.addFlashAttribute("purchaseOrder", purchaseOrder);
			request.setAttribute("purchaseOrderId", purchaseOrder.getPurchaseOrderId());
			return UIActions.REDIRECT + UIActions.EDIT_PURCHASE_ORDER;
		} else {
			logger.info("PurchaseOrder/edit-POST:  " + purchaseOrder.toString());
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			purchaseOrder.setUpdateBy(auth.getName());
			purchaseOrder.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
			purchaseOrderBD.updatePurchaseOrder(purchaseOrder);
			String message = "PurchaseOrder " + purchaseOrder.getPurchaseOrderId() + " was successfully edited";
			redirectAttrs.addFlashAttribute(UIConstants.MSG_FOR_USER.getValue(), message);
		}

		return UIActions.REDIRECT + UIActions.LIST_ALL_PURCHASE_ORDER;
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.GET_PURCHASE_ORDER_EXCEL)
	public void download(@RequestParam String token, @RequestParam Integer purchaseOrderId,
			HttpServletResponse response) {
		logger.debug("Requesting download of excel with token: " + token + " for PO id: " + purchaseOrderId);
		poExcelDownloadService.download(token, purchaseOrderId, response);

	}

}