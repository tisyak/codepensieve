package com.medsys.orders.bd;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.common.model.Response;
import com.medsys.exception.SysException;
import com.medsys.master.bd.MasterDataBD;
import com.medsys.master.model.OrderStatusCode;
import com.medsys.master.model.OrderStatusMaster;
import com.medsys.orders.dao.InvoiceDAO;
import com.medsys.orders.model.Invoice;
import com.medsys.orders.model.InvoiceProduct;
import com.medsys.orders.model.Orders;
import com.medsys.product.bd.ProductInvBD;
import com.medsys.product.model.ProductInv;
import com.medsys.util.EpSystemError;

@Service
@Transactional
public class InvoiceBDImpl implements InvoiceBD {
	static Logger logger = LoggerFactory.getLogger(InvoiceBDImpl.class);

	@Autowired
	private InvoiceDAO invoiceDAO;
	
	@Autowired
	private OrderBD orderBD;
	
	@Autowired
	private MasterDataBD masterDataBD;

	@Autowired
	private ProductInvBD productInvBD;

	@Override
	public Response addInvoice(Invoice invoice) {
		logger.debug("InvoiceBD: Adding invoice.");
		Response response = invoiceDAO.addInvoice(invoice);
		if(response.isStatus()){
			Orders order = invoice.getOrder();
			order.setOrderStatus((OrderStatusMaster) masterDataBD.getbyCode(OrderStatusMaster.class, OrderStatusCode.INVOICE_GENERATED.getCode()));
			response = orderBD.updateOrderStatus(order);
		}
		return response;

	}

	@Override
	public Invoice getInvoice(Integer invoiceId) {
		return invoiceDAO.getInvoice(invoiceId);
	}

	@Override
	public Invoice updateInvoice(Invoice invoice) {
		logger.debug("InvoiceBD: Updating invoice.");
		return invoiceDAO.updateInvoice(invoice);
	}

	@Override
	public Response deleteInvoice(Integer invoiceId) {
		return invoiceDAO.deleteInvoice(invoiceId);
	}

	@Override
	public List<Invoice> getAllInvoice() {
		return invoiceDAO.getAllInvoice();
	}

	@Override
	public List<Invoice> searchForInvoice(Invoice invoice) {
		return invoiceDAO.searchForInvoice(invoice);
	}

	@Override
	public List<Invoice> searchForInvoice(Date fromDate, Date toDate) {
		return invoiceDAO.searchForInvoice(fromDate, toDate);
	}

	@Override
	public List<InvoiceProduct> getAllProductsInInvoice(Integer invoiceId) {
		logger.info("Get All products in Invoice: " + invoiceId);
		List<InvoiceProduct> invoiceProducts = invoiceDAO.getAllProductsInInvoice(invoiceId);
		for (InvoiceProduct product : invoiceProducts) {
			try {
				ProductInv productInv = productInvBD.getProductByCode(product.getProduct().getProductCode());
				product.setAvailableQty(productInv.getAvailableQty());
			} catch (EmptyResultDataAccessException e) {
				logger.debug("Product " + product.getProduct().getProductCode() + " not found in Inventory");
				product.setAvailableQty(0);
			}

		}

		return invoiceProducts;

	}

	@Override
	@Transactional
	public Response addProductToInvoice(InvoiceProduct newInvoiceProduct) {
		logger.info("ADD product to Invoice: " + newInvoiceProduct);
		// Managing product inventory before adding product to the invoice
		try {

			newInvoiceProduct = this.calculateEffectiveTaxAndPrice(newInvoiceProduct);

			productInvBD.sellProduct(newInvoiceProduct.getProduct().getProductCode(), newInvoiceProduct.getQty());

			Response response = invoiceDAO.addProductToInvoice(newInvoiceProduct);

			if (response.isStatus()) {
				updateEffectiveTotalsInInvoice(newInvoiceProduct.getInvoiceId(), newInvoiceProduct.getUpdateBy(),
						newInvoiceProduct.getUpdateTimestamp());
			}

			return response;
		} catch (SysException e) {
			return new Response(false, e.getErrorCode());
		}
	}

	@Override
	public InvoiceProduct getProductInInvoice(Integer invoiceProductId) {
		logger.debug("GET product for invoiceProductId: " + invoiceProductId);
		return invoiceDAO.getProductInInvoice(invoiceProductId);
	}

	@Override
	@Transactional
	public Response updateProductInInvoice(InvoiceProduct invoiceProduct) {

		logger.info("UPDATE invoiceProduct: " + invoiceProduct);
		InvoiceProduct orgInvoiceProduct = getProductInInvoice(invoiceProduct.getInvoiceProductId());

		if (!orgInvoiceProduct.getProduct().getProductId().equals(invoiceProduct.getProduct().getProductId())) {
			return new Response(false, EpSystemError.INVOICE_PRODUCT_ID_MISMATCH);
		}
		if (orgInvoiceProduct.getQty() != invoiceProduct.getQty()) {

			// Managing product inventory before updating product to the invoice
			try {
				productInvBD.cancelProductSale(orgInvoiceProduct.getProduct().getProductCode(),
						orgInvoiceProduct.getQty());
				productInvBD.sellProduct(invoiceProduct.getProduct().getProductCode(), invoiceProduct.getQty());

			} catch (SysException e) {
				return new Response(false, e.getErrorCode());
			}
		} else {
			logger.info("Quantities are same ... Nothing to update in Product Inventory! Proceeding ahead.");
		}

		invoiceProduct = this.calculateEffectiveTaxAndPrice(invoiceProduct);

		logger.debug("Updating the product to invoice: " + invoiceProduct);

		Response response = invoiceDAO.updateProductInInvoice(invoiceProduct);

		if (response.isStatus()) {
			updateEffectiveTotalsInInvoice(invoiceProduct.getInvoiceId(), invoiceProduct.getUpdateBy(),
					invoiceProduct.getUpdateTimestamp());
		}
		return response;

	}

	private InvoiceProduct calculateEffectiveTaxAndPrice(InvoiceProduct invoiceProduct) {
		BigDecimal totalAmountBeforeTax = invoiceProduct.getRatePerUnit()
				.multiply(new BigDecimal(invoiceProduct.getQty()));
		invoiceProduct.setTotalBeforeTax(totalAmountBeforeTax);

		BigDecimal totalAmountAfterTax = totalAmountBeforeTax;
		logger.debug("totalAmountAfterTax: " + totalAmountAfterTax);
		Invoice invoice = this.getInvoice(invoiceProduct.getInvoiceId());

		if (!invoice.isGstInvoice()) {
			/* VAT multiplier */
			BigDecimal vatPercentage = new BigDecimal(invoiceProduct.getVatType().getTax_percentage());
			BigDecimal vatPercentageMultiplier = vatPercentage.divide(new BigDecimal(100));
			logger.debug("vatPercentageMultiplier: " + vatPercentageMultiplier);
			BigDecimal effectiveVat = totalAmountBeforeTax.multiply(vatPercentageMultiplier);
			logger.debug("effectiveVat " + effectiveVat);
			invoiceProduct.setVatAmount(effectiveVat);
			totalAmountAfterTax = totalAmountAfterTax.add(effectiveVat);
			logger.debug("totalAmountAfter VAT Tax " + totalAmountAfterTax);
			/* End Of VAT multiplier */
		} else {
			/* GST multiplier */
			BigDecimal cgstPercentage = new BigDecimal(invoiceProduct.getCgstType().getTax_percentage());
			BigDecimal cgstPercentageMultiplier = cgstPercentage.divide(new BigDecimal(100));
			logger.debug("cgstPercentageMultiplier: " + cgstPercentageMultiplier);
			BigDecimal effectiveCgst = totalAmountBeforeTax.multiply(cgstPercentageMultiplier);
			logger.debug("effectiveCgst " + effectiveCgst);
			invoiceProduct.setCgstAmount(effectiveCgst);
			totalAmountAfterTax = totalAmountAfterTax.add(effectiveCgst);
			logger.debug("totalAmountAfter CGST Tax " + totalAmountAfterTax);

			BigDecimal sgstPercentage = new BigDecimal(invoiceProduct.getSgstType().getTax_percentage());
			BigDecimal sgstPercentageMultiplier = sgstPercentage.divide(new BigDecimal(100));
			logger.debug("sgstPercentageMultiplier: " + sgstPercentageMultiplier);
			BigDecimal effectiveSgst = totalAmountBeforeTax.multiply(sgstPercentageMultiplier);
			logger.debug("effectiveSgst " + effectiveSgst);
			invoiceProduct.setSgstAmount(effectiveSgst);
			totalAmountAfterTax = totalAmountAfterTax.add(effectiveSgst);
			logger.debug("totalAmountAfter SGST Tax " + totalAmountAfterTax);
			/* End Of GST multiplier */
		}
		logger.debug("discount " + invoiceProduct.getDiscount());
		BigDecimal totalPrice = totalAmountAfterTax.subtract(invoiceProduct.getDiscount());
		logger.debug("totalPrice after discount " + totalPrice);
		invoiceProduct.setTotalPrice(totalPrice);

		return invoiceProduct;
	}

	private void updateEffectiveTotalsInInvoice(Integer invoiceId, String updateBy, Timestamp updateTimestamp) {

		Invoice invoice = invoiceDAO.getInvoice(invoiceId);

		BigDecimal totalBeforeTax = invoiceDAO.calculateTotalBeforeTaxForInvoice(invoiceId);
		if (!invoice.isGstInvoice()) {
			BigDecimal totalVATAmount = invoiceDAO.calculateTotalVATForInvoice(invoiceId);
			invoice.setTotalVat(totalVATAmount);
		} else {
			BigDecimal totalCGSTAmount = invoiceDAO.calculateTotalCGSTForInvoice(invoiceId);
			invoice.setTotalCgst(totalCGSTAmount);
			BigDecimal totalSGSTAmount = invoiceDAO.calculateTotalSGSTForInvoice(invoiceId);
			invoice.setTotalSgst(totalSGSTAmount);
		}
		BigDecimal totalDiscount = invoiceDAO.calculateTotalDiscountInInvoice(invoiceId);
		BigDecimal totalPrice = invoiceDAO.calculateTotalPriceForInvoice(invoiceId);

		invoice.setTotalAmountBeforeTax(totalBeforeTax);
		invoice.setTotalDiscount(totalDiscount);
		invoice.setTotalAmount(totalPrice);
		invoice.setUpdateBy(updateBy);
		invoice.setUpdateTimestamp(updateTimestamp);

		logger.debug("Updating invoice with changed TotalVAT and TotalPrice: " + invoice);
		invoiceDAO.updateInvoice(invoice);

	}

	@Override
	@Transactional
	public Response deleteProductFromInvoice(InvoiceProduct invoiceProduct) {
		logger.info("DELETE invoiceProduct: " + invoiceProduct);
		// Managing product inventory before deleting product to the invoice
		try {
			productInvBD.cancelProductSale(invoiceProduct.getProduct().getProductCode(), invoiceProduct.getQty());
			Response response =  invoiceDAO.deleteProductFromInvoice(invoiceProduct);
			
			if (response.isStatus()) {
				updateEffectiveTotalsInInvoice(invoiceProduct.getInvoiceId(), invoiceProduct.getUpdateBy(),
						invoiceProduct.getUpdateTimestamp());
			}
			
			return response;
			
		} catch (SysException e) {
			return new Response(false, e.getErrorCode());
		}
	}

	@Override
	public BigDecimal getTotalSalesAmountInMonth() {
		return invoiceDAO.getTotalSalesAmountInMonth();
	}

	@Override
	public int getCountOfTotalInvoicesInMonth() {
		return invoiceDAO.getCountOfTotalInvoicesInMonth();
	}

	@Override
	public int getCountOfCustomerBilledInMonth() {
		return invoiceDAO.getCountOfCustomerBilledInMonth();
	}

	@Override
	public BigDecimal getTotalSalesAmountInYear() {
		return invoiceDAO.getTotalSalesAmountInYear();
	}

	@Override
	public int getCountOfTotalInvoicesForYear() {
		return invoiceDAO.getCountOfTotalInvoicesForYear();
	}

	@Override
	public BigDecimal getTotalVATInYear() {
		return invoiceDAO.getTotalVATInYear();
	}

	@Override
	public int getCountOfCustomerBilledForYear() {
		return invoiceDAO.getCountOfCustomerBilledForYear();
	}

}