package com.medsys.product.bd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.product.dao.ProductMasterDAO;
import com.medsys.product.model.ProductMaster;
 
@Service
@Transactional
public class ProductMasterBDImpl implements ProductMasterBD {
    static Logger logger = LoggerFactory.getLogger(ProductMasterBDImpl.class);
     
    @Autowired
    private ProductMasterDAO productMasterDAO;
 
    @Override
    public void addProduct(ProductMaster product) {
    	logger.debug("ProductMasterBD: Adding product.");
        productMasterDAO.addProduct(product);
    }
 
    @Override
    public ProductMaster getProduct(Integer productId)  {
        return productMasterDAO.getProduct(productId);
    }
    
    @Override
    public ProductMaster getProductByCode(String productCode)  {
        return productMasterDAO.getProductByCode(productCode);
    }
 
    @Override
    public void updateProductInMaster(ProductMaster product)  {
    	logger.debug("ProductMasterBD: Updating product.");
        productMasterDAO.updateProductInMaster(product);
    }
 
    @Override
    public void deleteProductFromMaster(Integer productId)  {
        productMasterDAO.deleteProductFromMaster(productId);
    }
 
    @Override
    public List<ProductMaster> getAllProductMaster() {
        return productMasterDAO.getAllProduct();
    }

	@Override
	public List<ProductMaster> searchForProductMaster(ProductMaster product) {
		 return productMasterDAO.searchForProduct(product);
	}
   
}