package com.medsys.orders.bd;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.common.model.Response;
import com.medsys.exception.SysException;
import com.medsys.master.model.TaxMaster;
import com.medsys.orders.dao.InvoiceDAO;
import com.medsys.orders.model.Invoice;
import com.medsys.orders.model.InvoiceProduct;
import com.medsys.product.bd.ProductInvBD;
import com.medsys.product.model.ProductInv;
import com.medsys.ui.util.UIConstants;
import com.medsys.util.EpSystemError;

@Service
@Transactional
public class InvoiceBDImpl implements InvoiceBD {
	static Logger logger = LoggerFactory.getLogger(InvoiceBDImpl.class);

	@Autowired
	private InvoiceDAO invoiceDAO;

	@Autowired
	private ProductInvBD productInvBD;

	@Override
	public Response addInvoice(Invoice user) {
		logger.debug("InvoiceBD: Adding invoice.");
		return invoiceDAO.addInvoice(user);

	}

	@Override
	public Invoice getInvoice(Integer invoiceId) {
		return invoiceDAO.getInvoice(invoiceId);
	}

	@Override
	public Invoice updateInvoice(Invoice user) {
		logger.debug("InvoiceBD: Updating invoice.");
		return invoiceDAO.updateInvoice(user);
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
	public List<InvoiceProduct> getAllProductsInInvoice(Integer invoiceId) {
		logger.info("Get All products in Invoice: " + invoiceId);
		List<InvoiceProduct> invoiceProducts = invoiceDAO.getAllProductsInInvoice(invoiceId);
		for(InvoiceProduct product: invoiceProducts){
			try{
				ProductInv productInv = productInvBD.getProduct(product.getProduct().getProductId());
				product.setAvailableQty(productInv.getAvailableQty());
			}catch(EmptyResultDataAccessException e){ 
				logger.debug("Product "+ product.getProduct().getProductCode() +" not found in Inventory");
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
			MathContext mc = new MathContext(Integer.parseInt(UIConstants.MATH_PRECISION_CONTEXT.getValue())); // 4 precision
			BigDecimal totalAmountBeforeTax = newInvoiceProduct.getRatePerUnit().multiply(new BigDecimal(newInvoiceProduct.getQty()), mc);
			BigDecimal vatPercentageMultiplier = new BigDecimal((double)newInvoiceProduct.getVatType().getTax_percentage()/(double)100,mc);
			logger.debug("vatPercentageMultiplier: " + vatPercentageMultiplier);
			BigDecimal effectiveVat = totalAmountBeforeTax.multiply(vatPercentageMultiplier, mc);
			logger.debug("effectiveVat " + effectiveVat);
			newInvoiceProduct.setVatAmount(effectiveVat);
			newInvoiceProduct.setTotalPrice(totalAmountBeforeTax.add(effectiveVat,mc));
					
			productInvBD.sellProduct(newInvoiceProduct.getProduct().getProductCode(), newInvoiceProduct.getQty());
			return invoiceDAO.addProductToInvoice(newInvoiceProduct);
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
			logger.info("Quantities, Rate per Unit and VAT Types are same ... Nothing to update in Product Inventory! Proceeding ahead.");
		}
		orgInvoiceProduct.setQty(invoiceProduct.getQty());
		orgInvoiceProduct.setRatePerUnit(invoiceProduct.getRatePerUnit());
		orgInvoiceProduct.setVatType(invoiceProduct.getVatType());
		MathContext mc = new MathContext(Integer.parseInt(UIConstants.MATH_PRECISION_CONTEXT.getValue())); // 4 precision
		BigDecimal totalAmountBeforeTax = invoiceProduct.getRatePerUnit().multiply(new BigDecimal(invoiceProduct.getQty()), mc);
		BigDecimal vatPercentageMultiplier = new BigDecimal((double)invoiceProduct.getVatType().getTax_percentage()/(double)100,mc);
		logger.debug("vatPercentageMultiplier: " + vatPercentageMultiplier);
		BigDecimal effectiveVat = totalAmountBeforeTax.multiply(vatPercentageMultiplier, mc);
		logger.debug("effectiveVat " + effectiveVat);
		orgInvoiceProduct.setVatAmount(effectiveVat);
		orgInvoiceProduct.setTotalPrice(totalAmountBeforeTax.add(effectiveVat,mc));
		
		logger.debug("Adding the product to invoice: " + orgInvoiceProduct);
		orgInvoiceProduct.setUpdateBy(invoiceProduct.getUpdateBy());
		orgInvoiceProduct.setUpdateTimestamp(invoiceProduct.getUpdateTimestamp());
		
		return invoiceDAO.updateProductInInvoice(orgInvoiceProduct);

	}

	@Override
	public Response deleteProductFromInvoice(InvoiceProduct invoiceProduct) {
		logger.info("DELETE invoiceProduct: " + invoiceProduct);

		// Managing product inventory before updating product to the invoice
		try {
			productInvBD.cancelProductSale(invoiceProduct.getProduct().getProductCode(), invoiceProduct.getQty());
			return invoiceDAO.deleteProductFromInvoice(invoiceProduct);
		} catch (SysException e) {
			return new Response(false, e.getErrorCode());
		}

	}

}