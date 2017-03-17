package com.medsys.orders.bd;

import java.util.List;

import com.medsys.common.model.Response;
import com.medsys.orders.model.Invoice;
import com.medsys.orders.model.InvoiceProduct;

public interface InvoiceBD {

	public Response addInvoice(Invoice invoice);
	
	public Invoice getInvoice(Integer invoiceId);

	public Invoice updateInvoice(Invoice invoice);

	public Response deleteInvoice(Integer invoiceId);

	public List<Invoice> getAllInvoice();
	
	public List<Invoice> searchForInvoice(Invoice invoice);
	
	public List<InvoiceProduct> getAllProductsInInvoice(Integer invoiceId);

	public Response addProductToInvoice(InvoiceProduct newInvoiceProduct);

	public InvoiceProduct getProductInInvoice(Integer invoiceProductId);

	public Response updateProductInInvoice(InvoiceProduct invoiceProduct);

	public Response deleteProductFromInvoice(InvoiceProduct invoiceProduct);

	

}