package com.medsys.product.model;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductDeficitSummary {
	
	static Logger logger = LoggerFactory.getLogger(ProductDeficitSummary.class);

	private Date fromDate;
	
	private Date toDate;

	private Integer totalProductDeficitCount;

	java.util.List<ProductDeficit> productDeficits;

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

	public Integer getTotalProductDeficitCount() {
		return totalProductDeficitCount;
	}

	public void setTotalProductDeficitCount(Integer totalProductDeficitCount) {
		this.totalProductDeficitCount = totalProductDeficitCount;
	}

	public java.util.List<ProductDeficit> getProductDeficits() {
		return productDeficits;
	}

	public void setProductDeficits(java.util.List<ProductDeficit> productDeficits) {
		this.productDeficits = productDeficits;
	}

	@Override
	public String toString() {
		return "ProductDeficitSummary [fromDate=" + fromDate + ", toDate=" + toDate + ", totalProductDeficitCount="
				+ totalProductDeficitCount + "]";
	}

	

}
