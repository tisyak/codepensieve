package com.medsys.product.model;

public class ProductDeficit {
	
	private Integer productId;
	
	private String productCode;
	
	private String productDesc;
	
	private Integer availableQty; 
	
	private Integer engagedQty;
	
	private Integer requiredQty;
	
	private Integer deficit;
	
	private String setsWithDeficit;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public Integer getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(Integer availableQty) {
		this.availableQty = availableQty;
	}

	public Integer getEngagedQty() {
		return engagedQty;
	}

	public void setEngagedQty(Integer engagedQty) {
		this.engagedQty = engagedQty;
	}

	public Integer getRequiredQty() {
		return requiredQty;
	}

	public void setRequiredQty(Integer requiredQty) {
		this.requiredQty = requiredQty;
	}

	public Integer getDeficit() {
		return deficit;
	}

	public void setDeficit(Integer deficit) {
		this.deficit = deficit;
	}

	public String getSetsWithDeficit() {
		return setsWithDeficit;
	}

	public void setSetsWithDeficit(String setsWithDeficit) {
		this.setsWithDeficit = setsWithDeficit;
	}

	@Override
	public String toString() {
		return "ProductDeficit [productId=" + productId + ", productCode=" + productCode + ", productDesc="
				+ productDesc + ", availableQty=" + availableQty + ", engagedQty=" + engagedQty + ", requiredQty="
				+ requiredQty + ", deficit=" + deficit + ", setsWithDeficit=" + setsWithDeficit + "]";
	}
	
	
}
