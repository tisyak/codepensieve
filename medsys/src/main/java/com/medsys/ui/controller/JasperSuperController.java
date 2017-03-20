package com.medsys.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medsys.adminuser.model.Roles;
import com.medsys.common.model.ReportsResponse;
import com.medsys.ui.jasper.util.TokenService;
import com.medsys.ui.util.UIActions;

@Controller
@Secured(Roles.MASTER_ADMIN)
public class JasperSuperController extends SuperController {
	
	static Logger logger = LoggerFactory.getLogger(JasperSuperController.class);
	
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.GET_PROGRESS)
	public @ResponseBody ReportsResponse checkDownloadProgress(@RequestParam String token) {
		String tokenCheck = tokenService.check(token);
		logger.debug("returning tokenCheck: " + tokenCheck);
		return new ReportsResponse(true, tokenCheck);
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.GET_TOKEN)
	public @ResponseBody ReportsResponse getDownloadToken() {
		String token = tokenService.generate();
		logger.debug("returning generated token: " + token);
		return new ReportsResponse(true, token);
	}


}
