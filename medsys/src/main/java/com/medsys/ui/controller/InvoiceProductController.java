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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medsys.adminuser.model.Roles;
import com.medsys.common.model.Response;
import com.medsys.master.bd.MasterDataBD;
import com.medsys.master.model.TaxMaster;
import com.medsys.orders.bd.InvoiceBD;
import com.medsys.orders.model.InvoiceProduct;
import com.medsys.product.bd.ProductMasterBD;
import com.medsys.product.model.ProductMaster;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.jqgrid.JqgridResponse;
import com.medsys.util.EpSystemError;

@Controller
@Secured(Roles.MASTER_ADMIN)
public class InvoiceProductController {

	static Logger logger = LoggerFactory.getLogger(InvoiceProductController.class);

	@Autowired
	private InvoiceBD invoiceBD;

	@Autowired
	private MasterDataBD masterDataBD;

	@Autowired
	private ProductMasterBD productMasterBD;

	@RequestMapping(value = UIActions.FORWARD_SLASH
			+ UIActions.LIST_ALL_PRODUCT_INVOICES, produces = "application/json")
	public @ResponseBody JqgridResponse<?> records(@RequestParam("_search") Boolean search,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "invoiceProductSetId", required = false) Integer invoiceProductSetId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord,
			@RequestParam(value = "setId", required = false) Integer setId,
			@RequestParam(value = "invoiceId", required = false) Integer invoiceId) {

		// Pageable pageRequest = new PageRequest(page-1, rows);
		logger.debug("list all products / search in invoice : setId: " + setId + " ,invoiceID: " + invoiceId);
		if (search == true) {
			// TODO: enable search??
			// return getFilteredRecords(filters, pageRequest);

		}

		List<InvoiceProduct> invoiceProducts = invoiceBD.getAllProductsInInvoice(invoiceId);

		logger.debug("Products in invoice: " + invoiceProducts);

		JqgridResponse<InvoiceProduct> response = new JqgridResponse<InvoiceProduct>();
		response.setRows(invoiceProducts);

		response.setRecords(Integer.valueOf(invoiceProducts.size()).toString());
		// Single page to display all products part of the set chosen for
		// invoice.
		response.setTotal(Integer.valueOf(1).toString());
		response.setPage(Integer.valueOf(1).toString());

		logger.debug("Products already exist in invoice. Loading response from Invoice Product List: " + response);

		return response;

	}

	@RequestMapping(value = UIActions.GET_PRODUCT_INVOICE, produces = "application/json")
	public @ResponseBody InvoiceProduct get(@RequestBody InvoiceProduct invoiceProductSet) {
		logger.debug("Getting the product in invoice: " + invoiceProductSet);

		return invoiceBD.getProductInInvoice(invoiceProductSet.getInvoiceProductId());
	}

	@RequestMapping(value = UIActions.ADD_PRODUCT_INVOICE, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response create(@RequestParam(value = "invoiceId", required = true) Integer invoiceId,
			@RequestParam(value = "product.productId", required = true) Integer productId,
			@RequestParam(value = "productDesc", required = true) String productDesc,
			@RequestParam(value = "qty", required = false) Integer qty,
			@RequestParam(value = "ratePerUnit", required = false) BigDecimal ratePerUnit,
			@RequestParam(value = "vatType.taxId", required = false) Integer vatTypeId,
			@RequestParam(value = "cgstType.taxId", required = false) Integer cgstTypeId,
			@RequestParam(value = "sgstType.taxId", required = false) Integer sgstTypeId,
			@RequestParam(value = "discount", required = false) BigDecimal discount,
			HttpServletResponse httpServletResponse) {

		logger.debug("Call to add product to invoice.");

		ProductMaster product = productMasterBD.getProduct(productId);
		InvoiceProduct newInvoiceProduct = new InvoiceProduct();
		newInvoiceProduct.setInvoiceId(invoiceId);
		newInvoiceProduct.setProduct(product);
		newInvoiceProduct.setProductDesc(product.getProductDesc() + " " + productDesc);
		newInvoiceProduct.setQty(qty);
		newInvoiceProduct.setRatePerUnit(ratePerUnit);

		TaxMaster appliedCgstType = (TaxMaster) masterDataBD.get(TaxMaster.class, cgstTypeId);
		newInvoiceProduct.setCgstType(appliedCgstType);
		TaxMaster appliedSgstType = (TaxMaster) masterDataBD.get(TaxMaster.class, sgstTypeId);
		newInvoiceProduct.setSgstType(appliedSgstType);

		newInvoiceProduct.setDiscount(discount);

		logger.debug("Adding the product to invoice: " + newInvoiceProduct);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		newInvoiceProduct.setUpdateBy(auth.getName());
		newInvoiceProduct.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
		Response response = invoiceBD.addProductToInvoice(newInvoiceProduct);
		if (!response.isStatus()) {
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return response;
	}

	@RequestMapping(value = UIActions.EDIT_PRODUCT_INVOICE, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response update(@RequestParam Integer id,
			@RequestParam(value = "invoiceId", required = false) Integer invoiceId,
			
			@RequestParam(value = "productDesc", required = true) String productDesc,
			@RequestParam(value = "qty", required = false) Integer qty,
			@RequestParam(value = "ratePerUnit", required = false) BigDecimal ratePerUnit,
			@RequestParam(value = "vatType.taxId", required = false) Integer vatTypeId,
			@RequestParam(value = "cgstType.taxId", required = false) Integer cgstTypeId,
			@RequestParam(value = "sgstType.taxId", required = false) Integer sgstTypeId,
			@RequestParam(value = "discount", required = false) BigDecimal discount,
			HttpServletResponse httpServletResponse) {

		logger.debug("invoiceId in request: " + invoiceId);
		logger.debug("invoiceProductId in request: " + id);
		InvoiceProduct invoiceProduct = invoiceBD.getProductInInvoice(id);
		logger.debug("InvoiceProduct from DB: " + invoiceProduct);

		if (invoiceProduct.getInvoiceId().equals(invoiceId)) {
			InvoiceProduct toBeUpdatedInvoiceProduct = new InvoiceProduct();
			toBeUpdatedInvoiceProduct.setInvoiceProductId(id);
			toBeUpdatedInvoiceProduct.setInvoiceId(invoiceId);
			toBeUpdatedInvoiceProduct.setProduct(invoiceProduct.getProduct());
			toBeUpdatedInvoiceProduct.setProductDesc(productDesc);
			toBeUpdatedInvoiceProduct.setQty(qty);
			toBeUpdatedInvoiceProduct.setRatePerUnit(ratePerUnit);

			TaxMaster appliedCgstType = (TaxMaster) masterDataBD.get(TaxMaster.class, cgstTypeId);
			toBeUpdatedInvoiceProduct.setCgstType(appliedCgstType);
			TaxMaster appliedSgstType = (TaxMaster) masterDataBD.get(TaxMaster.class, sgstTypeId);
			toBeUpdatedInvoiceProduct.setSgstType(appliedSgstType);
			toBeUpdatedInvoiceProduct.setDiscount(discount);
			logger.debug("Updating the product to invoice: " + toBeUpdatedInvoiceProduct);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			toBeUpdatedInvoiceProduct.setUpdateBy(auth.getName());
			toBeUpdatedInvoiceProduct.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
			Response response = invoiceBD.updateProductInInvoice(toBeUpdatedInvoiceProduct);
			logger.debug("Checking the update status: " + response);
			if (!response.isStatus()) {
				httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			return response;
		} else {
			logger.debug("Error in updating the product in invoice: " + invoiceProduct
					+ ".\nThe invoiceId and productCodes in request do not match with System data");
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return new Response(false, EpSystemError.SYSTEM_INTERNAL_ERROR);
		}
	}

	@RequestMapping(value = UIActions.DELETE_PRODUCT_INVOICE, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response delete(@RequestParam Integer id, HttpServletResponse httpServletResponse) {

		InvoiceProduct invoiceProductSet = invoiceBD.getProductInInvoice(id);
		logger.debug("Deleting the product in invoice: " + invoiceProductSet);
		Response response = invoiceBD.deleteProductFromInvoice(invoiceProductSet);
		if (!response.isStatus()) {
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return response;
	}

	public static List<InvoiceProduct> map(Page<InvoiceProduct> pageOfInvoiceProducts) {
		List<InvoiceProduct> invoiceProducts = new ArrayList<InvoiceProduct>();
		for (InvoiceProduct invoiceProduct : pageOfInvoiceProducts) {
			invoiceProducts.add(invoiceProduct);
		}
		return invoiceProducts;
	}
}