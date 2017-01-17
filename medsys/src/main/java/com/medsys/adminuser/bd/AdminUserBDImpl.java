package com.medsys.adminuser.bd;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.adminuser.dao.AdminUserDAO;
import com.medsys.adminuser.model.AdminResponse;
import com.medsys.adminuser.model.AdminUser;
import com.medsys.common.model.Response;
import com.medsys.master.bd.ConfigParaBD;
//import com.medsys.user.model.User;
//import com.medsys.user.model.UserResponse;
import com.medsys.util.ConvertSHA2;
import com.medsys.util.EpSystemError;
 
@Service
@Transactional
public class AdminUserBDImpl implements AdminUserBD {
    static Logger logger = LoggerFactory.getLogger(AdminUserBDImpl.class);
     
    @Autowired
    private AdminUserDAO userDAO;

    
    @Autowired
    ConfigParaBD masterConfigBD;
 
    @Override
    public AdminResponse addUser(AdminUser user) {
       return userDAO.addUser(user);
    }
 
    @Override
    public AdminUser getUser(Integer userId) {
        return userDAO.getUser(userId);
    }
 
    @Override
    public AdminUser getUserByUserName(String username)  {
        return userDAO.getUserByUserName(username);
    }
 
    @Override
    public AdminResponse updateUser(AdminUser user)  {
        return userDAO.updateUser(user);
    }
 
    @Override
    public void deleteUser(Integer userId)  {
        userDAO.deleteUser(userId);
    }
 
    @Override
    public List<AdminUser> getUsers() {
        return userDAO.getUsers();
    }

    @Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		try {
			logger.debug("Fetching user details.");
            return getUserByUserName(username);
        } catch (EmptyResultDataAccessException e) {
        	logger.debug(e.getMessage());
        	return null;
        }
	}

	
	
	@Override
	public boolean resetPassword(AdminUser adminUser) {
		//TODO:Implement this method
		return false;
	}


	@Override
	public AdminResponse authenticateUser(AdminUser user) {
		Response response;
	       
		AdminResponse adminResponse = new AdminResponse();
        AdminUser checkUser = null;
        try {
            ConvertSHA2 convertSHA2 = new ConvertSHA2();
            String enPassword = convertSHA2.convertStringToSHA2(user
                    .getPassword());
            user.setPassword(enPassword);
            if (user.getUsername() != null) {
                logger.debug("Get user by user name");
                checkUser = userDAO.getUserByUserName(user.getUsername());
            }
           
            else {
                logger.error("Returning error to use any one either user name");
                adminResponse.setResponse(new Response(false,
                        EpSystemError.NO_RECORD_FOUND));
                return adminResponse;
            }
            logger.debug("Fetched user by user name, getting user from user response.");
           

        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            response = new Response(false, EpSystemError.SYSTEM_INTERNAL_ERROR);
            adminResponse = new AdminResponse(checkUser, response);
            return adminResponse;
        }

       if (checkUser!=null) {
            logger.debug("Check user's account status");
           
                logger.debug("User's account status is active");
                if (checkUser.getPassword().equals(user.getPassword())) {
                    
                //	adminResponse = processAuthenticateSuccess(checkUser);
                 //   response = userResponse.getResponse();
                	adminResponse = new AdminResponse(checkUser, new Response(true, null));

                } else {
                    
                	//adminResponse = processAuthenticateFailure(checkUser);
                    //response = adminResponse.getResponse();
                	adminResponse = new AdminResponse(checkUser, new Response(false, EpSystemError.INVALID_USER));
                }
           
                //adminResponse.setResponse(response);
        }
        return adminResponse;
	}

	
   
}