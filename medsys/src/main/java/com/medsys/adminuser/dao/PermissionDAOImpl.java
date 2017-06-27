package com.medsys.adminuser.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.medsys.adminuser.model.Permission;
 
@Repository
public class PermissionDAOImpl implements PermissionDAO {
    static Logger logger = LoggerFactory.getLogger(PermissionDAOImpl.class);
 
    @Autowired
    private SessionFactory sessionFactory;
 
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
 
    @Override
    public void addPermission(Permission permission) throws DuplicateKeyException {
        logger.debug("PermissionDAOImpl.addPermission() - [" + permission.getPermissionname() + "]");
        try {
            // if the permission is not found, then a PermissionNotFoundException is
            // thrown from the getPermission method call, and the new permission will be 
            // added.
            //
            // if the permission is found, then the flow will continue from the getPermission
            // method call and the DuplicatePermissionException will be thrown.
            Permission permCheck = getPermission(permission.getPermissionname());
            String message = "The permission [" + permCheck.getPermissionname() + "] already exists";
            throw new DuplicateKeyException(message);
        } catch (EmptyResultDataAccessException e) {
            getCurrentSession().save(permission);
        }
    }
 
    @Override
    public Permission getPermission(int permission_id) throws EmptyResultDataAccessException {
        Permission permObject = (Permission) getCurrentSession().get(Permission.class, permission_id);
        if (permObject == null ) {
            throw new EmptyResultDataAccessException("Permission id [" + permission_id + "] not found",1);
        } else {
            return permObject;
        }
    }
 
    @Override
    public Permission getPermission(String usersPermission) throws EmptyResultDataAccessException {
        logger.debug("PermissionDAOImpl.getPermission() - [" + usersPermission + "]");
        Query <Permission>query = getCurrentSession().createQuery("from Permission where permissionname = :usersPermission ",Permission.class);
        query.setParameter("usersPermission", usersPermission);
         
        logger.debug(query.toString());
        if (query.getResultList().size() == 0 ) {
            throw new EmptyResultDataAccessException("Permission [" + usersPermission + "] not found",1);
        } else {
            logger.debug("Permission List Size: " + query.getResultList().size());
            List<Permission> list = (List<Permission>)query.getResultList();
            Permission permObject = (Permission) list.get(0);
 
            return permObject;
        }
    }
 
    @Override
    public void updatePermission(Permission permission) throws EmptyResultDataAccessException {
        Permission permissionToUpdate = getPermission(permission.getId());
        permissionToUpdate.setId(permission.getId());
        permissionToUpdate.setPermissionname(permission.getPermissionname());
        getCurrentSession().update(permissionToUpdate);
    }
 
    @Override
    public void deletePermission(int permission_id) throws EmptyResultDataAccessException {
        Permission permission = getPermission(permission_id);
        if (permission != null) {
            getCurrentSession().delete(permission);
        }
    }
 
    @Override
    @SuppressWarnings("unchecked")
    public List<Permission> getPermissions() {
        return getCurrentSession().createQuery("from Permission").getResultList();
    }
}