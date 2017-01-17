package com.medsys.adminuser.model;

import com.medsys.common.model.Response;

public class AdminResponse {

	AdminUser adminUser;
	
	Response response;

	public AdminResponse() {
	}
	
	public AdminResponse(AdminUser adminUser, Response response) {
		this.adminUser = adminUser;
		this.response = response;
	}

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "AdminResponse [adminUser=" + adminUser + ", response="
				+ response + "]";
	}
	
}
