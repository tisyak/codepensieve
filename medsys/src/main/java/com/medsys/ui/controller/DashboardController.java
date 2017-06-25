package com.medsys.ui.controller;

import java.math.BigDecimal;

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
import com.medsys.orders.bd.InvoiceBD;
import com.medsys.orders.bd.OrderBD;
import com.medsys.product.bd.ProductInvBD;
import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.UIConstants;
import com.medsys.util.EmailService;

@Controller
public class DashboardController extends SuperController {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	AdminUserBD adminUserBD;

	@Autowired
	OrderBD orderBD;

	@Autowired
	InvoiceBD invoiceBD;

	@Autowired
	ProductInvBD productInvBD;

	@Autowired
	EmailService emailService;

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_DEFAULT_DASHBOARD, method = { RequestMethod.GET })
	public String loadDefaultDashboard(HttpServletRequest request, Model model) {
		logger.debug("displaying default dashboard.");

		return MedsysUITiles.DEFAULT_HOME.getTile();
	}

	@Secured(Roles.MASTER_ADMIN)
	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_ADMIN_DASHBOARD, method = { RequestMethod.GET })
	public String loadMasterAdminDashboard(HttpServletRequest request, Model model) {
		logger.debug("displaying master admin dashboard.");
		// TODO: Change this dashboard to reflect relevant information.
		int totalOrdersInMonth = 0, totalOrdersInWeek = 0, totalInvoicesInMonth = 0, countOfProductsInDeficit = 0,
				countOfPOsInMonth = 0, countOfCustomersBilledInMonth = 0, orderCountForYear = 0,
				invoiceCountForYear = 0, countOfCustomersBilledThisYear = 0;
		BigDecimal totalInvoiceAmountInMonth = null, salesInYear = null, VATForYear = null;

		try {
			totalOrdersInMonth = orderBD.getCountOfTotalOrdersInMonth();
			totalOrdersInWeek = orderBD.getCountOfTotalOrdersInMonth();
			totalInvoiceAmountInMonth = invoiceBD.getTotalSalesAmountInMonth();
			totalInvoicesInMonth = invoiceBD.getCountOfTotalInvoicesInMonth();
			countOfProductsInDeficit = productInvBD.getCountOfProductsInDeficit();
			countOfPOsInMonth = 0;
			countOfCustomersBilledInMonth = invoiceBD.getCountOfCustomerBilledInMonth();
			orderCountForYear = orderBD.getCountOfTotalOrdersForYear();
			salesInYear = invoiceBD.getTotalSalesAmountInYear();
			invoiceCountForYear = invoiceBD.getCountOfTotalInvoicesForYear();
			VATForYear = invoiceBD.getTotalVATInYear();
			countOfCustomersBilledThisYear = invoiceBD.getCountOfCustomerBilledForYear();
		} catch (Exception e) {
			logger.debug("Exception in summary: " + e.getMessage());
			model.addAttribute(UIConstants.MSG_FOR_USER_ERROR.getValue(), e.getMessage());
		}

		request.setAttribute("totalOrdersInMonth", totalOrdersInMonth);
		// TODO Change this to per week count
		request.setAttribute("totalOrdersInWeek", totalOrdersInWeek);
		request.setAttribute("totalInvoiceAmountInMonth", totalInvoiceAmountInMonth);
		request.setAttribute("totalInvoicesInMonth", totalInvoicesInMonth);
		request.setAttribute("countOfProductsInDeficit", countOfProductsInDeficit);
		request.setAttribute("countOfPOsInMonth",countOfPOsInMonth);
		request.setAttribute("countOfCustomersBilledInMonth", countOfCustomersBilledInMonth);
		request.setAttribute("orderCountForYear", orderCountForYear);
		request.setAttribute("salesInYear", salesInYear);
		request.setAttribute("invoiceCountForYear", invoiceCountForYear);
		request.setAttribute("VATForYear", VATForYear);
		request.setAttribute("countOfCustomersBilledThisYear", countOfCustomersBilledThisYear);

		return MedsysUITiles.MASTER_ADMIN_DASHBOARD.getTile();
	}

}
