package com.medsys.common.model;

public class EmailLinkParameters {

	String key;
	String code;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "EmailLinkParameters [key=" + key + ", code=" + code + "]";
	}

}
