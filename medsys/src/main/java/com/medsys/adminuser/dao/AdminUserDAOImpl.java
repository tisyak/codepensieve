package com.medsys.adminuser.dao;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.medsys.adminuser.model.AdminResponse;
import com.medsys.adminuser.model.AdminUser;
import com.medsys.common.model.Response;
import com.medsys.util.ConvertSHA2;
import com.medsys.util.EpSystemError;

@Repository
public class AdminUserDAOImpl implements AdminUserDAO {

	static Logger logger = LoggerFactory.getLogger(AdminUserDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public AdminResponse addUser(AdminUser user) {
		logger.debug("AdminUserDAOImpl.addUser() - [" + user.toString() + "]");
		final ConvertSHA2 convertSHA2 = new ConvertSHA2();
		AdminResponse adminResponse =  new AdminResponse();
		try {
			final String enPassword = convertSHA2.convertStringToSHA2(user
			        .getPassword());
			user.setPassword(enPassword);
			Integer adminId = (Integer)getCurrentSession().save(user);
			logger.debug(" Succesfully data inserted.");
			user.setId(adminId);
			adminResponse.setAdminUser(user);
			adminResponse.setResponse(new Response(true, null));
		} catch(HibernateException he) {
			logger.error(" HibernateException :"+he.getMessage());
			adminResponse.setResponse(new Response(false,EpSystemError.DB_EXCEPTION));
		} catch (NoSuchAlgorithmException he) {
			logger.error(" NoSuchAlgorithmException :"+he.getMessage());
			adminResponse.setResponse(new Response(false,EpSystemError.SYSTEM_INTERNAL_ERROR));
		}
		
		return adminResponse;
	}

	@Override
	public AdminUser getUser(Integer userId) throws UsernameNotFoundException {
		logger.debug("AdminUserDAOImpl.getUser() - [" + userId + "]");
		AdminUser userObject = (AdminUser) getCurrentSession().get(
				AdminUser.class, userId);

		if (userObject == null) {
			logger.debug("No user found.");  
			throw new UsernameNotFoundException("AdminUser id [" + userId
					+ "] not found");
		} else {
			//logger.debug("returning user object :"+userObject); 
			return userObject;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AdminUser getUserByUserName(String usersName) throws UsernameNotFoundException {
		logger.debug("AdminUserDAOImpl.getUserByUserName() - [" + usersName + "]");
		Query query = getCurrentSession().createQuery(
				"from AdminUser where lower(username) = :usersName");
		query.setString("usersName", usersName.toLowerCase());
		AdminUser userObject = null;
		logger.debug(query.toString());
		if (query.list().size() == 0) {
			logger.debug("No user found.");
			//throw new UsernameNotFoundException("AdminUser [" + usersName
			//		+ "] not found");
			return userObject;
		} else {
			
			logger.debug("AdminUser List Size: " + query.list().size());
			List<AdminUser> list = (List<AdminUser>) query.list();
			userObject = (AdminUser) list.get(0);
			return userObject;
		}
	}

	@Override
	public void deleteUser(Integer userId) throws UsernameNotFoundException {
		AdminUser user = getUser(userId);
		if (user != null) {
			getCurrentSession().delete(user);
		} else {
			throw new UsernameNotFoundException("AdminUser ID : [" + userId
					+ "] not found");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AdminUser> getUsers() {
		return getCurrentSession().createQuery("from AdminUser").list();
	}

	@Override
	public AdminResponse updateUser(AdminUser user) {
		logger.debug("AdminUserDAOImpl.updateAdminUser() - [" + user + "]");
		AdminResponse adminResponse = new AdminResponse();
		try {
			getCurrentSession().update(user);
			logger.debug("adminuser successfully updated");
			adminResponse.setResponse(new Response(true, null));
		}catch(HibernateException he) {
			logger.debug("failed to update adminuser");
			adminResponse.setResponse(new Response(false, EpSystemError.DB_EXCEPTION));	
		}
		return adminResponse;
	}
	@Override
	public boolean checkUsernameAvailability(String username) {
		logger.debug("AdminUserDAOImpl.checkUsernameAvailability() - [" + username + "]");
		Query query = getCurrentSession().createSQLQuery("select lower(user_name) from admin_users"
				+ " where lower(user_name) = :user");
		query.setString("user", username);
		//query.list().size()
		//int status = query.executeUpdate();
		//logger.debug(" username checking status:"+ query.list().size());
		if(query.list().size() > 0) {
			logger.debug(" username available in table");
			return false;
		} else {
			logger.debug(" username not available in table");
			return true;
		}		
		
	}


}