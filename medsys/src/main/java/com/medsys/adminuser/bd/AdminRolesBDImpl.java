package com.medsys.adminuser.bd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.adminuser.dao.AdminRolesDAO;
import com.medsys.adminuser.model.AdminResponse;
import com.medsys.adminuser.model.AdminRole;

@Service
@Transactional
public class AdminRolesBDImpl implements AdminRolesBD {

	@Autowired
	private AdminRolesDAO adminRolesDAO;
	
	@Override
	public AdminResponse addAdminRoles(AdminRole adminRoles) {
		return adminRolesDAO.addAdminRoles(adminRoles);
	}

	@Override
	public AdminResponse updateAdminRoles(AdminRole adminRoles) {
		return adminRolesDAO.updateAdminRoles(adminRoles);
	}

}
