package com.medsys.ui.jasper.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.orders.bd.InvoiceBD;
import com.medsys.orders.model.Invoice;
import com.medsys.ui.jasper.util.ExporterService;
import com.medsys.ui.jasper.util.TokenService;

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
public class InvoiceReportDownloadService {

	public static final String INVOICE_TEMPLATE = "/jasper/Invoice.jrxml";
	public static final String INVOICE_GST_TEMPLATE = "/jasper/InvoiceGST.jrxml";
	static Logger logger = LoggerFactory.getLogger(InvoiceReportDownloadService.class);

	@Autowired
	private ExporterService exporter;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private InvoiceBD invoiceBD;

	@Transactional
	public void download(String type, String token, Integer invoiceId, String billVersion,
			HttpServletResponse response) {

		try {
			// 1. Add report parameters
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("Title", "Invoice");
			logger.debug("Setting title for file.");

			Invoice invoice = invoiceBD.getInvoice(invoiceId);
			logger.debug("Setting billVersion as " + billVersion);
			invoice.setBillVersion(billVersion);
			// 2. Retrieve template
			InputStream reportStream = this.getClass().getResourceAsStream(INVOICE_GST_TEMPLATE);
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
			JasperPrint jp = JasperFillManager.fillReport(jr, params, getInvoiceData(invoice));
			logger.debug("Pass the JasperReport, report parameters, and data source tp JasperPrint Object." + invoice);

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
	private JRDataSource getInvoiceData(Invoice invoice) {

		List<Invoice> lstInvoice = new ArrayList<Invoice>();
		lstInvoice.add(invoice);
		// Return wrapped collection
		return new JRBeanCollectionDataSource(lstInvoice);
	}
}