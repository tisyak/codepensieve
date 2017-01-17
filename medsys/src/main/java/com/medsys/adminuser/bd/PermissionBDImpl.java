package com.medsys.adminuser.bd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.adminuser.dao.PermissionDAO;
import com.medsys.adminuser.model.Permission;
 
@Service
@Transactional
public class PermissionBDImpl implements PermissionBD {
    static Logger logger = LoggerFactory.getLogger(PermissionBDImpl.class);
     
    @Autowired
    private PermissionDAO permissionDAO;
 
    @Override
    public void addPermission(Permission permission) throws DuplicateKeyException {
        permissionDAO.addPermission(permission);
    }
 
    @Override
    public Permission getPermission(int id) throws EmptyResultDataAccessException {
        return permissionDAO.getPermission(id);
    }
 
    @Override
    public Permission getPermission(String permissionname) throws EmptyResultDataAccessException {
        return permissionDAO.getPermission(permissionname);
    }
 
    @Override
    public void updatePermission(Permission permission) throws EmptyResultDataAccessException {
        permissionDAO.updatePermission(permission);
    }
 
    @Override
    public void deletePermission(int id) throws EmptyResultDataAccessException {
        permissionDAO.deletePermission(id);
    }
 
    @Override
    public List<Permission> getPermissions() {
        return permissionDAO.getPermissions();
    }
}