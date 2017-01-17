package com.medsys.ui.controller;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medsys.adminuser.bd.AdminUserBD;
import com.medsys.adminuser.model.AdminUser;
import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
 

 
@Controller

//@PreAuthorize("denyAll")
public class AdminUserController  extends SuperController  {
    static Logger logger = LoggerFactory.getLogger(AdminUserController.class);
 
    @Autowired
    private AdminUserBD adminUserBD;
 
    @Autowired
    private MessageSource messageSource;
 
    @RequestMapping(value = {UIActions.FORWARD_SLASH + UIActions.LIST_ADMIN_USERS}, method = {RequestMethod.GET, RequestMethod.POST})
    //@PreAuthorize("hasRole('LIST_ADMIN_USERS')")
    public String listOfAdminUsers(Model model) {
        logger.info("IN: Adminuser/list-GET/POST");
 
        List<AdminUser> adminUsers = adminUserBD.getUsers();
        model.addAttribute("adminUsers", adminUsers);
 
        // if there was an error in /add, we do not want to overwrite
        // the existing adminUser object containing the errors.
        if (!model.containsAttribute("adminUsers")) {
            logger.info("Adding AdminUser object to model");
            AdminUser adminuser = new AdminUser();
            model.addAttribute("adminuser", adminuser);
        }
        return MedsysUITiles.ADMIN_USERS_LIST.getTile();
    }              
     
    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    //@PreAuthorize("hasPermission('ADD_ADMIN_USER')")
    public String addAdminUser(@Valid @ModelAttribute AdminUser adminUser,
            BindingResult result, RedirectAttributes redirectAttrs) {
 
        logger.info("IN: AdminUser/add-POST");
 
        if (result.hasErrors()) {
            logger.info("adminUser-add error: " + result.toString());
            redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.adminuser", result);
            redirectAttrs.addFlashAttribute("adminUser", adminUser);
            return UIActions.REDIRECT + UIActions.LIST_ADMIN_USERS;
        } else {
        	adminUserBD.addUser(adminUser);
            String message = "Admin User " + adminUser.getUsername() + " was successfully added";
            redirectAttrs.addFlashAttribute("message", message);
            return UIActions.REDIRECT + UIActions.LIST_ADMIN_USERS;
        }
    }
 
    @RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
    //@PreAuthorize("hasPermission('EDIT_ADMIN_USER')")
    public String loadEditAdminUserPage(@RequestParam(value = "username", required = true) 
            String username, Model model) {
         
        logger.info("IN: Adminuser/edit-GET:  username to query = " + username);
 
        if (!model.containsAttribute("adminuser")) {
            logger.info("Adding adminuser object to model");
            AdminUser adminuser = adminUserBD.getUserByUserName(username);
            logger.info("Adminuser/edit-GET:  " + adminuser.toString());
            model.addAttribute("adminuser", adminuser);
        }
 
        return "adminuser-edit";
    }
         
    @RequestMapping(value = "/admin/save", method = RequestMethod.POST)
   // @PreAuthorize("hasPermission('EDIT_ADMIN_USER')")
    public String saveAdminUser(@Valid @ModelAttribute AdminUser adminuser,
            BindingResult result, RedirectAttributes redirectAttrs,
            @RequestParam(value = "action", required = true) String action) {
 
        logger.info("IN: adminuser/save-POST: " + action);
 
        if (action.equals(messageSource.getMessage("button.action.cancel", null, Locale.US))) {
            String message = "Admin User " + adminuser.getUsername() + " edit cancelled";
            redirectAttrs.addFlashAttribute("message", message);
        } else if (result.hasErrors()) {
            logger.info("Adminuser-edit error: " + result.toString());
            redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.adminuser", result);
            redirectAttrs.addFlashAttribute("adminuser", adminuser);
            return "redirect:/adminuser/edit?username=" + adminuser.getUsername();
        } else if (action.equals(messageSource.getMessage("button.action.save",  null, Locale.US))) {
            logger.info("Adminuser/edit-POST:  " + adminuser.toString());
            adminUserBD.updateUser(adminuser);
            String message = "Adminuser " + adminuser.getUsername() + " was successfully edited";
            redirectAttrs.addFlashAttribute("message", message);
        }
 
        return UIActions.REDIRECT + UIActions.LIST_ADMIN_USERS;
    }
 
    @RequestMapping(value = "/admin/delete", method = RequestMethod.GET)
   // @PreAuthorize("hasPermission('DELETE_ADMIN_USER')")
    public String deleteAdminUserPage(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "phase", required = true) String phase,
            Model model) {
 
    	AdminUser adminuser = adminUserBD.getUserByUserName(username);
        logger.info("IN: Adminuser/delete-GET | username = " + username + " | phase = " + phase + " | " + adminuser.toString());
 
        if (phase.equals(messageSource.getMessage("button.action.cancel", null, Locale.US))) {
            String message = "Adminuser delete was cancelled.";
            model.addAttribute("message", message);
            return UIActions.REDIRECT + UIActions.LIST_ADMIN_USERS;
        } else if (phase.equals(messageSource.getMessage("button.action.stage", null, Locale.US))) {
            String message = "Adminuser " + adminuser.getUsername() + " queued for display.";
            model.addAttribute("adminuser", adminuser);
            model.addAttribute("message", message);
            return "adminuser-delete";
        } else if (phase.equals(messageSource.getMessage("button.action.delete", null, Locale.US))) {
        	adminUserBD.deleteUser(adminuser.getId());
            String message = "Adminuser " + adminuser.getUsername() + " was successfully deleted";
            model.addAttribute("message", message);
            return UIActions.REDIRECT + UIActions.LIST_ADMIN_USERS;
        }
 
        return UIActions.REDIRECT + UIActions.LIST_ADMIN_USERS;
    }
}