package com.medsys.product.dao;

import java.util.List;

import com.medsys.product.model.ProductInv;

public interface ProductInvDAO {
 
    public void addProduct(ProductInv product);
 
    public ProductInv getProduct(Integer productId);
    
    public ProductInv getProductByCode(String productCode);
 
    public void updateProductInv(ProductInv product);
 
    public void deleteProductFromInv(Integer productId);
 
    public List<ProductInv> getAllProduct();

	public List<ProductInv> searchForProduct(ProductInv product);	
	
	
}