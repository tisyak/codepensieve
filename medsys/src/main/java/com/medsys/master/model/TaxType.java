package com.medsys.master.model;

public enum TaxType {

	VAT("VAT"), 
	CGST("CGST"), 
	SGST("SGST");

	private String code;

	public String getCode() {
		return code;
	}

	private TaxType(String code) {
		this.code = code;
	}

	public static TaxType getTaxTypeByCode(String code) {
		for (TaxType taxTypeCode : values()) {
			if (taxTypeCode.getCode().equals(code)) {
				return taxTypeCode;
			}
		}
		return null;
	}

}
