package com.medsys.orders.bd;

import java.util.Date;
import java.util.List;

import com.medsys.common.model.Response;
import com.medsys.orders.model.PurchaseOrder;
import com.medsys.orders.model.PurchaseOrderProductSet;

public interface PurchaseOrderBD {

	public Response addPurchaseOrder(PurchaseOrder purchaseOrder);
	
	public PurchaseOrder getPurchaseOrder(Integer purchaseOrderId);

	public PurchaseOrder updatePurchaseOrder(PurchaseOrder purchaseOrder);

	public Response deletePurchaseOrder(Integer purchaseOrderId);

	public List<PurchaseOrder> getAllPurchaseOrder();
	
	public List<PurchaseOrder> searchForPurchaseOrder(PurchaseOrder purchaseOrder);
	
	public List<PurchaseOrderProductSet> getAllProductsInPurchaseOrder(Integer purchaseOrderId);

	public Response addProductToPurchaseOrder(PurchaseOrderProductSet newPOProductSet);

	public PurchaseOrderProductSet getProductInPurchaseOrder(Integer poProductSetId);

	public Response updateProductInPurchaseOrder(PurchaseOrderProductSet poProductSet);

	public Response deleteProductFromPurchaseOrder(PurchaseOrderProductSet poProductSet);

	public List<PurchaseOrder> searchForPurchaseOrder(Date fromDate, Date toDate);

	public int getCountOfTotalPurchaseOrdersInMonth();

	public int getCountOfTotalPurchaseOrdersForYear();


}