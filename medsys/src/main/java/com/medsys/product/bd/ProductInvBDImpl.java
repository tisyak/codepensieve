package com.medsys.product.bd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.exception.SysException;
import com.medsys.product.dao.ProductInvDAO;
import com.medsys.product.model.ProductInv;
 
@Service
@Transactional
public class ProductInvBDImpl implements ProductInvBD {
    static Logger logger = LoggerFactory.getLogger(ProductInvBDImpl.class);
     
    @Autowired
    private ProductInvDAO productInvDAO;
 
    @Override
    public void addProduct(ProductInv user) {
    	logger.debug("ProductInvBD: Adding product.");
        productInvDAO.addProduct(user);
    }
 
    @Override
    public ProductInv getProduct(Integer productId)  {
        return productInvDAO.getProduct(productId);
    }
    
    @Override
    public ProductInv getProductByCode(String productCode)  {
        return productInvDAO.getProductByCode(productCode);
    }
     
    @Override
	public void discardProduct(String productCode, Integer discardQty) throws SysException {
    	logger.debug("ProductInvBD: Discarding product.");
        productInvDAO.discardProduct(productCode, discardQty);
		
	}

	@Override
	public void engageProduct(String productCode, Integer engageQty) throws SysException {
		logger.debug("ProductInvBD: Engaging product.");
        productInvDAO.engageProduct(productCode, engageQty);
	}

	@Override
	public void disengageProduct(String productCode, Integer releaseQty) throws SysException {
		logger.debug("ProductInvBD: Releasing product.");
        productInvDAO.disengageProduct(productCode, releaseQty);
	}

	@Override
	public void sellProduct(String productCode, Integer saleQty) throws SysException {
		logger.debug("ProductInvBD: update product sale.");
        productInvDAO.sellProduct(productCode, saleQty);
	}
 
	
	@Override
	public void cancelProductSale(String productCode, Integer cancelQty) throws SysException {
		logger.debug("ProductInvBD: cancel product sale.");
        productInvDAO.cancelProductSale(productCode, cancelQty);
	}
 
    @Override
    public List<ProductInv> getAllProductInv() {
        return productInvDAO.getAllProduct();
    }

	@Override
	public List<ProductInv> searchForProductInv(ProductInv product) {
		 return productInvDAO.searchForProduct(product);
	}

	
   
}