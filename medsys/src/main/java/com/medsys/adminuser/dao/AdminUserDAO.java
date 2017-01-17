package com.medsys.adminuser.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.medsys.adminuser.model.AdminResponse;
import com.medsys.adminuser.model.AdminUser;
 
public interface AdminUserDAO {
 
    public AdminResponse addUser(AdminUser user);
 
    public AdminUser getUser(Integer userId) throws UsernameNotFoundException;
 
    public AdminUser getUserByUserName(String username) throws UsernameNotFoundException;
 
    public AdminResponse updateUser(AdminUser user) throws UsernameNotFoundException;
 
    public void deleteUser(Integer userId) throws UsernameNotFoundException;
 
    public List<AdminUser> getUsers();
    
    public boolean checkUsernameAvailability(String username);
    
}