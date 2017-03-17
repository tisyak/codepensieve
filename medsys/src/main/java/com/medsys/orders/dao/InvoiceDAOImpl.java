package com.medsys.orders.dao;

import java.util.List;

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

import com.medsys.common.model.Response;
import com.medsys.orders.model.Invoice;
import com.medsys.orders.model.InvoiceProduct;
import com.medsys.util.EpSystemError;

@Repository
public class InvoiceDAOImpl implements InvoiceDAO {

	static Logger logger = LoggerFactory.getLogger(InvoiceDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Response addInvoice(Invoice invoice) {
		logger.debug("Saving invoice to DB.");
		try {
			getCurrentSession().save(invoice);
		} catch (HibernateException he) {
			logger.debug("Unable to save object. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Saved invoice: " + invoice);
		return new Response(true, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Invoice getInvoice(Integer invoiceId) {
		logger.debug("InvoiceDAOImpl.getInvoice() - [" + invoiceId + "]");
		Query<Invoice> query = getCurrentSession().createQuery("from Invoice where invoiceId = " + invoiceId + "");
		// query.setParameter("invoiceId", invoiceId.toString());

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No user found.");
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
	@SuppressWarnings("unchecked")
	public List<Invoice> getAllInvoice() {
		return getCurrentSession().createQuery("from Invoice order by invoiceDate desc").getResultList();
	}

	@Override
	public Invoice updateInvoice(Invoice invoice) {
		// TODO: Conditional based on InvoiceStatus to be included
		Invoice invoiceToUpdate = getInvoice(invoice.getInvoiceId());
		invoiceToUpdate.setCustomer(invoice.getCustomer());
		invoiceToUpdate.setInvoiceDate(invoice.getInvoiceDate());
		invoiceToUpdate.setPaymentTerms(invoice.getPaymentTerms());
		invoiceToUpdate.setPatientName(invoice.getPatientName());
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
				"from Invoice where lower(invoiceNo) like :invoiceNo OR lower(customer.name) like :custName "
				+ " OR invoiceDate = :invoiceDate "
						+ " order by invoiceNo asc");

		if (invoice.getInvoiceNo() != null) {
			query.setParameter("invoiceNo", "%" + invoice.getInvoiceNo().toLowerCase() + "%");
		} else {
			query.setParameter("invoiceNo", invoice.getInvoiceNo());
		}
		
		if (invoice.getInvoiceDate() != null) {
			query.setParameter("invoiceDate",  invoice.getInvoiceDate(),TemporalType.DATE);
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
	public List<InvoiceProduct> getAllProductsInInvoice(Integer invoiceId) {
		logger.debug("Fetching all products in Invoice: " + invoiceId);
		getCurrentSession().flush();
		return getCurrentSession()
				.createQuery(
						"from InvoiceProduct " + " where invoiceId = " + invoiceId + " invoice by product.productCode asc ")
				.getResultList();
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

		Query<InvoiceProduct> query = getCurrentSession()
				.createQuery("from InvoiceProduct where invoiceProductId = " + invoiceProductId.toString() + "");
		// query.setParameter("invoiceId", invoiceId.toString());

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("Product not found in invoice.");
			throw new EmptyResultDataAccessException("InvoiceProduct [" + invoiceProductId + "] not found", 1);
		} else {

			logger.debug("InvoiceProduct List Size: " + query.getResultList().size());
			List<InvoiceProduct> list = (List<InvoiceProduct>) query.getResultList();
			InvoiceProduct invoiceProduct = (InvoiceProduct) list.get(0);
			return invoiceProduct;
		}
	}

	@Override
	public Response updateProductInInvoice(InvoiceProduct invoiceProduct) {
		InvoiceProduct invoiceProductToUpdate = getProductInInvoice(invoiceProduct.getInvoiceProductId());
		invoiceProductToUpdate.setQty(invoiceProduct.getQty());
		invoiceProductToUpdate.setRatePerUnit(invoiceProduct.getRatePerUnit());
		invoiceProductToUpdate.setVatType(invoiceProduct.getVatType());
		invoiceProductToUpdate.setTotalPrice(invoiceProduct.getTotalPrice());
		invoiceProductToUpdate.setVatAmount(invoiceProduct.getVatAmount());
		invoiceProductToUpdate.setUpdateBy(invoiceProduct.getUpdateBy());
		invoiceProductToUpdate.setUpdateTimestamp(invoiceProduct.getUpdateTimestamp());
		getCurrentSession().update(invoiceProductToUpdate);
		// TODO: change return appropriately
		return new Response(true, null);
	}

	@Override
	public Response deleteProductFromInvoice(InvoiceProduct invoiceProduct) {
		InvoiceProduct existingInvoiceProduct = getProductInInvoice(invoiceProduct.getInvoiceProductId());

		if (existingInvoiceProduct != null) {
			getCurrentSession().delete(existingInvoiceProduct);
			return new Response(true, null);
		}

		return new Response(false, EpSystemError.NO_RECORD_FOUND);

	}

}