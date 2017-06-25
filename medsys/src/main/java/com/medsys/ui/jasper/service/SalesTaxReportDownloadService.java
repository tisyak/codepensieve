package com.medsys.ui.jasper.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medsys.orders.bd.InvoiceBD;
import com.medsys.orders.model.Invoice;
import com.medsys.orders.model.SalesTax;
import com.medsys.ui.jasper.util.ExporterService;
import com.medsys.ui.jasper.util.TokenService;
import com.medsys.ui.util.UIConstants;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
// TODO: Change this to factory method
public class SalesTaxReportDownloadService {

	public static final String SALES_TAX_TEMPLATE = "/jasper/SalesTax.jrxml";
	static Logger logger = LoggerFactory.getLogger(SalesTaxReportDownloadService.class);

	@Autowired
	private ExporterService exporter;

	@Autowired
	private InvoiceBD invoiceBD;

	@Autowired
	private TokenService tokenService;

	public void download(String type, String token, Date fromDate, Date toDate, HttpServletResponse response) {

		try {
			// 1. Add report parameters
			HashMap<String, Object> params = new HashMap<String, Object>();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");
			params.put("Title", "Sales Tax Report FROM [" + simpleDateFormat.format(fromDate) + "] TO ["
					+ simpleDateFormat.format(toDate) + "]");
			logger.debug("Setting title for file.");
			// 2. Retrieve template
			InputStream reportStream = this.getClass().getResourceAsStream(SALES_TAX_TEMPLATE);
			logger.debug("Obtaining the .jrxml inputstream.");

			// 3. Convert template to JasperDesign
			JasperDesign jd = JRXmlLoader.load(reportStream);
			logger.debug("Convert template to JasperDesign.");

			// 4. Compile design to JasperReport
			JasperReport jr = JasperCompileManager.compileReport(jd);
			logger.debug("Compile design to JasperReport.");

			// 5. Create the JasperPrint object
			// Make sure to pass the JasperReport, report parameters, and data
			// source
			JasperPrint jp = JasperFillManager.fillReport(jr, params, getSalesTaxData(fromDate, toDate));
			logger.debug("Pass the JasperReport, report parameters, and data source tp JasperPrint Object.");

			// 6. Create an output byte stream where data will be written
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			logger.debug("Create an output byte stream where data will be written.");
			// 7. Export report
			exporter.export(type, jp, response, baos);
			logger.debug("Export report.");
			// 8. Write to reponse stream
			write(token, response, baos);

		} catch (JRException jre) {
			logger.error("Unable to process download");
			throw new RuntimeException(jre);
		}
	}

	/**
	 * Writes the report to the output stream
	 */
	private void write(String token, HttpServletResponse response, ByteArrayOutputStream baos) {

		try {
			logger.debug("Writing output: " + baos.size());

			// Retrieve output stream
			ServletOutputStream outputStream = response.getOutputStream();
			// Write to output stream
			baos.writeTo(outputStream);
			// Flush the stream
			outputStream.flush();

			// Remove download token
			tokenService.remove(token);

		} catch (Exception e) {
			logger.error("Unable to write report to the output stream");
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns a data source that's wrapped within {@link JRDataSource}
	 * 
	 * @return
	 */
	private JRDataSource getSalesTaxData(Date fromDate, Date toDate) {
		List<Invoice> lstInvoices = invoiceBD.searchForInvoice(fromDate, toDate);
		Set<Invoice> invoiceSetForVAT = new HashSet<Invoice>();
		Set<Invoice> invoiceSetForCGST = new HashSet<Invoice>();
		Set<Invoice> invoiceSetForSGST = new HashSet<Invoice>();
		List<SalesTax> lstSalesTax = new ArrayList<SalesTax>();
		SalesTax salesTax = new SalesTax();
		salesTax.setFromDate(fromDate);
		salesTax.setToDate(toDate);
		salesTax.setTotalVATTax(new BigDecimal(Integer.parseInt(UIConstants.TOTAL_ZERO.getValue())));
		salesTax.setTotalCGSTTax(new BigDecimal(Integer.parseInt(UIConstants.TOTAL_ZERO.getValue())));
		salesTax.setTotalSGSTTax(new BigDecimal(Integer.parseInt(UIConstants.TOTAL_ZERO.getValue())));
		for (Invoice invoice : lstInvoices) {
			if (invoice.getTotalCgst() != null) {
				salesTax.setTotalCGSTTax(salesTax.getTotalCGSTTax().add(invoice.getTotalCgst()));
				invoiceSetForCGST.add(invoice);
			}
			
			if (invoice.getTotalSgst() != null) {
				salesTax.setTotalSGSTTax(salesTax.getTotalSGSTTax().add(invoice.getTotalSgst()));
				invoiceSetForSGST.add(invoice);
			}
			
			if (invoice.getTotalVat() != null) {
				salesTax.setTotalVATTax(salesTax.getTotalVATTax().add(invoice.getTotalVat()));
				invoiceSetForVAT.add(invoice);
			}

		}
		salesTax.setInvoicesHavingCGST(invoiceSetForCGST);
		salesTax.setInvoicesHavingSGST(invoiceSetForSGST);
		salesTax.setInvoicesHavingVAT(invoiceSetForVAT);
		lstSalesTax.add(salesTax);
		// Return wrapped collection
		return new JRBeanCollectionDataSource(lstSalesTax);
	}
}