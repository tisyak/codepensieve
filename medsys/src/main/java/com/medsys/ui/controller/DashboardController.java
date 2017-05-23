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
		//TODO: Change this dashboard to reflect relevant information.
		/*totalOrdersInMonth
		totalOrdersInWeek
		totalInvoiceAmountInMonth
		totalInvoicesInMonth
		countOfProductsInDeficit
		countOfPOsInMonth
		countOfCustomersBilledInMonth
		orderCountForYear
		salesInYear
		invoiceCountForYear
		VATForYear
		countOfCustomersBilledThisYear*/
		request.setAttribute("totalOrdersInMonth", 0);
		request.setAttribute("totalOrdersInWeek", 0);
		request.setAttribute("totalInvoiceAmountInMonth", 0);
		request.setAttribute("totalInvoicesInMonth", 0);
		request.setAttribute("countOfProductsInDeficit", 0);
		request.setAttribute("countOfPOsInMonth", 0);
		request.setAttribute("countOfCustomersBilledInMonth", 0);
		request.setAttribute("orderCountForYear", 0);
		request.setAttribute("salesInYear", 0);
		request.setAttribute("invoiceCountForYear", 0);
		request.setAttribute("VATForYear", 0);
		request.setAttribute("countOfCustomersBilledThisYear", 0);

		return MedsysUITiles.MASTER_ADMIN_DASHBOARD.getTile();
	}

}
