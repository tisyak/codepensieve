package com.medsys.product.bd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.product.dao.ProductInvDAO;
import com.medsys.product.model.ProductInv;
 
@Service
@Transactional
public class ProductInvBDImpl implements ProductInvBD {
    static Logger logger = LoggerFactory.getLogger(ProductInvBDImpl.class);
     
    @Autowired
    private ProductInvDAO productMasterDAO;
 
    @Override
    public void addProduct(ProductInv user) {
    	logger.debug("ProductInvBD: Adding product.");
        productMasterDAO.addProduct(user);
    }
 
    @Override
    public ProductInv getProduct(Integer productId)  {
        return productMasterDAO.getProduct(productId);
    }
    
    @Override
    public ProductInv getProductByCode(String productCode)  {
        return productMasterDAO.getProductByCode(productCode);
    }
 
    @Override
    public void updateProductInv(ProductInv product)  {
    	logger.debug("ProductInvBD: Updating product.");
        productMasterDAO.updateProductInv(product);
    }
 
    @Override
    public void deleteProductFromInv(Integer productId)  {
        productMasterDAO.deleteProductFromInv(productId);
    }
 
    @Override
    public List<ProductInv> getAllProductInv() {
        return productMasterDAO.getAllProduct();
    }

	@Override
	public List<ProductInv> searchForProductInv(ProductInv product) {
		 return productMasterDAO.searchForProduct(product);
	}
   
}