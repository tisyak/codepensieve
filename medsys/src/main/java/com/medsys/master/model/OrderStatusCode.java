package com.medsys.master.model;

public enum OrderStatusCode {

	ACTIVE("A"), 
	PRINT_READY("PR"), 
	SET_RESTORED("SR"), 
	INVOICE_GENERATED("IG"), 
	DISCARDED("DI");

	private String code;

	public String getCode() {
		return code;
	}

	private OrderStatusCode(String code) {
		this.code = code;
	}

	public static OrderStatusCode getOrderStatusCode(String code) {
		for (OrderStatusCode ordCode : values()) {
			if (ordCode.getCode().equals(code)) {
				return ordCode;
			}
		}
		return null;
	}

}
