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

	EDIT_PRODUCTS_IN_ORDER("orders.EditProductsInOrder"),

	DELETE_PRODUCTS_IN_ORDER("orders.DeleteProductsInOrder"),
	

	/** Admin User Management */
	ADMIN_USERS_LIST("master.AdminUserList"),

	SEARCH_CUSTOMER("customer.SearchCustomer"),

	CUSTOMER_LIST("customer.CustomerList"),

	ADD_CUSTOMER("customer.AddCustomer"),

	EDIT_CUSTOMER("customer.EditCustomer"),

	CONFIRM_DELETE_CUSTOMER("customer.DeleteCustomer"),

	SEARCH_CUSTOMER_DSC("customer.SearchCustomerDSC"),

	CUSTOMER_DSC_LIST("customer.CustomerDSCList"),

	ADD_CUSTOMER_DSC("customer.AddCustomerDSC"),

	EDIT_CUSTOMER_DSC("customer.EditCustomerDSC"),

	CONFIRM_DELETE_CUSTOMER_DSC("customer.DeleteCustomerDSC"),

	SEARCH_THIRDPARTY("thirdparty.SearchThirdParty"),

	THIRDPARTY_LIST("thirdparty.ThirdPartyList"),

	ADD_THIRDPARTY("thirdparty.AddThirdParty"),

	EDIT_THIRDPARTY("thirdparty.EditThirdParty"),

	CONFIRM_DELETE_THIRDPARTY("thirdparty.DeleteThirdParty"),

	SEARCH_DSC_TRANSFER("dsctransfer.SearchDscTranfer"),

	DSC_TRANSFER_LIST("dsctransfer.DscTranferList"),

	ADD_DSC_TRANSFER_INFO("dsctransfer.AddDscTranferInfo"),

	EDIT_DSC_TRANSFER_INFO("dsctransfer.EditDscTranferInfo"),

	CONFIRM_DSC_RETURN("dsctransfer.ReturnDsc"),

	CONFIRM_DSC_DELETE("dsctransfer.DeleteDsc"),

	/** PROFILE MANAGEMENT */
	VIEW_PROFILE("profile.View"), EDIT_PROFILE("profile.Edit"), FORGOT_PASSWORD_OPTIONS("profile.forgotpassword.Options"),

	MESSAGE("common.Blank");

	/** The tiles name. */
	private final String tilesName;

	/**
	 * Instantiates a new citizen ui tiles.
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
