package com.medsys.product.dao;

import java.util.List;

import com.medsys.product.model.ProductMaster;

public interface ProductMasterDAO {
 
    public void addProduct(ProductMaster product);
 
    public ProductMaster getProduct(Integer productId);
    
    public ProductMaster getProductByCode(String productCode);
 
    public void updateProductInMaster(ProductMaster product);
 
    public void deleteProductFromMaster(Integer productId);
 
    public List<ProductMaster> getAllProduct();

	public List<ProductMaster> searchForProduct(ProductMaster product);	
	
	
}