package com.medsys.product.bd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.common.model.Response;
import com.medsys.product.dao.ProductGroupDAO;
import com.medsys.product.model.ProductGroup;
 
@Service
@Transactional
public class ProductGroupBDImpl implements ProductGroupBD {
    static Logger logger = LoggerFactory.getLogger(ProductGroupBDImpl.class);
     
    @Autowired
    private ProductGroupDAO productGroupDAO;
 
    @Override
    public Response addProductGroup(ProductGroup productGroup) {
    	logger.debug("ProductGroupBD: Adding productGroup.");
        return productGroupDAO.addProductGroup(productGroup);
    }
 
    @Override
    public ProductGroup getProductGroup(Integer groupId)  {
        return productGroupDAO.getProductGroup(groupId);
    }
    
    @Override
    public Response updateProductGroup(ProductGroup productGroup)  {
    	logger.debug("ProductGroupBD: Updating productGroup.");
        return productGroupDAO.updateProductGroup(productGroup);
    }
 
    @Override
    public Response deleteProductGroup(Integer groupId)  {
        return productGroupDAO.deleteProductGroup(groupId);
    }
 
    @Override
    public List<ProductGroup> getAllProductGroup() {
        return productGroupDAO.getAllProductGroup();
    }

	@Override
	public List<ProductGroup> searchForProductGroup(ProductGroup productGroup) {
		 return productGroupDAO.searchForProductGroup(productGroup);
	}
   
}