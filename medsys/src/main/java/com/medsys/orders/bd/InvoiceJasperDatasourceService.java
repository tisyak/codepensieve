package com.medsys.orders.bd;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medsys.orders.model.Invoice;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class InvoiceJasperDatasourceService {

	@Autowired
	private InvoiceBD invoiceBD;
	
	/**
	 * Returns a data source that's wrapped within {@link JRDataSource}
	 * @return
	 */
	public JRDataSource getInvoiceData(Integer invoiceId) {
		Invoice invoice = invoiceBD.getInvoice(invoiceId);
		List<Invoice> lstInvoice = new ArrayList<Invoice>();
		lstInvoice.add(invoice);
		// Return wrapped collection
		return new JRBeanCollectionDataSource(lstInvoice);
	}
}