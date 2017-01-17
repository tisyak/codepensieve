package com.medsys.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.medsys.adminuser.bd.AdminUserBD;
import com.medsys.adminuser.model.Roles;
import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
import com.medsys.util.EmailService;

@Controller
public class DashboardController extends SuperController {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(DashboardController.class);

	@Autowired
	AdminUserBD adminUserBD;
	
	@Autowired
	EmailService emailService;

	@RequestMapping(value = UIActions.FORWARD_SLASH
			+ UIActions.LOAD_DEFAULT_DASHBOARD, method = { RequestMethod.GET })
	public String loadDefaultDashboard(HttpServletRequest request, Model model) {
		logger.debug("displaying default dashboard.");

		return MedsysUITiles.DEFAULT_HOME.getTile();
	}

	@Secured(Roles.MASTER_ADMIN)
	@RequestMapping(value = UIActions.FORWARD_SLASH
			+ UIActions.LOAD_ADMIN_DASHBOARD, method = { RequestMethod.GET })
	public String loadMasterAdminDashboard(HttpServletRequest request,
			Model model) {
		logger.debug("displaying master admin dashboard.");

		//TODO: Correct Dashboard
		int masterAdminCount = 0;
		//int dscCount = clientDSCInfoBD.getCountAllClientDSCs();
		int spAdminCount = 0;
		
		//int monthlyInwardCertificatesCount = clientDSCInfoBD.getCountForCurrentMonthInwardDSCs();
		//int monthlyOutwardCertificatesCount = clientDSCInfoBD.getCountForCurrentMonthOutwardDSCs();
		
		//int thirdPartyCount = thirdPartyBD.getCountOfAllThirdParties();

		request.setAttribute("totalDSCCount", 0);
		request.setAttribute("thirdPartyCount", 0);
		request.setAttribute("certExpiringClientCount", 0);
		request.setAttribute("monthlyInwardCertificatesCount", 0);
		request.setAttribute("monthlyOutwardCertificatesCount", 0);

		return MedsysUITiles.MASTER_ADMIN_DASHBOARD.getTile();
	}

}
