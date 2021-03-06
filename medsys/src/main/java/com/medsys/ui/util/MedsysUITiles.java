/*
 * 
 */
package com.medsys.ui.util;

/**
 * The Enum AdminUITiles.
 */
public enum MedsysUITiles {

	/** The index. */
	LOGIN("Login"),

	/** The Dashboard. */
	MASTER_ADMIN_DASHBOARD("master.MsDashboard"),

	DEFAULT_HOME("common.Dashboard"),
	
	/** ORDER MANAGEMENT */

	SEARCH_ORDERS("orders.SearchOrders"),

	ORDERS_LIST("orders.SearchOrders"),

	ADD_ORDER("orders.AddOrder"),

	EDIT_ORDER("orders.EditOrder"),

	CONFIRM_DELETE_ORDER("orders.DeleteOrder"),
	
	/** INVOICE MANAGEMENT */

	SEARCH_INVOICES("invoices.SearchInvoices"),

	INVOICES_LIST("invoices.SearchInvoices"),

	ADD_INVOICE("invoices.AddInvoice"),

	EDIT_INVOICE("invoices.EditInvoice"),
	
	EDIT_GST_INVOICE("invoices.EditGSTInvoice"),

	CONFIRM_DELETE_INVOICE("invoices.DeleteInvoice"),
	
	CONFIRM_CANCEL_INVOICE("invoices.CancelInvoice"),
	
	SALES_TAX_REPORT("invoices.SalesTax"),
	
	PRODUCT_DEFICIT_REPORT("product.ProductDeficit"),
	
	/** PRODUCT INVENTORY MANAGEMENT*/
	MANAGE_PRODUCT_INV("product.ProductInv"),
	
	/** PRODUCT GROUP MANAGEMENT*/
	MANAGE_PRODUCT_GROUP("product.ProductGroup"),
	
	/** SET MANAGEMENT */

	SEARCH_SET("set.SearchSet"),

	SET_LIST("set.SearchSet"),

	ADD_SET("set.AddSet"),

	EDIT_SET("set.EditSet"),
	
	QUOTATION("set.Quotation"),
	
	/** Admin User Management */
	ADMIN_USERS_LIST("master.AdminUserList"),
	
	/** CUSTOMER MANAGEMENT*/
	MANAGE_CUSTOMER("customer.Customer"),
	
	/** COMPANY MANAGEMENT*/
	MANAGE_COMPANY("company.Company"),
	
	
	/** PURCHASE_ORDER MANAGEMENT */

	SEARCH_PURCHASE_ORDER("purchaseorder.SearchPO"),

	PURCHASE_ORDER_LIST("purchaseorder.SearchPO"),

	ADD_PURCHASE_ORDER("purchaseorder.AddPO"),

	EDIT_PURCHASE_ORDER("purchaseorder.EditPO"),
	

	

	/** PROFILE MANAGEMENT */
	VIEW_PROFILE("profile.View"), EDIT_PROFILE("profile.Edit"), FORGOT_PASSWORD_OPTIONS("profile.forgotpassword.Options"),

	MESSAGE("common.Blank");

	/** The tiles name. */
	private final String tilesName;

	/**
	 * Instantiates a new  ui tiles.
	 *
	 * @param tilesName
	 *            the tiles name
	 */
	MedsysUITiles(String tilesName) {
		this.tilesName = tilesName;
	}

	/**
	 * Gets the tile.
	 *
	 * @return the tile
	 */
	public String getTile() {
		return tilesName;
	}

}
