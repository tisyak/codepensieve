package com.medsys.orders.model;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SalesTax {
	
	static Logger logger = LoggerFactory.getLogger(SalesTax.class);

	private Date fromDate;
	
	private Date toDate;

	private BigDecimal totalSalesTax;

	java.util.Set<Invoice> invoices;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public BigDecimal getTotalSalesTax() {
		return totalSalesTax;
	}

	public void setTotalSalesTax(BigDecimal totalSalesTax) {
		this.totalSalesTax = totalSalesTax;
	}

	public java.util.Set<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(java.util.Set<Invoice> invoices) {
		this.invoices = invoices;
	}

	@Override
	public String toString() {
		return "SalesTax [fromDate=" + fromDate + ", toDate=" + toDate + ", totalSalesTax=" + totalSalesTax
				+ ", invoices=" + invoices + "]";
	}

}
