package com.medsys.adminuser.bd;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.medsys.adminuser.model.Role;
 
public interface RoleBD {
 
    public void addRole(Role role) throws DuplicateKeyException;
 
    public Role getRole(int id) throws EmptyResultDataAccessException;
     
    public Role getRole(String rolename) throws EmptyResultDataAccessException;
 
    public void updateRole(Role role) throws EmptyResultDataAccessException;
 
    public void deleteRole(int id) throws EmptyResultDataAccessException;
 
    public List<Role> getRoles();
 
}