package com.medsys.ui.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.UIConstants;
import com.medsys.util.EpSystemError;

@Controller
@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.ERROR)
public class ErrorController  extends SuperController {
	static Logger logger = LoggerFactory.getLogger(ErrorController.class);

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_ERROR, method = {
			RequestMethod.POST, RequestMethod.GET })
	public String loadError(HttpServletRequest request, Model model) {
		model.addAttribute(UIConstants.MSG_FOR_SYSTEM_ERROR.getValue(),
				EpSystemError.SYSTEM_INTERNAL_ERROR.getErrorCode());
		//TODO reconsider
		return MedsysUITiles.MESSAGE.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_HTTP_ERROR, method = {
			RequestMethod.POST, RequestMethod.GET })
	public String loadHttpError(HttpServletRequest request, Model model,HttpServletResponse response) {
		logger.error("Encountered http error: " + response.getStatus() + ". " );
		model.addAttribute(UIConstants.MSG_FOR_SYSTEM_ERROR.getValue(),
				EpSystemError.HTTP_ERROR.getErrorCode());
		//TODO reconsider
		return MedsysUITiles.MESSAGE.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH
			+ UIActions.LOAD_HTTP_403_ERROR, method = { RequestMethod.POST,
			RequestMethod.GET })
	public String loadHttp403Error(Principal adminUser, Model model) {
		logger.error("Encountered http 403 error.");
		model.addAttribute(UIConstants.MSG_FOR_SYSTEM_ERROR.getValue(),
				EpSystemError.USER_ACCESS_DENIED.getErrorCode());
		if (adminUser != null) {
			model.addAttribute(UIConstants.MSG_FOR_USER_WITH_ARG.getValue(),
					adminUser.getName());
		} else {
			model.addAttribute(UIConstants.MSG_FOR_USER_WITH_ARG.getValue(),
					"");
		}
		return MedsysUITiles.MESSAGE.getTile();
	}

}