package com.medsys.master.model;

public enum PurchaseOrderStatusCode {

	ACTIVE("A"), 
	DISPATCHED("DS"), 
	RECEIVED("RC"), 
	DISCARDED("DI");

	private String code;

	public String getCode() {
		return code;
	}

	private PurchaseOrderStatusCode(String code) {
		this.code = code;
	}

	public static PurchaseOrderStatusCode getPurchaseOrderStatusCode(String code) {
		for (PurchaseOrderStatusCode purchaseOrderCode : values()) {
			if (purchaseOrderCode.getCode().equals(code)) {
				return purchaseOrderCode;
			}
		}
		return null;
	}

}
