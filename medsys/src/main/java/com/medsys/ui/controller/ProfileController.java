package com.medsys.ui.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medsys.adminuser.bd.AdminUserBD;
import com.medsys.adminuser.model.AdminUser;
import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.UIConstants;

@Controller
public class ProfileController extends SuperController {

	static Logger logger = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	private AdminUserBD adminUserBD;

	@RequestMapping(value = "/profile/view", method = RequestMethod.GET)
	public String loadViewProfilePage(Model model) {

		AdminUser principal = (AdminUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		logger.info("IN: Profile/view-GET:  username to query = " + principal.getUsername());

		if (!model.containsAttribute("adminuser")) {
			logger.info("Adding adminuser object to model");
			AdminUser adminuser = adminUserBD.getUserByUserName(principal.getUsername());
			logger.info("Profile/view-GET:  " + adminuser.toString());
			model.addAttribute("adminuser", adminuser);
		}

		return MedsysUITiles.VIEW_PROFILE.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.EDIT_PROFILE, method = RequestMethod.GET)
	public String loadEditProfilePage(Model model) {

		AdminUser principal = (AdminUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info("IN: Profile/edit-GET:  username to query = " + principal.getUsername());
		
		if (!model.containsAttribute("adminuser")) {
			logger.info("Adding adminuser object to model");
			AdminUser adminuser = adminUserBD.getUserByUserName(principal.getUsername());
			logger.info("Profile/edit-GET:  " + adminuser.toString());
			model.addAttribute("adminuser", adminuser);
		}

		return MedsysUITiles.EDIT_PROFILE.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.SAVE_PROFILE, method = RequestMethod.POST)
	public String saveProfile(@Valid @ModelAttribute AdminUser adminUser, BindingResult result,
			RedirectAttributes redirectAttrs) {

		logger.info("IN: Profile/save-POST: " + adminUser);

		if (result.hasErrors()) {
			logger.info("Profile-save error: " + result.toString());
			redirectAttrs.addFlashAttribute(UIConstants.MSG_FOR_USER_ERROR.getValue(),  result.getAllErrors());
			redirectAttrs.addFlashAttribute("adminUser", adminUser);
			return UIActions.REDIRECT + UIActions.EDIT_PROFILE ;
		} else {
			logger.info("Profile/save-POST:  " + adminUser);
			adminUserBD.updateUser(adminUser);
			String message = "adminUser " + adminUser.getName() + " was successfully edited";
			redirectAttrs.addFlashAttribute(UIConstants.MSG_FOR_USER.getValue(), message);
		}

		return UIActions.REDIRECT + UIActions.VIEW_PROFILE;
	}

}
