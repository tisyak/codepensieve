package com.medsys.product.dao;

import java.util.List;

import com.medsys.common.model.Response;
import com.medsys.exception.SysException;
import com.medsys.product.model.ProductInv;

public interface ProductInvDAO {

	public Response addProduct(ProductInv product);

	public ProductInv getProduct(Integer productId);

	public ProductInv getProductByCode(String productCode);

	public void discardProduct(String productCode, Integer discardQty) throws SysException;

	public void engageProduct(String productCode, Integer engageQty) throws SysException;

	public void disengageProduct(String productCode, Integer releaseQty) throws SysException;

	public void sellProduct(String productCode, Integer saleQty) throws SysException;
	
	void cancelProductSale(String productCode, Integer cancelledQty) throws SysException;

	public List<ProductInv> getAllProduct();

	public List<ProductInv> searchForProduct(ProductInv product);

	public Response updateProduct(ProductInv productInv);

	public Response deleteProduct(ProductInv productInv);

	public int getCountOfProductsInDeficit();

	

}