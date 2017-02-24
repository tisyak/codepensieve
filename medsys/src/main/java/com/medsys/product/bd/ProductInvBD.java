package com.medsys.product.bd;

import java.util.List;

import com.medsys.exception.SysException;
import com.medsys.product.model.ProductInv;

public interface ProductInvBD {

	public void addProduct(ProductInv product);
	
	public ProductInv getProduct(Integer productId);
	
	public ProductInv getProductByCode(String productCode);

	public void discardProduct(String productCode, Integer discardQty) throws SysException;

	public void engageProduct(String productCode, Integer engageQty) throws SysException;

	public void disengageProduct(String productCode, Integer releaseQty) throws SysException;

	public void sellProduct(String productCode, Integer saleQty) throws SysException;

	public List<ProductInv> getAllProductInv();
	
	public List<ProductInv> searchForProductInv(ProductInv product);

	

}