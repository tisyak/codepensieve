package com.medsys.adminuser.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.medsys.adminuser.model.AdminResponse;
import com.medsys.adminuser.model.AdminRole;
import com.medsys.common.model.Response;
import com.medsys.util.EpSystemError;

@Repository
public class AdminRolesDAOImpl implements AdminRolesDAO {

	static Logger logger = LoggerFactory.getLogger(AdminRolesDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public AdminResponse addAdminRoles(AdminRole adminRoles) {
		logger.debug("AdminRolesDAOImpl.addAdminRoles() - [" + adminRoles.toString() + "]");
		AdminResponse adminResponse =  new AdminResponse();	
		try {
		getCurrentSession().save(adminRoles);
		logger.debug(" Succesfully data inserted.");		
		adminResponse.setResponse(new Response(true, null));
		} catch(HibernateException he) {
			logger.error(" HibernateException :"+he.getMessage());
			adminResponse.setResponse(new Response(false,EpSystemError.DB_EXCEPTION));
		}
		return adminResponse;
	}

	@Override
	public AdminResponse updateAdminRoles(AdminRole adminRoles) {
		logger.debug("AdminRolesDAOImpl.addAdminRoles() - [" + adminRoles.toString() + "]");
		AdminResponse adminResponse =  new AdminResponse();	
		try {
			getCurrentSession().update(adminRoles);
			logger.debug(" admin role Succesfully updated.");		
			adminResponse.setResponse(new Response(true, null));
		} catch(HibernateException he) {
			logger.error(" HibernateException :"+he.getMessage());
			adminResponse.setResponse(new Response(false,EpSystemError.DB_EXCEPTION));
			}
		return adminResponse;
		}

}
