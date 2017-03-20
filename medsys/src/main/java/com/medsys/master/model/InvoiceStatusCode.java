package com.medsys.master.model;

public enum InvoiceStatusCode {

	ACTIVE("A"), 
	PRINT_READY("PR"), 
	PART_PAYMENT_RECEIVED("PP"), 
	PAYMENT_COMPLETED("CS"), 
	CANCELLED("CAN");

	private String code;

	public String getCode() {
		return code;
	}

	private InvoiceStatusCode(String code) {
		this.code = code;
	}

	public static InvoiceStatusCode getInvoiceStatusCode(String code) {
		for (InvoiceStatusCode invCode : values()) {
			if (invCode.getCode().equals(code)) {
				return invCode;
			}
		}
		return null;
	}

}
