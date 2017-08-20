package com.medsys.orders.bd;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.common.model.Response;
import com.medsys.orders.dao.PurchaseOrderDAO;
import com.medsys.orders.model.PurchaseOrder;
import com.medsys.orders.model.PurchaseOrderProductSet;
import com.medsys.util.EpSystemError;

@Service
@Transactional
public class PurchaseOrderBDImpl implements PurchaseOrderBD {
	static Logger logger = LoggerFactory.getLogger(PurchaseOrderBDImpl.class);

	@Autowired
	private PurchaseOrderDAO purchaseOrderDAO;

	@Override
	public Response addPurchaseOrder(PurchaseOrder purchaseOrder) {
		logger.debug("PurchaseOrderBD: Adding purchaseOrder.");
		Response response = purchaseOrderDAO.addPurchaseOrder(purchaseOrder);
		return response;

	}

	@Override
	public PurchaseOrder getPurchaseOrder(Integer purchaseOrderId) {
		return purchaseOrderDAO.getPurchaseOrder(purchaseOrderId);
	}

	@Override
	public PurchaseOrder updatePurchaseOrder(PurchaseOrder purchaseOrder) {
		logger.debug("PurchaseOrderBD: Updating purchaseOrder.");
		return purchaseOrderDAO.updatePurchaseOrder(purchaseOrder);
	}

	@Override
	public Response deletePurchaseOrder(Integer purchaseOrderId) {
		return purchaseOrderDAO.deletePurchaseOrder(purchaseOrderId);
	}

	@Override
	public List<PurchaseOrder> getAllPurchaseOrder() {
		return purchaseOrderDAO.getAllPurchaseOrder();
	}

	@Override
	public List<PurchaseOrder> searchForPurchaseOrder(PurchaseOrder purchaseOrder) {
		return purchaseOrderDAO.searchForPurchaseOrder(purchaseOrder);
	}

	@Override
	public List<PurchaseOrder> searchForPurchaseOrder(Date fromDate, Date toDate) {
		return purchaseOrderDAO.searchForPurchaseOrder(fromDate, toDate);
	}

	@Override
	public List<PurchaseOrderProductSet> getAllProductsInPurchaseOrder(Integer purchaseOrderId) {
		logger.info("Get All products in PurchaseOrder: " + purchaseOrderId);
		List<PurchaseOrderProductSet> poProductSets = purchaseOrderDAO.getAllProductsInPurchaseOrder(purchaseOrderId);
		return poProductSets;

	}

	@Override
	@Transactional
	public Response addProductToPurchaseOrder(PurchaseOrderProductSet newPOProductSet) {
		logger.info("ADD product to PurchaseOrder: " + newPOProductSet);
		// Managing product inventory before adding product to the purchaseOrder
		Response response = purchaseOrderDAO.addProductToPurchaseOrder(newPOProductSet);
		return response;
	}

	@Override
	public PurchaseOrderProductSet getProductInPurchaseOrder(Integer poProductSetId) {
		logger.debug("GET product for poProductSetId: " + poProductSetId);
		return purchaseOrderDAO.getProductInPurchaseOrder(poProductSetId);
	}

	@Override
	@Transactional
	public Response updateProductInPurchaseOrder(PurchaseOrderProductSet poProductSet) {

		logger.info("UPDATE poProductSet: " + poProductSet);
		PurchaseOrderProductSet orgPOProductSet = getProductInPurchaseOrder(poProductSet.getProductSetId());

		if (!orgPOProductSet.getProduct().getProductId().equals(poProductSet.getProduct().getProductId())) {
			return new Response(false, EpSystemError.PO_PRODUCT_ID_MISMATCH);
		}

		logger.debug("Updating the product to purchaseOrder: " + poProductSet);

		Response response = purchaseOrderDAO.updateProductInPurchaseOrder(poProductSet);

		return response;

	}

	@Override
	@Transactional
	public Response deleteProductFromPurchaseOrder(PurchaseOrderProductSet poProductSet) {
		logger.info("DELETE poProductSet: " + poProductSet);
		return purchaseOrderDAO.deleteProductFromPurchaseOrder(poProductSet);
	}

	@Override
	public int getCountOfTotalPurchaseOrdersInMonth() {
		return purchaseOrderDAO.getCountOfTotalPurchaseOrdersInMonth();
	}

	@Override
	public int getCountOfTotalPurchaseOrdersForYear() {
		return purchaseOrderDAO.getCountOfTotalPurchaseOrdersForYear();
	}

}