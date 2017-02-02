package com.medsys.adminuser.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.medsys.adminuser.model.Role;

@Repository
public class RoleDAOImpl implements RoleDAO {
	static Logger logger = LoggerFactory.getLogger(RoleDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void addRole(Role role) throws DuplicateKeyException {
		logger.debug("RoleDAOImpl.addRole() - [" + role.getRolename() + "]");
		try {
			// if the role is not found, then a RoleNotFoundException is
			// thrown from the getRole method call, and the new role will be
			// added.
			//
			// if the role is found, then the flow will continue from the
			// getRole
			// method call and the DuplicateRoleException will be thrown.
			Role roleCheck = getRole(role.getRolename());
			String message = "The role [" + roleCheck.getRolename()
					+ "] already exists";
			throw new DuplicateKeyException(message);
		} catch (EmptyResultDataAccessException e) {
			getCurrentSession().save(role);
		}
	}

	@Override
	public Role getRole(int role_id) throws EmptyResultDataAccessException {
		logger.debug("RoleDAOImpl.getRole() - [" + role_id + "]");
		Role roleObject = (Role) getCurrentSession().get(Role.class, role_id);

		if (roleObject == null) {
			logger.debug("No role found.");
			throw new EmptyResultDataAccessException("Role id [" + role_id
					+ "] not found", 1);
		} else {
			logger.debug("returning Role object");
			return roleObject;
		}
	}

	@Override
	public Role getRole(String usersRole) throws EmptyResultDataAccessException {
		logger.debug("getRole() - [" + usersRole + "]");
		Query query = getCurrentSession().createQuery(
				"from Role where rolename = :usersRole ");
		query.setString("usersRole", usersRole);

		logger.debug(query.toString());
		if (query.list().size() == 0) {
			logger.debug("No role found.");
			throw new UsernameNotFoundException("Role [" + usersRole
					+ "] not found");
		} else {

			logger.debug("AdminUser List Size: " + query.list().size());
			@SuppressWarnings("unchecked")
			List<Role> list = (List<Role>) query.list();
			Role roleObject = (Role) list.get(0);

			return roleObject;
		}
	}

	@Override
	public void updateRole(Role role) throws EmptyResultDataAccessException {
		Role roleToUpdate = getRole(role.getId());
		roleToUpdate.setRolename(role.getRolename());
		roleToUpdate.setAdminRoles(role.getAdminRoles());
		roleToUpdate.setPermissions(role.getPermissions());
		getCurrentSession().update(roleToUpdate);
	}

	@Override
	public void deleteRole(int role_id) throws EmptyResultDataAccessException {
		Role role = getRole(role_id);
		if (role != null) {
			getCurrentSession().delete(role);
		} else {
			throw new EmptyResultDataAccessException("Role ID : [" + role_id
					+ "] not found",1);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRoles() {
		return getCurrentSession().createQuery("from Role").list();
	}
}