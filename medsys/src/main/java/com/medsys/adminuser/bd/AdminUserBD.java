package com.medsys.adminuser.bd;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.medsys.adminuser.model.AdminResponse;
import com.medsys.adminuser.model.AdminUser;
 
public interface AdminUserBD extends UserDetailsService {
 
    public AdminResponse addUser(AdminUser user);
 
    public AdminUser getUser(Integer userId);
 
    public AdminUser getUserByUserName(String username);
    
    public AdminResponse authenticateUser(AdminUser adminUser);
 
    public AdminResponse updateUser(AdminUser user);
    
    public void deleteUser(Integer userId);
 
    public List<AdminUser> getUsers();
    
    public boolean resetPassword(AdminUser adminUser);
}