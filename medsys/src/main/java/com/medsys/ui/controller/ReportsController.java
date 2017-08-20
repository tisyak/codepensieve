package com.medsys.ui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medsys.adminuser.model.Roles;
import com.medsys.orders.model.SalesTax;
import com.medsys.product.model.ProductDeficit;
import com.medsys.ui.jasper.service.ProductDeficitReportDownloadService;
import com.medsys.ui.jasper.service.SalesTaxReportDownloadService;
import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;

@Controller
@Secured(Roles.MASTER_ADMIN)
public class ReportsController extends SuperController {
	static Logger logger = LoggerFactory.getLogger(ReportsController.class);

	@Autowired
	private SalesTaxReportDownloadService salesTaxReportDownloadService;
	
	@Autowired
	private ProductDeficitReportDownloadService productDeficitReportDownloadService;

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_REQUEST_FOR_SALES_TAX_REPORT)
	public String loadSalesTaxReportPage(Model model) {
		model.addAttribute("salesTax", new SalesTax());
		return MedsysUITiles.SALES_TAX_REPORT.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.VIEW_SALES_TAX)
	public void downloadSalesTaxReport(@RequestParam String type, @RequestParam String token,
			@RequestParam Date fromDate, @RequestParam Date toDate, HttpServletResponse response) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");
		logger.debug("Requesting month wise sales tax report download of type: " + type + " with token: " + token
				+ " FROM [" + simpleDateFormat.format(fromDate) + "] TO [" + simpleDateFormat.format(toDate) + "]");
		salesTaxReportDownloadService.download(type, token, fromDate, toDate, response);
	}
	
	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LOAD_REQUEST_FOR_PRODUCT_DEFICIT_REPORT)
	public String loadProductDeficitReportPage(Model model) {
		model.addAttribute("productDeficit", new ProductDeficit());
		return MedsysUITiles.PRODUCT_DEFICIT_REPORT.getTile();
	}

	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.VIEW_PRODUCT_DEFICIT)
	public void downloadProductDeficitReport(@RequestParam String type, @RequestParam String token, HttpServletResponse response) {
		logger.debug("Requesting ProductDeficitReport download of type: " + type);
		productDeficitReportDownloadService.download(type, token, response);
	}
}