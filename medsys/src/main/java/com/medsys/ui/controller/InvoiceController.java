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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medsys.adminuser.model.Roles;
import com.medsys.common.model.ReportsResponse;
import com.medsys.customer.bd.CustomerBD;
import com.medsys.orders.bd.InvoiceBD;
import com.medsys.orders.model.Invoice;
import com.medsys.product.bd.SetBD;
import com.medsys.ui.jasper.service.InvoiceReportDownloadService;
import com.medsys.ui.jasper.util.TokenService;
import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.UIConstants;
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
		model.addAttribute("setList", setBD.getAllSet());
		invoice = new Invoice(true);
		model.addAttribute("invoice", invoice);
		return MedsysUITiles.ADD_INVOICE.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.ADD_INVOICE, method = RequestMethod.POST)
	public String addInvoice(@Valid @ModelAttribute Invoice invoice, Model model, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		logger.info("IN: Invoice/add-POST");

		if (result.hasErrors()) {
			logger.info("Invoice-add error: " + result.toString());
			redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.Invoice", result);
			redirectAttrs.addFlashAttribute("invoice", invoice);
			return MedsysUITiles.ADD_INVOICE.getTile();
		} else {
			logger.info("Invoice-add: " + invoice);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			invoice.setUpdateBy(auth.getName());
			invoice.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
			invoiceBD.addInvoice(invoice);
			String message = "Invoice " + invoice.getInvoiceId() + " was successfully added";

			logger.info("Invoice-add: " + message + "\n Invoice: " + invoice + " setting the same in request");
			// Unable to directly update the modelAttribute. Hence, setting
			// invoiceId separately in request
			request.setAttribute("updatedInvoiceId", invoice.getInvoiceId());
			return UIActions.REDIRECT + UIActions.LIST_ALL_INVOICES;
		}
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.EDIT_INVOICE, method = RequestMethod.GET)
	public String loadEditInvoicePage(@RequestParam(value = "invoiceId", required = false) Integer invoiceId,
			Model model) {
		logger.info("IN: Invoice/edit-GET:  invoice to query = " + invoiceId);
		if (!model.containsAttribute("invoice")) {

			logger.info("Adding Invoice object to model");
			Invoice invoice = invoiceBD.getInvoice(invoiceId);
			logger.info("Invoice/edit-GET:  " + invoice);
			model.addAttribute("invoice", invoice);
		}
		model.addAttribute("customerList", customerBD.getAllCustomers());
		model.addAttribute("setList", setBD.getAllSet());

		return MedsysUITiles.EDIT_INVOICE.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.SAVE_INVOICE, method = RequestMethod.POST)
	public String saveInvoice(@Valid @ModelAttribute Invoice invoice, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		logger.info("IN: Invoice/save-POST: " + invoice);

		if (result.hasErrors()) {
			logger.info("Invoice-edit error: " + result.toString());
			redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.Invoice", result);
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
			redirectAttrs.addFlashAttribute("message", message);
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
		model.addAttribute("message", message);
		return UIActions.REDIRECT + UIActions.LIST_ALL_INVOICES;
	}

	
	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.GET_INVOICE_REPORT)
	public void download(@RequestParam String type, @RequestParam String token, @RequestParam Integer invoiceId,
			HttpServletResponse response) {
		logger.debug("Requesting download of type: " + type + " with token: " + token);
		invoiceReportDownloadService.download(type, token, invoiceId, response);
	}
}