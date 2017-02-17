package com.medsys.product.bd;

import java.util.List;

import com.medsys.product.model.ProductInv;

public interface ProductInvBD {

	public void addProduct(ProductInv product);
	
	public ProductInv getProduct(Integer productId);
	
	public ProductInv getProductByCode(String productCode);

	public void updateProductInv(ProductInv product);

	public void deleteProductFromInv(Integer productId);

	public List<ProductInv> getAllProductInv();
	
	public List<ProductInv> searchForProductInv(ProductInv product);

	

}