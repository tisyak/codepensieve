package com.medsys.ui.jasper.service;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medsys.orders.model.OrderProductSet;
import com.medsys.orders.model.Orders;
import com.medsys.product.bd.SetBD;
import com.medsys.product.model.Set;
import com.medsys.product.model.SetPdtTemplate;
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
public class SetReportDownloadService {

	public static final String CHALLAN_TEMPLATE = "/jasper/Challan.jrxml";
	public static final String CHALLAN_INSTR_TEMPLATE = "/jasper/ChallanInstrSet.jrxml";
	public static final String SET_WISE_PRICELIST_TEMPLATE = "/jasper/SetwisePricelist.jrxml";
	static Logger logger = LoggerFactory.getLogger(SetReportDownloadService.class);


	@Autowired
	private SetBD setBD;
	
	@Autowired
	private ExporterService exporter;
	
	@Autowired
	private TokenService tokenService;
	
	public void download(String type, String token, 
			Integer setId,String challanKind, 
			String extraParam, HttpServletResponse response) {
		 
		try {
			// 1. Add report parameters
			HashMap<String, Object> params = new HashMap<String, Object>(); 
			params.put("Title", "Delivery Challan");
			logger.debug("Setting title for file.");
			// 2.  Retrieve template
			InputStream reportStream = this.getClass().getResourceAsStream(CHALLAN_TEMPLATE); 
			logger.debug("Obtaining the .jrxml inputstream.");
			
			if (challanKind.equals("instr")) {
				logger.debug("Switching to Instr Template.");
				params.put("Title", "Instrument Challan");
				reportStream = this.getClass().getResourceAsStream(CHALLAN_INSTR_TEMPLATE);
			}
			
			if (challanKind.equals("pricelist") || challanKind.equals("quotation")) {
				params.put("Title", "Pricelist");
				logger.debug("Switching to pricelist / quotation Template.");
				reportStream = this.getClass().getResourceAsStream(SET_WISE_PRICELIST_TEMPLATE);
			}
			 
			// 3. Convert template to JasperDesign
			JasperDesign jd = JRXmlLoader.load(reportStream);
			logger.debug("Convert template to JasperDesign.");
			 
			// 4. Compile design to JasperReport
			JasperReport jr = JasperCompileManager.compileReport(jd);
			logger.debug("Compile design to JasperReport.");
			
			JRDataSource ds = null;
			
			if (challanKind.equals("instr")) {
				ds = getBlankOrderInstrData(setId);
			} else if (challanKind.equals("pricelist")) {
				ds = getPricelist(setId,extraParam);
			} else if (challanKind.equals("quotation")) {
				ds = getQuotation(extraParam);
			} else {
				ds = getBlankOrderData(setId);
			}
			 
			// 5. Create the JasperPrint object
			// Make sure to pass the JasperReport, report parameters, and data source
			JasperPrint jp = JasperFillManager.fillReport(jr, params,ds);
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
	private void write(String token, HttpServletResponse response,
			ByteArrayOutputStream baos) {
		 
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
	 * @return
	 */
	private JRDataSource getBlankOrderData(Integer setId) {
		Orders order = new Orders();
		Set set = setBD.getSetWithProducts(setId);
		order.setSet(set);
		
		SortedSet<OrderProductSet> productSets = new ConcurrentSkipListSet<>();
		for(SetPdtTemplate  pdtTemplate : set.getPdtTemplates()){
			OrderProductSet orderProductSet =  new OrderProductSet();
			BeanUtils.copyProperties(pdtTemplate, orderProductSet);
			productSets.add(orderProductSet);
		}
		order.setProducts(productSets);
		
		List<Orders> lstOrder = new ArrayList<Orders>();
		lstOrder.add(order);
		// Return wrapped collection
		return new JRBeanCollectionDataSource(lstOrder);
	}
	
	/**
	 * Returns a data source that's wrapped within {@link JRDataSource}
	 * @return
	 */
	private JRDataSource getBlankOrderInstrData(Integer setId) {
		Orders order = new Orders();
		Set set = setBD.getSetWithInstr(setId);
		order.setSet(set);		
		List<Orders> lstOrder = new ArrayList<Orders>();
		lstOrder.add(order);
		// Return wrapped collection
		return new JRBeanCollectionDataSource(lstOrder);
	}
	

	private JRDataSource getPricelist(Integer setId,String pricelistPercent) {
		Set set = setBD.getSetWithAlteredPricelist(setId,Integer.parseInt(pricelistPercent));
		List<Set> lstSet = new ArrayList<Set>();
		lstSet.add(set);
		// Return wrapped collection
		return new JRBeanCollectionDataSource(lstSet);
	}
	
	private JRDataSource getQuotation(String pricelistPercent) {
		List<Set> lstSet = setBD.getQuotation(Integer.parseInt(pricelistPercent));
		// Return wrapped collection
		return new JRBeanCollectionDataSource(lstSet);
	}

}