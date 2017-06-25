package com.medsys.ui.controller;

import java.sql.Timestamp;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medsys.adminuser.model.Roles;
import com.medsys.common.model.Response;
import com.medsys.customer.bd.CustomerBD;
import com.medsys.master.bd.MasterDataBD;
import com.medsys.master.model.InvoiceStatusCode;
import com.medsys.master.model.InvoiceStatusMaster;
import com.medsys.master.model.MasterData;
import com.medsys.master.model.OrderStatusCode;
import com.medsys.master.model.PaymentTermsMaster;
import com.medsys.master.model.TaxMaster;
import com.medsys.master.model.TaxType;
import com.medsys.orders.bd.InvoiceBD;
import com.medsys.orders.bd.OrderBD;
import com.medsys.orders.model.Invoice;
import com.medsys.orders.model.Orders;
import com.medsys.product.bd.ProductGroupBD;
import com.medsys.product.bd.SetBD;
import com.medsys.product.model.ProductGroup;
import com.medsys.product.model.Set;
import com.medsys.ui.jasper.service.InvoiceReportDownloadService;
import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.UIConstants;
import com.medsys.util.CalendarUtility;
import com.medsys.util.EpMessage;

@Controller
@Secured(Roles.MASTER_ADMIN)
public class InvoiceController extends SuperController {
	static Logger logger = LoggerFactory.getLogger(InvoiceController.class);

	@Autowired
	private InvoiceBD invoiceBD;

	@Autowired
	private CustomerBD customerBD;

	@Autowired
	private SetBD setBD;

	@Autowired
	private OrderBD orderBD;

	@Autowired
	private MasterDataBD masterDataBD;

	@Autowired
	private ProductGroupBD productGroupBD;

	@Autowired
	private InvoiceReportDownloadService invoiceReportDownloadService;

	@RequestMapping(value = { UIActions.FORWARD_SLASH + UIActions.LIST_ALL_INVOICES }, method = RequestMethod.GET)
	public String listOfInvoice(Model model) {
		logger.info("IN: Invoice/list-GET");

		List<Invoice> invoices = invoiceBD.getAllInvoice();
		model.addAttribute("invoices", invoices);

		// if there was an error in /add, we do not want to overwrite
		// the existing Invoice object containing the errors.
		if (!model.containsAttribute("invoice")) {
			logger.info("Adding Invoice object to model");
			Invoice invoice = new Invoice();
			model.addAttribute("invoice", invoice);
		}
		return MedsysUITiles.INVOICES_LIST.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_SEARCH_INVOICES, method = RequestMethod.GET)
	public String loadSearchInvoice(@ModelAttribute Invoice invoice, Model model) {

		logger.info("IN: Invoice/loadSearchInvoice-GET");
		if (invoice == null) {
			invoice = new Invoice();
		}
		model.addAttribute("invoice", invoice);
		return MedsysUITiles.SEARCH_INVOICES.getTile();
	}

	@RequestMapping(value = { UIActions.FORWARD_SLASH + UIActions.SEARCH_INVOICES }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public String searchInvoice(@ModelAttribute Invoice invoice, Model model) {
		logger.info("IN: Invoice/search");
		if (invoice.getInvoiceNo() == null && invoice.getCustomer().getName() == null
				&& invoice.getInvoiceDate() == null && invoice.getInvoiceStatus() == null) {
			logger.info("Fetching All invoices.");
			List<Invoice> invoices = invoiceBD.getAllInvoice();
			model.addAttribute("invoices", invoices);
		} else {
			logger.info("Fetching invoices as per search criteria.");
			List<Invoice> invoices = invoiceBD.searchForInvoice(invoice);
			if (invoices == null) {
				model.addAttribute(UIConstants.MSG_FOR_USER.getValue(),
						EpMessage.MSG_FOR_EMPTY_SEARCH_RESULT.getMsgCode());
			} else {
				model.addAttribute("invoices", invoices);
			}
		}
		model.addAttribute("invoice", invoice);
		return MedsysUITiles.SEARCH_INVOICES.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_ADD_INVOICE, method = RequestMethod.GET)
	public String loadAddInvoice(@ModelAttribute Invoice invoice, Model model) {

		logger.info("IN: Invoice/loadAdd-GET");
		model.addAttribute("customerList", customerBD.getAllCustomers());

		model.addAttribute("paymentTermsList", masterDataBD.getAll(PaymentTermsMaster.class));
		List<Orders> lstOrdersLastThreeMonths = getOrdersForLastThreeMonths();
		model.addAttribute("orderList", lstOrdersLastThreeMonths);
		invoice.setInvoiceStatus((InvoiceStatusMaster) masterDataBD.getbyCode(InvoiceStatusMaster.class,
				InvoiceStatusCode.ACTIVE.getCode()));
		model.addAttribute("invoice", invoice);
		return MedsysUITiles.ADD_INVOICE.getTile();
	}

	private List<Orders> getOrdersForLastThreeMonths() {
		Date startDate = CalendarUtility.getStartDateForLastThreeMonths();
		Date endDate = CalendarUtility.getEndDateAndTimeOfToday();
		return orderBD.searchForOrdersInDateRange(startDate, endDate);

	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.ADD_INVOICE, method = RequestMethod.POST)
	public String addInvoice(@Valid @ModelAttribute Invoice invoice, Model model, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		logger.info("IN: Invoice/add-POST");

		if (result.hasErrors()) {
			logger.error("Bindingresult Invoice-add error: " + result.hasErrors() + ".Error: " + result.getAllErrors()
					+ result.getErrorCount());
			model.addAttribute(UIConstants.MSG_FOR_USER_ERROR.getValue(), result);
			model.addAttribute("invoice", invoice);
			return UIActions.FORWARD + UIActions.LOAD_ADD_INVOICE;
		} else {
			logger.info("Invoice-add: " + invoice);
			try {
				Orders invOrder = orderBD.getOrder(invoice.getOrder().getOrderId());
				logger.info("Order: " + invOrder);
				if (!invOrder.getOrderStatus().getOrderStatusCode().equals(OrderStatusCode.SET_RESTORED.getCode())) {
					String error = "Order Set status is "
							+ OrderStatusCode.getOrderStatusCode(invOrder.getOrderStatus().getOrderStatusCode())
							+ ".Expected status is " + OrderStatusCode.SET_RESTORED + ".Unable to raise invoice.";
					logger.error("Invoice-add error in Order Status: " + error);
					model.addAttribute(UIConstants.MSG_FOR_USER_ERROR.getValue(), error);
					model.addAttribute("invoice", invoice);
					return UIActions.FORWARD + UIActions.LOAD_ADD_INVOICE;
				}
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				invoice.setUpdateBy(auth.getName());
				invoice.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
				invoice.setInvoiceStatus((InvoiceStatusMaster) masterDataBD.getbyCode(InvoiceStatusMaster.class,
						InvoiceStatusCode.ACTIVE.getCode()));
				Response response = invoiceBD.addInvoice(invoice);
				if (response.isStatus()) {
					String message = "Invoice " + invoice.getInvoiceId() + " was successfully added";

					logger.info("Invoice-add: " + message + "\n Invoice: " + invoice + " setting the same in request");
					// Unable to directly update the modelAttribute. Hence,
					// setting
					// invoiceId separately in request
					redirectAttrs.addFlashAttribute(UIConstants.MSG_FOR_USER.getValue(), message);
					redirectAttrs.addFlashAttribute("invoiceId", invoice.getInvoiceId());

					return UIActions.REDIRECT + UIActions.EDIT_INVOICE;
				} else {
					logger.info("Unable to add Invoice. Invoice-add error: " + response.getError().getErrorCode());
					model.addAttribute(UIConstants.MSG_FOR_USER_ERROR.getValue(), response.getError().getErrorCode());
					model.addAttribute("invoice", invoice);
					return UIActions.FORWARD + UIActions.LOAD_ADD_INVOICE;
				}
			} catch (Exception e) {
				logger.info("Invoice-add error: " + e.getMessage());
				model.addAttribute(UIConstants.MSG_FOR_USER_ERROR.getValue(), e.getMessage());
				model.addAttribute("invoice", invoice);
				return UIActions.FORWARD + UIActions.LOAD_ADD_INVOICE;
			}

		}
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.EDIT_INVOICE, method = RequestMethod.GET)
	public String loadEditInvoicePage(@RequestParam(value = "invoiceId", required = false) Integer invoiceId,
			Model model) {
		logger.info("IN: Invoice/edit-GET:  invoice to query = " + invoiceId);

		if (invoiceId == null) {
			logger.info("Checking in model for invoiceId = " + model.asMap().get("invoiceId"));
			invoiceId = (Integer) model.asMap().get("invoiceId");
		}

		Invoice invoice = null;

		if (!model.containsAttribute("invoice")) {

			logger.info("Adding Invoice object to model");
			invoice = invoiceBD.getInvoice(invoiceId);
			logger.info("Invoice/edit-GET:  " + invoice);
			model.addAttribute("invoice", invoice);
		} else {
			invoice = (Invoice) model.asMap().get("invoice");
		}
		model.addAttribute("customerList", customerBD.getAllCustomers());

		model.addAttribute("paymentTermsList", masterDataBD.getAll(PaymentTermsMaster.class));
		List<Orders> lstOrdersLastThreeMonths = getOrdersForLastThreeMonths();
		model.addAttribute("orderList", lstOrdersLastThreeMonths);

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
		List<ProductGroup> productGroupMasterList = productGroupBD.getAllProductGroup();
		String pdtGroupList = "{";
		for (ProductGroup productGroup : productGroupMasterList) {
			pdtGroupList += "'" + productGroup.getGroupId() + "':'" + productGroup.getGroupName() + "',";
		}
		pdtGroupList = pdtGroupList.substring(0, pdtGroupList.length() - 1);
		pdtGroupList += "}";
		// Figure out how to cache all these values
		model.addAttribute("pdtGroupList", pdtGroupList);

		/** VAT Listing for ADD Product Filter **/
		List<MasterData> taxMasterList = masterDataBD.getAll(TaxMaster.class);
		String vatTypeList = "{";
		String cgstTypeList = "{";
		String sgstTypeList = "{";
		for (MasterData txMd : taxMasterList) {
			TaxMaster taxMaster = (TaxMaster) txMd;
			switch (TaxType.getTaxTypeByCode(taxMaster.getTaxType())) {
			case VAT:
				vatTypeList += "'" + taxMaster.getTaxId() + "':'" + taxMaster.getTaxDesc() + "',";
				break;
			case CGST:
				cgstTypeList += "'" + taxMaster.getTaxId() + "':'" + taxMaster.getTaxDesc() + "',";
				break;
			case SGST:
				sgstTypeList += "'" + taxMaster.getTaxId() + "':'" + taxMaster.getTaxDesc() + "',";
				break;
			}

		}
		vatTypeList = vatTypeList.substring(0, vatTypeList.length() - 1);
		cgstTypeList = cgstTypeList.substring(0, cgstTypeList.length() - 1);
		sgstTypeList = sgstTypeList.substring(0, sgstTypeList.length() - 1);
		vatTypeList += "}";
		cgstTypeList += "}";
		sgstTypeList += "}";
		// TODO: Figure out how to cache all these values
		model.addAttribute("vatTypeList", vatTypeList);
		model.addAttribute("cgstTypeList", cgstTypeList);
		model.addAttribute("sgstTypeList", sgstTypeList);

		/**
		 * END Of Converting the MasterData into the format of
		 * "Code:DisplayValue" as required by the JQGrid Select Options
		 */
		if (invoice.isGstInvoice()) {
			return MedsysUITiles.EDIT_GST_INVOICE.getTile();
		} else {
			return MedsysUITiles.EDIT_INVOICE.getTile();
		}
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.SAVE_INVOICE, method = RequestMethod.POST)
	public String saveInvoice(@Valid @ModelAttribute Invoice invoice, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		logger.info("IN: Invoice/save-POST: " + invoice);

		if (result.hasErrors()) {
			logger.info("Invoice-edit error: " + result.toString());
			redirectAttrs.addFlashAttribute(UIConstants.MSG_FOR_USER_ERROR.getValue(), result);
			redirectAttrs.addFlashAttribute("invoice", invoice);
			request.setAttribute("invoiceId", invoice.getInvoiceId());
			return UIActions.REDIRECT + UIActions.EDIT_INVOICE;
		} else {
			logger.info("Invoice/edit-POST:  " + invoice.toString());
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			invoice.setUpdateBy(auth.getName());
			invoice.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
			invoiceBD.updateInvoice(invoice);
			String message = "Invoice " + invoice.getInvoiceId() + " was successfully edited";
			redirectAttrs.addFlashAttribute(UIConstants.MSG_FOR_USER.getValue(), message);
		}

		return UIActions.REDIRECT + UIActions.LIST_ALL_INVOICES;
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_DELETE_INVOICE, method = RequestMethod.GET)
	public String loadDeleteInvoicePage(@RequestParam(value = "invoiceId", required = true) Integer invoiceId,
			Model model) {

		logger.info("IN: Invoice/delete-GET:  username to query = " + invoiceId);
		Invoice invoice = invoiceBD.getInvoice(invoiceId);
		logger.info("Invoice/delete-GET:  " + invoice.toString());
		model.addAttribute("invoice", invoice);

		return MedsysUITiles.CONFIRM_DELETE_INVOICE.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.DELETE_INVOICE, method = RequestMethod.POST)
	public String deleteInvoicePage(Integer invoiceId, Model model) {

		logger.info("IN: Invoice/delete-POST | invoiceId = " + invoiceId);

		invoiceBD.deleteInvoice(invoiceId);
		String message = "Invoice with invoiceId: " + invoiceId + " was successfully deleted";
		model.addAttribute(UIConstants.MSG_FOR_USER.getValue(), message);
		return UIActions.REDIRECT + UIActions.LIST_ALL_INVOICES;
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.GET_INVOICE_REPORT)
	public void download(@RequestParam String type, @RequestParam String token, @RequestParam Integer invoiceId,
			@RequestParam(required = false) String billVersion, HttpServletResponse response) {
		logger.debug("Requesting download of type: " + type + " with token: " + token);
		invoiceReportDownloadService.download(type, token, invoiceId, billVersion, response);
	}
}