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
	
	ADD_PRODUCTS_IN_ORDER("orders.AddProductsToOrder"),
	
	
	/** INVOICE MANAGEMENT */

	SEARCH_INVOICES("invoices.SearchInvoices"),

	INVOICES_LIST("invoices.SearchInvoices"),

	ADD_INVOICE("invoices.AddInvoice"),

	EDIT_INVOICE("invoices.EditInvoice"),

	CONFIRM_DELETE_INVOICE("invoices.DeleteInvoice"),
	
	/** PRODUCT INVENTORY MANAGEMENT*/
	MANAGE_PRODUCT_INV("product.ProductInv"),
	
	
	/** Admin User Management */
	ADMIN_USERS_LIST("master.AdminUserList"),
	
	/** CUSTOMER MANAGEMENT*/
	MANAGE_CUSTOMER("customer.Customer"),
	
	/** COMPANY MANAGEMENT*/
	MANAGE_COMPANY("company.Company"),

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
