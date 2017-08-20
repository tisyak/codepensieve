package com.medsys.orders.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TemporalType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.common.model.Response;
import com.medsys.master.bd.MasterDataBD;
import com.medsys.master.model.SeedMaster;
import com.medsys.master.model.SeedMasterKey;
import com.medsys.orders.bd.InvoiceNoGenerator;
import com.medsys.orders.model.Invoice;
import com.medsys.orders.model.InvoiceProduct;
import com.medsys.util.CalendarUtility;
import com.medsys.util.EpSystemError;

@Repository
public class InvoiceDAOImpl implements InvoiceDAO {

	static Logger logger = LoggerFactory.getLogger(InvoiceDAOImpl.class);

	@Autowired
	private MasterDataBD masterDataBD;

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	@Transactional
	public Response addInvoice(Invoice invoice) {
		logger.debug("Saving invoice to DB.");
		try {
			SeedMaster lastInvoiceNo = (SeedMaster) masterDataBD.getbyCode(SeedMaster.class,
					SeedMasterKey.LATEST_INVOICE_NO);
			String nextInvoiceNo = InvoiceNoGenerator.getNextInvoiceNumber(lastInvoiceNo.getSeedValue());
			invoice.setInvoiceNo(nextInvoiceNo);
			getCurrentSession().save(invoice);
			lastInvoiceNo.setSeedValue(nextInvoiceNo);
			masterDataBD.update(SeedMaster.class, lastInvoiceNo);
		} catch (Exception he) {
			logger.debug("Unable to save object. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Saved invoice: " + invoice);
		return new Response(true, null);
	}

	@Override
	public Invoice getInvoice(Integer invoiceId) {
		logger.debug("InvoiceDAOImpl.getInvoice() - [" + invoiceId + "]");
		Query<Invoice> query = getCurrentSession().createQuery("from Invoice inv LEFT JOIN FETCH inv.products where inv.invoiceId = " + invoiceId + "",Invoice.class);
		// query.setParameter("invoiceId", invoiceId.toString());

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No invoice found.");
			throw new EmptyResultDataAccessException("Invoice [" + invoiceId + "] not found", 1);
		} else {

			logger.debug("Invoice List Size: " + query.getResultList().size());
			List<Invoice> list = (List<Invoice>) query.getResultList();
			Invoice invoice = (Invoice) list.get(0);
			return invoice;
		}
	}

	@Override
	public Response deleteInvoice(Integer invoiceId) {
		try {
			Invoice invoice = getInvoice(invoiceId);
			if (invoice != null) {
				getCurrentSession().delete(invoice);
				logger.info("Delete of invoice with invoiceId: " + invoiceId + " successful");
				return new Response(true, null);
			} else {
				throw new EmptyResultDataAccessException("Invoice [" + invoiceId + "] not found", 1);
			}
		} catch (HibernateException he) {
			logger.error("Delete of invoice with invoiceId: " + invoiceId + " failed.");
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
	}

	@Override
	public List<Invoice> getAllInvoice() {
		
		
		return getCurrentSession().createQuery(" from Invoice order by invoiceDate,invoiceNo desc",Invoice.class)
				.getResultList();
	}

	@Override
	public Invoice updateInvoice(Invoice invoice) {
		// TODO: Conditional based on InvoiceStatus to be included
		Invoice invoiceToUpdate = getInvoice(invoice.getInvoiceId());
		invoiceToUpdate.setCustomer(invoice.getCustomer());
		invoiceToUpdate.setInvoiceDate(invoice.getInvoiceDate());
		invoiceToUpdate.setPaymentTerms(invoice.getPaymentTerms());
		invoiceToUpdate.setPatientName(invoice.getPatientName());
		invoiceToUpdate.setPatientInfo(invoice.getPatientInfo());
		invoiceToUpdate.setBillToPatient(invoice.getBillToPatient());
		invoiceToUpdate.setPrintMRP(invoice.getPrintMRP());
		invoiceToUpdate.setRefSource(invoice.getRefSource());
		invoiceToUpdate.setUpdateBy(invoice.getUpdateBy());
		invoiceToUpdate.setUpdateTimestamp(invoice.getUpdateTimestamp());
		getCurrentSession().update(invoiceToUpdate);
		return invoiceToUpdate;
	}

	@Override
	public List<Invoice> searchForInvoice(Invoice invoice) {
		logger.debug("InvoiceDAOImpl.searchForInvoice() - [" + invoice.toString() + "]");
		Query<Invoice> query = getCurrentSession().createQuery(
				" from Invoice where lower(invoiceNo) like :invoiceNo OR lower(customer.name) like :custName "
						+ " OR invoiceDate = :invoiceDate " + " order by invoiceNo asc",
				Invoice.class);

		if (invoice.getInvoiceNo() != null) {
			query.setParameter("invoiceNo", "%" + invoice.getInvoiceNo().toLowerCase() + "%");
		} else {
			query.setParameter("invoiceNo", invoice.getInvoiceNo());
		}

		if (invoice.getInvoiceDate() != null) {
			query.setParameter("invoiceDate", invoice.getInvoiceDate(), TemporalType.DATE);
		} else {
			query.setParameter("invoiceDate", null);
		}

		if (invoice.getCustomer() != null && invoice.getCustomer().getName() != null) {
			query.setParameter("custName", "%" + invoice.getCustomer().getName().toLowerCase() + "%");
		} else {
			query.setParameter("custName", null);
		}

		logger.debug(query.toString());

		if (query.getResultList().size() == 0) {
			logger.debug("No invoices found matching current search criteria.");
			return null;

		} else {

			logger.debug("Search Invoice List Size: " + query.getResultList().size());
			List<Invoice> list = (List<Invoice>) query.getResultList();
			return list;
		}
	}

	@Override
	public List<Invoice> searchForInvoice(Date fromDate, Date toDate) {
		logger.debug("InvoiceDAOImpl.searchForInvoice() - [" + fromDate + "]" + " to [" + toDate + "]");
		Query<Invoice> query = getCurrentSession().createQuery(
				"from Invoice where invoiceDate >= :fromDate AND invoiceDate<= :toDate " + " order by invoiceNo asc",
				Invoice.class);

		if (fromDate != null) {
			query.setParameter("fromDate", fromDate, TemporalType.DATE);
		} else {
			query.setParameter("fromDate", null);
		}

		if (toDate != null) {
			query.setParameter("toDate", toDate, TemporalType.DATE);
		} else {
			query.setParameter("toDate", null);
		}

		logger.debug(query.toString());

		if (query.getResultList().size() == 0) {
			logger.debug("No invoices found matching current date search criteria.");
			return null;

		} else {

			logger.debug("Search Invoice List Size: " + query.getResultList().size());
			List<Invoice> list = (List<Invoice>) query.getResultList();
			return list;
		}
	}

	@Override
	public List<InvoiceProduct> getAllProductsInInvoice(Integer invoiceId) {
		logger.debug("Fetching all products in Invoice: " + invoiceId);
		getCurrentSession().flush();
		return getCurrentSession().createQuery(
				"from InvoiceProduct " + " where invoiceId = " + invoiceId + " order by product.productCode asc ",
				InvoiceProduct.class).getResultList();
	}

	@Override
	public Response addProductToInvoice(InvoiceProduct newInvoiceProduct) {
		logger.debug("Adding product to Invoice: " + newInvoiceProduct);
		getCurrentSession().save(newInvoiceProduct);
		logger.debug("Saved product in invoice: " + newInvoiceProduct);
		return new Response(true, null);
	}

	@Override
	public InvoiceProduct getProductInInvoice(Integer invoiceProductId) {
		logger.debug("Getting product having invoiceProductId: " + invoiceProductId);

		Query<InvoiceProduct> query = getCurrentSession().createQuery(
				"from InvoiceProduct where invoiceProductId = " + invoiceProductId.toString() + "",
				InvoiceProduct.class);
		// query.setParameter("invoiceId", invoiceId.toString());

		logger.debug(query.toString());
		try {
			InvoiceProduct invoiceProduct = query.getSingleResult();
			return invoiceProduct;
		} catch (NoResultException | NonUniqueResultException ne) {
			logger.debug("Product not found in invoice." + ne.getMessage());
			throw new EmptyResultDataAccessException("InvoiceProduct [" + invoiceProductId + "] not found", 1);
		}

	}

	@Override
	public Response updateProductInInvoice(InvoiceProduct invoiceProduct) {
		InvoiceProduct invoiceProductToUpdate = getProductInInvoice(invoiceProduct.getInvoiceProductId());
		invoiceProductToUpdate.setQty(invoiceProduct.getQty());
		invoiceProductToUpdate.setRatePerUnit(invoiceProduct.getRatePerUnit());
		invoiceProductToUpdate.setVatType(invoiceProduct.getVatType());
		invoiceProductToUpdate.setTotalBeforeTax(invoiceProduct.getTotalBeforeTax());
		invoiceProductToUpdate.setDiscount(invoiceProduct.getDiscount());
		invoiceProductToUpdate.setVatAmount(invoiceProduct.getVatAmount());
		invoiceProductToUpdate.setCgstAmount(invoiceProduct.getCgstAmount());
		invoiceProductToUpdate.setSgstAmount(invoiceProduct.getSgstAmount());
		invoiceProductToUpdate.setTotalPrice(invoiceProduct.getTotalPrice());
		invoiceProductToUpdate.setUpdateBy(invoiceProduct.getUpdateBy());
		invoiceProductToUpdate.setUpdateTimestamp(invoiceProduct.getUpdateTimestamp());
		getCurrentSession().update(invoiceProductToUpdate);
		getCurrentSession().flush();
		return new Response(true, null);
	}

	@Override
	public Response deleteProductFromInvoice(InvoiceProduct invoiceProduct) {

		InvoiceProduct existingInvoiceProduct = getProductInInvoice(invoiceProduct.getInvoiceProductId());
		if (existingInvoiceProduct != null) {
			getCurrentSession().delete(existingInvoiceProduct);
			getCurrentSession().flush();
			return new Response(true, null);
		}
		return new Response(false, EpSystemError.NO_RECORD_FOUND);
	}

	@Override
	public BigDecimal getTotalSalesAmountInYear() {
		Date startDate = CalendarUtility.getFirstDateOfYear();
		Date endDate = CalendarUtility.getLastDateOfYear();
		logger.debug("getTotalSalesAmountInYear for the Year - [" + startDate + " - " + endDate + "]");
		return getTotalSalesAmountInDateRange(startDate, endDate);
	}

	@Override
	public BigDecimal getTotalSalesAmountInMonth() {
		Date startDate = CalendarUtility.getFirstDateOfMonth();
		Date endDate = CalendarUtility.getLastDateOfMonth();
		logger.debug("getTotalSalesAmountInMonth for  - [" + startDate + " - " + endDate + "]");
		return getTotalSalesAmountInDateRange(startDate, endDate);
	}

	@Override
	public BigDecimal getTotalSalesAmountInDateRange(Date startDate, Date endDate) {
		logger.debug("getTotalSalesAmountInDateRange for - [" + startDate + " - " + endDate + "]");
		Query<BigDecimal> sumQuery = getCurrentSession().createQuery(
				"select sum(totalAmount) from Invoice WHERE invoiceDate BETWEEN :stDate AND :edDate ",
				BigDecimal.class);
		sumQuery.setParameter("stDate", startDate, TemporalType.DATE);
		sumQuery.setParameter("edDate", endDate, TemporalType.DATE);
		logger.debug(sumQuery.toString());
		return sumQuery.getSingleResult();
	}

	@Override
	public int getCountOfTotalInvoicesForYear() {
		Date startDate = CalendarUtility.getFirstDateOfYear();
		Date endDate = CalendarUtility.getLastDateOfYear();
		logger.debug("getCountOfTotalInvoicesForYear for the Year - [" + startDate + " - " + endDate + "]");
		return getCountOfTotalInvoicesInDateRange(startDate, endDate);
	}

	@Override
	public int getCountOfTotalInvoicesInMonth() {
		Date begining, end;
		begining = CalendarUtility.getFirstDateOfMonth();
		end = CalendarUtility.getLastDateOfMonth();
		logger.debug("getCountOfTotalInvoicesInMonth() for the Month - [" + begining + " - " + end + "]");
		return getCountOfTotalInvoicesInDateRange(begining, end);
	}

	@Override
	public int getCountOfTotalInvoicesInDateRange(Date startDate, Date endDate) {
		logger.debug("getCountOfTotalInvoicesInDateRange for  [" + startDate + " - " + endDate + "]");

		Query<Long> countQuery = getCurrentSession()
				.createQuery("select count(*) from Invoice WHERE invoiceDate BETWEEN :stDate AND :edDate ", Long.class);

		countQuery.setParameter("stDate", startDate, TemporalType.DATE);
		countQuery.setParameter("edDate", endDate, TemporalType.DATE);

		logger.debug(countQuery.toString());

		return countQuery.getSingleResult().intValue();
	}

	@Override
	public BigDecimal getTotalVATInYear() {
		Date startDate = CalendarUtility.getFirstDateOfYear();
		Date endDate = CalendarUtility.getLastDateOfYear();
		return getTotalVATInDateRange(startDate, endDate);
	}

	@Override
	public BigDecimal getTotalVATInDateRange(Date startDate, Date endDate) {
		logger.debug("getTotalVATInDateRange for - [" + startDate + " - " + endDate + "]");

		Query<BigDecimal> sumQuery = getCurrentSession().createQuery(
				"select sum(totalVat) from Invoice WHERE invoiceDate BETWEEN :stDate AND :edDate ", BigDecimal.class);

		sumQuery.setParameter("stDate", startDate, TemporalType.DATE);
		sumQuery.setParameter("edDate", endDate, TemporalType.DATE);

		logger.debug(sumQuery.toString());

		return sumQuery.getSingleResult();
	}

	@Override
	public BigDecimal getTotalGSTInYear() {
		Date startDate = CalendarUtility.getFirstDateOfYear();
		Date endDate = CalendarUtility.getLastDateOfYear();
		return getTotalGSTInDateRange(startDate, endDate);
	}

	@Override
	public BigDecimal getTotalGSTInDateRange(Date startDate, Date endDate) {
		logger.debug("getTotalGSTInDateRange for - [" + startDate + " - " + endDate + "]");

		Query<BigDecimal> sumQuery = getCurrentSession().createQuery(
				"select sum(totalSgst)+sum(totalCgst) from Invoice WHERE invoiceDate BETWEEN :stDate AND :edDate ", BigDecimal.class);

		sumQuery.setParameter("stDate", startDate, TemporalType.DATE);
		sumQuery.setParameter("edDate", endDate, TemporalType.DATE);

		logger.debug(sumQuery.toString());

		return sumQuery.getSingleResult();
	}
	
	@Override
	public int getCountOfCustomerBilledForYear() {
		Date startDate = CalendarUtility.getFirstDateOfYear();
		Date endDate = CalendarUtility.getLastDateOfYear();
		return getCountOfCustomerBilledInDateRange(startDate, endDate);
	}

	@Override
	public int getCountOfCustomerBilledInMonth() {
		Date startDate = CalendarUtility.getFirstDateOfMonth();
		Date endDate = CalendarUtility.getLastDateOfMonth();
		return getCountOfCustomerBilledInDateRange(startDate, endDate);
	}

	@Override
	public int getCountOfCustomerBilledInDateRange(Date startDate, Date endDate) {
		logger.debug("getCountOfCustomerBilledInDateRange for - [" + startDate + " - " + endDate + "]");

		Query<Long> sumQuery = getCurrentSession().createQuery(
				"select count(distinct customer) from Invoice WHERE invoiceDate BETWEEN :stDate AND :edDate ",
				Long.class);

		sumQuery.setParameter("stDate", startDate, TemporalType.DATE);
		sumQuery.setParameter("edDate", endDate, TemporalType.DATE);

		logger.debug(sumQuery.toString());

		return sumQuery.getSingleResult().intValue();
	}

	/*@Override
	public void updateEffectiveTotalsInInvoice(Integer invoiceId, String updateBy, Timestamp updateTimestamp) {
		*
		 * UPDATE public.invoice SET total_amount_before_tax =
		 * invPdt.total_amount_before_tax, total_cgst = invPdt.total_cgst,
		 * total_sgst = invPdt.total_sgst, total_vat = invPdt.total_vat,
		 * total_amount = invPdt.total_amount, total_discount =
		 * invPdt.total_discount FROM ( SELECT SUM(total_before_tax)
		 * total_amount_before_tax, SUM(cgst_amount) total_cgst,
		 * SUM(sgst_amount) total_sgst, SUM(vat_amount) total_vat, SUM(total)
		 * total_amount, SUM(discount) total_discount
		 * 
		 * FROM public.invoice_product
		 * 
		 * WHERE invoice_id = 20 GROUP BY invoice_id ) invPdt WHERE invoice_id =
		 * 20
		 *

		
		Query query = getCurrentSession().createNativeQuery("UPDATE public.invoice SET  "
				+ " total_amount_before_tax = invPdt.total_amount_before_tax," + " total_cgst = invPdt.total_cgst,"
				+ " total_sgst = invPdt.total_sgst, " + " total_vat = invPdt.total_vat, "
				+ " total_amount = invPdt.total_amount, " + " total_discount = invPdt.total_discount "
				+ " FROM  ( SELECT  SUM(total_before_tax) total_amount_before_tax,SUM(cgst_amount) total_cgst,"
				+ "	SUM(sgst_amount) total_sgst,"
				+ " SUM(vat_amount) total_vat,SUM(total) total_amount,SUM(discount) total_discount "
				+ " FROM public.invoice_product  WHERE invoice_id = :invoiceId GROUP BY invoice_id ) invPdt WHERE invoice_id = :invoiceId");

		query.setParameter("invoiceId",invoiceId);
		query.executeUpdate();

	}*/

}