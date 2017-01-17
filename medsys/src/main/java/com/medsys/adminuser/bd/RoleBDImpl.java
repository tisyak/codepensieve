package com.medsys.adminuser.bd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.adminuser.dao.RoleDAO;
import com.medsys.adminuser.model.Role;
 
@Service
@Transactional
public class RoleBDImpl implements RoleBD {
    static Logger logger = LoggerFactory.getLogger(RoleBDImpl.class);
     
    @Autowired
    private RoleDAO roleDAO;
 
    @Override
    public void addRole(Role role) throws DuplicateKeyException {
        roleDAO.addRole(role);
    }
 
    @Override
    public Role getRole(int id) throws EmptyResultDataAccessException {
        return roleDAO.getRole(id);
    }
 
    @Override
    public Role getRole(String rolename) throws EmptyResultDataAccessException {
        return roleDAO.getRole(rolename);
    }
 
    @Override
    public void updateRole(Role role) throws EmptyResultDataAccessException {
        roleDAO.updateRole(role);
    }
 
    @Override
    public void deleteRole(int id) throws EmptyResultDataAccessException {
        roleDAO.deleteRole(id);
    }
 
    @Override
    public List<Role> getRoles() {
        return roleDAO.getRoles();
    }
}