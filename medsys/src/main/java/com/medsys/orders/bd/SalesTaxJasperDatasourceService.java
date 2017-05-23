package com.medsys.orders.bd;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medsys.orders.model.Invoice;
import com.medsys.orders.model.SalesTax;
import com.medsys.ui.util.UIConstants;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class SalesTaxJasperDatasourceService {

	@Autowired
	private InvoiceBD invoiceBD;
	
	/**
	 * Returns a data source that's wrapped within {@link JRDataSource}
	 * @return
	 */
	public JRDataSource getSalesTaxData(Date fromDate,Date toDate) {
		List<Invoice> lstInvoices = invoiceBD.searchForInvoice(fromDate, toDate);
		Set<Invoice> invoiceSet = new HashSet<Invoice>();
		List<SalesTax> lstSalesTax = new ArrayList<SalesTax>();
		SalesTax salesTax = new SalesTax();
		salesTax.setFromDate(fromDate);
		salesTax.setToDate(toDate);
		salesTax.setTotalSalesTax(new BigDecimal(Integer.parseInt(UIConstants.TOTAL_ZERO.getValue())));
		for(Invoice invoice : lstInvoices){
			salesTax.setTotalSalesTax(salesTax.getTotalSalesTax().add(invoice.getTotalVat()));
			invoiceSet.add(invoice);
		}
		salesTax.setInvoices(invoiceSet);
		lstSalesTax.add(salesTax);
		// Return wrapped collection
		return new JRBeanCollectionDataSource(lstSalesTax);
	}
}