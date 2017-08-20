package com.medsys.orders.dao;

import java.util.Date;
import java.util.List;

import com.medsys.common.model.Response;
import com.medsys.orders.model.PurchaseOrder;
import com.medsys.orders.model.PurchaseOrderProductSet;
 
public interface PurchaseOrderDAO {
 
    public Response addPurchaseOrder(PurchaseOrder invoice);
 
    public PurchaseOrder getPurchaseOrder(Integer invoiceId);
 
    public PurchaseOrder updatePurchaseOrder(PurchaseOrder invoice);
 
    public Response deletePurchaseOrder(Integer invoiceId);
 
    public List<PurchaseOrder> getAllPurchaseOrder();

	public List<PurchaseOrder> searchForPurchaseOrder(PurchaseOrder invoice);

	public List<PurchaseOrderProductSet> getAllProductsInPurchaseOrder(Integer invoiceId);

	public Response addProductToPurchaseOrder(PurchaseOrderProductSet newPOProductSet);

	public PurchaseOrderProductSet getProductInPurchaseOrder(Integer invoiceProductId);

	public Response updateProductInPurchaseOrder(PurchaseOrderProductSet invoiceProduct);

	public Response deleteProductFromPurchaseOrder(PurchaseOrderProductSet invoiceProduct);

	List<PurchaseOrder> searchForPurchaseOrder(Date fromDate, Date toDate);

	public int getCountOfTotalPurchaseOrdersForYear();
	
	public int getCountOfTotalPurchaseOrdersInMonth();

	int getCountOfTotalPurchaseOrdersInDateRange(Date startDate, Date endDate);
	
}