package com.medsys.adminuser.bd;

import com.medsys.adminuser.model.AdminResponse;
import com.medsys.adminuser.model.AdminRole;

public interface AdminRolesBD {

	public AdminResponse addAdminRoles(AdminRole adminRoles);
	
	public AdminResponse updateAdminRoles(AdminRole adminRoles);
}
