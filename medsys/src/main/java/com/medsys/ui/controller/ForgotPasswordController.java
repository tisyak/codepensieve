package com.medsys.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.UIConstants;
import com.medsys.util.EpMessage;

@Controller
@RequestMapping(value = { UIActions.FORWARD_SLASH + UIActions.FORGOT_PASSWORD })
public class ForgotPasswordController extends SuperController {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(ForgotPasswordController.class);

	@RequestMapping(value = { UIActions.FORWARD_SLASH }, method = RequestMethod.GET)
	public String forgotPassword(Model model) {

		logger.debug("Forgot Password request - directing to Options load");
		return UIActions.FORWARD + UIActions.FORGOT_PASSWORD
				+ UIActions.FORWARD_SLASH
				+ UIActions.LOAD_FORGOT_PASSWORD_OPTIONS;

	}

	@RequestMapping(value = { UIActions.FORWARD_SLASH
			+ UIActions.LOAD_FORGOT_PASSWORD_OPTIONS }, method = RequestMethod.GET)
	public String loadForgotPasswordOptions(Model model) {

		logger.debug("loading forgot password options");
		return MedsysUITiles.FORGOT_PASSWORD_OPTIONS.getTile();

	}

	@RequestMapping(value = { UIActions.FORWARD_SLASH
			+ UIActions.VALIDATE_FORGOT_PASSWORD_OPTIONS }, method = RequestMethod.POST)
	public String validateForgotPasswordOptions(Model model) {

		logger.debug("validating forgot password options");
		model.addAttribute(UIConstants.MSG_FOR_USER.getValue(),
				EpMessage.MSG_FOR_SUCCESSFUL_USER_VALIDATION.getMsgCode());
		return MedsysUITiles.LOGIN.getTile();

	}

}
