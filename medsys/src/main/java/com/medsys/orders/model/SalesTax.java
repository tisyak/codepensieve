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

	private BigDecimal totalVATTax;
	
	private BigDecimal totalCGSTTax;
	
	private BigDecimal totalSGSTTax;

	java.util.Set<Invoice> invoicesHavingVAT;
	java.util.Set<Invoice> invoicesHavingCGST;
	java.util.Set<Invoice> invoicesHavingSGST;

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

	public BigDecimal getTotalVATTax() {
		return totalVATTax;
	}

	public void setTotalVATTax(BigDecimal totalVATTax) {
		if (totalVATTax != null) {
			this.totalVATTax = totalVATTax.setScale(0, RoundingMode.HALF_UP);
		} else {
			this.totalVATTax = totalVATTax;
		}
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

	public java.util.Set<Invoice> getInvoicesHavingVAT() {
		return invoicesHavingVAT;
	}

	public void setInvoicesHavingVAT(java.util.Set<Invoice> invoicesHavingVAT) {
		this.invoicesHavingVAT = invoicesHavingVAT;
	}

	public java.util.Set<Invoice> getInvoicesHavingCGST() {
		return invoicesHavingCGST;
	}

	public void setInvoicesHavingCGST(java.util.Set<Invoice> invoicesHavingCGST) {
		this.invoicesHavingCGST = invoicesHavingCGST;
	}

	public java.util.Set<Invoice> getInvoicesHavingSGST() {
		return invoicesHavingSGST;
	}

	public void setInvoicesHavingSGST(java.util.Set<Invoice> invoicesHavingSGST) {
		this.invoicesHavingSGST = invoicesHavingSGST;
	}

	@Override
	public String toString() {
		return "SalesTax [fromDate=" + fromDate + ", toDate=" + toDate + ", totalVATTax=" + totalVATTax
				+ ", totalCGSTTax=" + totalCGSTTax + ", totalSGSTTax=" + totalSGSTTax + "]";
	}

}
