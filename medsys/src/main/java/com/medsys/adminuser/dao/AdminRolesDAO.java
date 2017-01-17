package com.medsys.adminuser.dao;

import com.medsys.adminuser.model.AdminResponse;
import com.medsys.adminuser.model.AdminRole;

public interface AdminRolesDAO {

	public AdminResponse addAdminRoles(AdminRole adminRoles);
	
	public AdminResponse updateAdminRoles(AdminRole adminRoles);
}
