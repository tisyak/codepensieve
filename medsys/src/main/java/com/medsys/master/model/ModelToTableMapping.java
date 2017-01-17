package com.medsys.master.model;

public enum ModelToTableMapping {
	
	ConfigPara("ConfigPara","config_para"),
	AuthMedium("AuthMedium","m_auth_medium"),
	IdProof("IdProof","m_id_proof"),
	ServiceAccountStatus("ServiceAccountStatus","m_service_account_status"),
	SPAccountStatus("SPAccountStatus","m_sp_account_status"),
	SPCategory("SPCategory","m_sp_category"),
	State("State","m_state"),
	UserAccountStatus("UserAccountStatus","m_user_account_status"),
	Ministry("Ministry","m_ministry"),
	AuthenticationLevel("AuthenticationLevel","m_authentication_level"),
	IMGPASSWORDCATEGORY("ImagePasswordCategory","m_img_pwd_category"),
	IMGPASSWORDDETAIL("ImagePasswordDetail","m_img_pwd_detail"),
	UserMappingOptions("UserMappingOptions","m_user_mapping_options");
	
		
	private ModelToTableMapping(String modelClassName, String tableName) {
		this.modelClassName = modelClassName;
		this.tableName = tableName;		
	}
	
	String modelClassName;
	String tableName;
	
	public String getModelClassName() {
		return modelClassName;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	

}
