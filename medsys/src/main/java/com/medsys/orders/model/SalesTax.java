package com.medsys.orders.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SalesTax {
	
	static Logger logger = LoggerFactory.getLogger(SalesTax.class);

	private Date fromDate;
	
	private Date toDate;

	
	private BigDecimal totalCGSTTax;
	
	private BigDecimal totalSGSTTax;

	java.util.List<Invoice> invoicesHavingCGST;
	java.util.List<Invoice> invoicesHavingSGST;

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

	public BigDecimal getTotalCGSTTax() {
		return totalCGSTTax;
	}

	public void setTotalCGSTTax(BigDecimal totalCGSTTax) {
		if (totalCGSTTax != null) {
			this.totalCGSTTax = totalCGSTTax.setScale(0, RoundingMode.HALF_UP);
		} else {
			this.totalCGSTTax = totalCGSTTax;
		}
	}

	public BigDecimal getTotalSGSTTax() {
		return totalSGSTTax;
	}

	public void setTotalSGSTTax(BigDecimal totalSGSTTax) {
		if (totalSGSTTax != null) {
			this.totalSGSTTax = totalSGSTTax.setScale(0, RoundingMode.HALF_UP);
		} else {
			this.totalSGSTTax = totalSGSTTax;
		}
	}

	
	public java.util.List<Invoice> getInvoicesHavingCGST() {
		return invoicesHavingCGST;
	}

	public void setInvoicesHavingCGST(java.util.List<Invoice> invoicesHavingCGST) {
		this.invoicesHavingCGST = invoicesHavingCGST;
	}

	public java.util.List<Invoice> getInvoicesHavingSGST() {
		return invoicesHavingSGST;
	}

	public void setInvoicesHavingSGST(java.util.List<Invoice> invoicesHavingSGST) {
		this.invoicesHavingSGST = invoicesHavingSGST;
	}

	@Override
	public String toString() {
		return "SalesTax [fromDate=" + fromDate + ", toDate=" + toDate
				+ ", totalCGSTTax=" + totalCGSTTax + ", totalSGSTTax=" + totalSGSTTax + "]";
	}

}
