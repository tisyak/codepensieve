package com.medsys.orders.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.medsys.common.model.Response;
import com.medsys.orders.model.Invoice;
import com.medsys.orders.model.InvoiceProduct;
 
public interface InvoiceDAO {
 
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

	List<Invoice> searchForInvoice(Date fromDate, Date toDate);

	public BigDecimal getTotalSalesAmountInMonth();

	public BigDecimal getTotalSalesAmountInYear();

	public int getCountOfTotalInvoicesForYear();

	public int getCountOfCustomerBilledForYear();

	public int getCountOfCustomerBilledInMonth();

	public int getCountOfTotalInvoicesInMonth();

	BigDecimal getTotalSalesAmountInDateRange(Date startDate, Date endDate);

	int getCountOfTotalInvoicesInDateRange(Date startDate, Date endDate);
	
	public BigDecimal getTotalVATInYear();

	BigDecimal getTotalVATInDateRange(Date startDate, Date endDate);

	int getCountOfCustomerBilledInDateRange(Date startDate, Date endDate);

	public BigDecimal getTotalGSTInYear();
	
	BigDecimal getTotalGSTInDateRange(Date startDate, Date endDate);

	//public void updateEffectiveTotalsInInvoice(Integer invoiceId, String updateBy, Timestamp updateTimestamp);
	
}