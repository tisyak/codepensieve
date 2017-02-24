package com.medsys.product.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.medsys.exception.SysException;
import com.medsys.product.model.ProductInv;

public interface ProductInvDAO {

	public void addProduct(ProductInv product);

	public ProductInv getProduct(Integer productId);

	public ProductInv getProductByCode(String productCode);

	public void discardProduct(String productCode, Integer discardQty) throws SysException;

	public void engageProduct(String productCode, Integer engageQty) throws SysException;

	public void disengageProduct(String productCode, Integer releaseQty) throws SysException;

	public void sellProduct(String productCode, Integer saleQty) throws SysException;

	public List<ProductInv> getAllProduct();

	public List<ProductInv> searchForProduct(ProductInv product);

	void addProductByCode(String productCode, Integer qty, BigDecimal price, String updateBy,
			Timestamp updateTimestamp);

}