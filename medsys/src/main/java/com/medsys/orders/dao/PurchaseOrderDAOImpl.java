package com.medsys.orders.dao;

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
import com.medsys.orders.model.PurchaseOrder;
import com.medsys.orders.model.PurchaseOrderProductSet;
import com.medsys.util.CalendarUtility;
import com.medsys.util.EpSystemError;

@Repository
public class PurchaseOrderDAOImpl implements PurchaseOrderDAO {

	static Logger logger = LoggerFactory.getLogger(PurchaseOrderDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	@Transactional
	public Response addPurchaseOrder(PurchaseOrder purchaseOrder) {
		logger.debug("Saving purchaseOrder to DB.");
		try {
			getCurrentSession().save(purchaseOrder);
		} catch (Exception he) {
			logger.debug("Unable to save object. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Saved purchaseOrder: " + purchaseOrder);
		return new Response(true, null);
	}

	@Override
	public PurchaseOrder getPurchaseOrder(Integer purchaseOrderId) {
		logger.debug("PurchaseOrderDAOImpl.getPurchaseOrder() - [" + purchaseOrderId + "]");
		Query<PurchaseOrder> query = getCurrentSession()
				.createQuery("from PurchaseOrder inv LEFT JOIN FETCH inv.products where inv.purchaseOrderId = "
						+ purchaseOrderId + "", PurchaseOrder.class);
		// query.setParameter("purchaseOrderId", purchaseOrderId.toString());

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No purchaseOrder found.");
			throw new EmptyResultDataAccessException("PurchaseOrder [" + purchaseOrderId + "] not found", 1);
		} else {

			logger.debug("PurchaseOrder List Size: " + query.getResultList().size());
			List<PurchaseOrder> list = (List<PurchaseOrder>) query.getResultList();
			PurchaseOrder purchaseOrder = (PurchaseOrder) list.get(0);
			return purchaseOrder;
		}
	}

	@Override
	public Response deletePurchaseOrder(Integer purchaseOrderId) {
		try {
			PurchaseOrder purchaseOrder = getPurchaseOrder(purchaseOrderId);
			if (purchaseOrder != null) {
				getCurrentSession().delete(purchaseOrder);
				logger.info("Delete of purchaseOrder with purchaseOrderId: " + purchaseOrderId + " successful");
				return new Response(true, null);
			} else {
				throw new EmptyResultDataAccessException("PurchaseOrder [" + purchaseOrderId + "] not found", 1);
			}
		} catch (HibernateException he) {
			logger.error("Delete of purchaseOrder with purchaseOrderId: " + purchaseOrderId + " failed.");
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
	}

	@Override
	public List<PurchaseOrder> getAllPurchaseOrder() {

		return getCurrentSession()
				.createQuery(" from PurchaseOrder order by purchaseOrderDate,purchaseOrderNumber desc",
						PurchaseOrder.class)
				.getResultList();
	}

	@Override
	public PurchaseOrder updatePurchaseOrder(PurchaseOrder purchaseOrder) {
		// TODO: Conditional based on PurchaseOrderStatus to be included
		PurchaseOrder purchaseOrderToUpdate = getPurchaseOrder(purchaseOrder.getPurchaseOrderId());
		purchaseOrderToUpdate.setSupplier(purchaseOrder.getSupplier());
		purchaseOrderToUpdate.setPurchaseOrderDate(purchaseOrder.getPurchaseOrderDate());
		purchaseOrderToUpdate.setUpdateBy(purchaseOrder.getUpdateBy());
		purchaseOrderToUpdate.setUpdateTimestamp(purchaseOrder.getUpdateTimestamp());
		getCurrentSession().update(purchaseOrderToUpdate);
		return purchaseOrderToUpdate;
	}

	@Override
	public List<PurchaseOrder> searchForPurchaseOrder(PurchaseOrder purchaseOrder) {
		logger.debug("PurchaseOrderDAOImpl.searchForPurchaseOrder() - [" + purchaseOrder.toString() + "]");
		Query<PurchaseOrder> query = getCurrentSession().createQuery(
				" from PurchaseOrder where lower(purchaseOrderNumber) like :purchaseOrderNumber OR lower(supplier.name) like :suppName "
						+ " OR purchaseOrderDate = :purchaseOrderDate " + " order by purchaseOrderNumber asc",
				PurchaseOrder.class);

		if (purchaseOrder.getPurchaseOrderNumber() != null) {
			query.setParameter("purchaseOrderNumber", "%" + purchaseOrder.getPurchaseOrderNumber().toLowerCase() + "%");
		} else {
			query.setParameter("purchaseOrderNumber", purchaseOrder.getPurchaseOrderNumber());
		}

		if (purchaseOrder.getPurchaseOrderDate() != null) {
			query.setParameter("purchaseOrderDate", purchaseOrder.getPurchaseOrderDate(), TemporalType.DATE);
		} else {
			query.setParameter("purchaseOrderDate", null);
		}

		if (purchaseOrder.getSupplier() != null && purchaseOrder.getSupplier().getName() != null) {
			query.setParameter("suppName", "%" + purchaseOrder.getSupplier().getName().toLowerCase() + "%");
		} else {
			query.setParameter("suppName", null);
		}

		logger.debug(query.toString());

		if (query.getResultList().size() == 0) {
			logger.debug("No purchaseOrders found matching current search criteria.");
			return null;

		} else {

			logger.debug("Search PurchaseOrder List Size: " + query.getResultList().size());
			List<PurchaseOrder> list = (List<PurchaseOrder>) query.getResultList();
			return list;
		}
	}

	@Override
	public List<PurchaseOrder> searchForPurchaseOrder(Date fromDate, Date toDate) {
		logger.debug("PurchaseOrderDAOImpl.searchForPurchaseOrder() - [" + fromDate + "]" + " to [" + toDate + "]");
		Query<PurchaseOrder> query = getCurrentSession()
				.createQuery("from PurchaseOrder where purchaseOrderDate >= :fromDate AND purchaseOrderDate<= :toDate "
						+ " order by purchaseOrderNumber asc", PurchaseOrder.class);

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
			logger.debug("No purchaseOrders found matching current date search criteria.");
			return null;

		} else {

			logger.debug("Search PurchaseOrder List Size: " + query.getResultList().size());
			List<PurchaseOrder> list = (List<PurchaseOrder>) query.getResultList();
			return list;
		}
	}

	@Override
	public List<PurchaseOrderProductSet> getAllProductsInPurchaseOrder(Integer purchaseOrderId) {
		logger.debug("Fetching all products in PurchaseOrder: " + purchaseOrderId);
		getCurrentSession().flush();
		return getCurrentSession().createQuery("from PurchaseOrderProductSet " + " where purchaseOrderId = "
				+ purchaseOrderId + " order by product.productCode asc ", PurchaseOrderProductSet.class)
				.getResultList();
	}

	@Override
	public Response addProductToPurchaseOrder(PurchaseOrderProductSet newPurchaseOrderProductSet) {
		logger.debug("Adding product to PurchaseOrder: " + newPurchaseOrderProductSet);
		getCurrentSession().save(newPurchaseOrderProductSet);
		logger.debug("Saved product in purchaseOrder: " + newPurchaseOrderProductSet);
		return new Response(true, null);
	}

	@Override
	public PurchaseOrderProductSet getProductInPurchaseOrder(Integer productSetId) {
		logger.debug("Getting product having productSetId: " + productSetId);

		Query<PurchaseOrderProductSet> query = getCurrentSession().createQuery(
				"from PurchaseOrderProductSet where productSetId = " + productSetId.toString() + "",
				PurchaseOrderProductSet.class);
		// query.setParameter("purchaseOrderId", purchaseOrderId.toString());

		logger.debug(query.toString());
		try {
			PurchaseOrderProductSet purchaseOrderProduct = query.getSingleResult();
			return purchaseOrderProduct;
		} catch (NoResultException | NonUniqueResultException ne) {
			logger.debug("Product not found in purchaseOrder." + ne.getMessage());
			throw new EmptyResultDataAccessException("PurchaseOrderProductSet [" + productSetId + "] not found", 1);
		}

	}

	@Override
	public Response updateProductInPurchaseOrder(PurchaseOrderProductSet purchaseOrderProduct) {
		PurchaseOrderProductSet purchaseOrderProductToUpdate = getProductInPurchaseOrder(
				purchaseOrderProduct.getProductSetId());
		purchaseOrderProductToUpdate.setQty(purchaseOrderProduct.getQty());
		purchaseOrderProductToUpdate.setUpdateBy(purchaseOrderProduct.getUpdateBy());
		purchaseOrderProductToUpdate.setUpdateTimestamp(purchaseOrderProduct.getUpdateTimestamp());
		getCurrentSession().update(purchaseOrderProductToUpdate);
		getCurrentSession().flush();
		return new Response(true, null);
	}

	@Override
	public Response deleteProductFromPurchaseOrder(PurchaseOrderProductSet purchaseOrderProduct) {

		PurchaseOrderProductSet existingPurchaseOrderProductSet = getProductInPurchaseOrder(
				purchaseOrderProduct.getProductSetId());
		if (existingPurchaseOrderProductSet != null) {
			getCurrentSession().delete(existingPurchaseOrderProductSet);
			return new Response(true, null);
		}
		return new Response(false, EpSystemError.NO_RECORD_FOUND);
	}

	@Override
	public int getCountOfTotalPurchaseOrdersForYear() {
		Date startDate = CalendarUtility.getFirstDateOfYear();
		Date endDate = CalendarUtility.getLastDateOfYear();
		logger.debug("getCountOfTotalPurchaseOrdersForYear for the Year - [" + startDate + " - " + endDate + "]");
		return getCountOfTotalPurchaseOrdersInDateRange(startDate, endDate);
	}

	@Override
	public int getCountOfTotalPurchaseOrdersInMonth() {
		Date begining, end;
		begining = CalendarUtility.getFirstDateOfMonth();
		end = CalendarUtility.getLastDateOfMonth();
		logger.debug("getCountOfTotalPurchaseOrdersInMonth() for the Month - [" + begining + " - " + end + "]");
		return getCountOfTotalPurchaseOrdersInDateRange(begining, end);
	}

	@Override
	public int getCountOfTotalPurchaseOrdersInDateRange(Date startDate, Date endDate) {
		logger.debug("getCountOfTotalPurchaseOrdersInDateRange for  [" + startDate + " - " + endDate + "]");

		Query<Long> countQuery = getCurrentSession().createQuery(
				"select count(*) from PurchaseOrder WHERE purchaseOrderDate BETWEEN :stDate AND :edDate ", Long.class);

		countQuery.setParameter("stDate", startDate, TemporalType.DATE);
		countQuery.setParameter("edDate", endDate, TemporalType.DATE);

		logger.debug(countQuery.toString());

		return countQuery.getSingleResult().intValue();
	}

}