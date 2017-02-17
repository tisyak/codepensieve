package com.medsys.product.bd;

import java.util.List;

import com.medsys.product.model.ProductMaster;

public interface ProductMasterBD {

	public void addProduct(ProductMaster product);
	
	public ProductMaster getProduct(Integer productId);
	
	public ProductMaster getProductByCode(String productCode);

	public void updateProductInMaster(ProductMaster product);

	public void deleteProductFromMaster(Integer productId);

	public List<ProductMaster> getAllProductMaster();
	
	public List<ProductMaster> searchForProductMaster(ProductMaster product);

	

}