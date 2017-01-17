package com.medsys.adminuser.model;

public enum AdminUserStatusCode {

	REGISTERED("R"),
	
	VERIFIED("V"),
	
	ACTIVATED("A"),
	
	DEACTIVATED("D");
	
	private String statusCode;

	public String getStatusCode() {
		return statusCode;
	}

	private AdminUserStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	
}
