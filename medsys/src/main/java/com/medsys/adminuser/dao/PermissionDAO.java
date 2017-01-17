package com.medsys.adminuser.dao;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.medsys.adminuser.model.Permission;
 
public interface PermissionDAO {
 
    public void addPermission(Permission permission) throws DuplicateKeyException;
 
    public Permission getPermission(int id) throws EmptyResultDataAccessException;
 
    public Permission getPermission(String permissionName) throws EmptyResultDataAccessException;
 
    public void updatePermission(Permission permission) throws EmptyResultDataAccessException;
 
    public void deletePermission(int id) throws EmptyResultDataAccessException;
 
    public List<Permission> getPermissions();
 
}