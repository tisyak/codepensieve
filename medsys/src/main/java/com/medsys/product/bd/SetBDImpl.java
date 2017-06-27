package com.medsys.product.bd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.common.model.Response;
import com.medsys.product.dao.SetDAO;
import com.medsys.product.model.ProductGroup;
import com.medsys.product.model.ProductInv;
import com.medsys.product.model.Set;
import com.medsys.product.model.SetPdtTemplate;
 
@Service
@Transactional
public class SetBDImpl implements SetBD {
    static Logger logger = LoggerFactory.getLogger(SetBDImpl.class);
     
    @Autowired
    private SetDAO setDAO;
    
    @Autowired
    private ProductInvBD productInvBD;
 
    @Override
    public void addSet(Set set) {
    	logger.debug("SetBD: Adding set.");
        setDAO.addSet(set);
    }
 
    @Override
    public Set getSet(Integer setId)  {
        return setDAO.getSet(setId);
    }
 
    @Override
    public void updateSet(Set set)  {
    	logger.debug("SetBD: Updating set.");
        setDAO.updateSet(set);
    }
 
    @Override
    public void deleteSet(Integer setId)  {
        setDAO.deleteSet(setId);
    }
 
    @Override
    public List<Set> getAllSet() {
        return setDAO.getAllSet();
    }

	@Override
	public List<Set> searchForSet(Set set) {
		 return setDAO.searchForSet(set);
	}
	
	@Override
	public List<SetPdtTemplate> getAllProductsInSet(Integer setId) {
		logger.debug("Get All products in Set: " + setId);
		List<SetPdtTemplate> setPdtTemplates =  setDAO.getAllProductsInSet(setId);
		/*
		for(SetPdtTemplate pdtTemplate: setPdtTemplates){
			try{
				ProductInv productInv = productInvBD.getProductByCode(pdtTemplate.getProduct().getProductCode());
				pdtTemplate.setAvailableQty(productInv.getAvailableQty());
			}catch(EmptyResultDataAccessException e){ 
				logger.debug("Product "+ pdtTemplate.getProduct().getProductCode() +" not found in Inventory");
				pdtTemplate.setAvailableQty(0);
			}
			
		}*/
		
		return setPdtTemplates;
	}

	@Override
	public List<SetPdtTemplate> getAllProductsInSetAndGroup(Integer setId,Integer groupId) {
		logger.debug("Get All products in Set: " + setId + " and Group: " + groupId);
		List<SetPdtTemplate> setPdtTemplates =  setDAO.getAllProductsInSetAndGroup(setId,groupId);
		
		for(SetPdtTemplate pdtTemplate: setPdtTemplates){
			try{
				ProductInv productInv = productInvBD.getProductByCode(pdtTemplate.getProduct().getProductCode());
				pdtTemplate.setAvailableQty(productInv.getAvailableQty());
			}catch(EmptyResultDataAccessException e){ 
				logger.debug("Product "+ pdtTemplate.getProduct().getProductCode() +" not found in Inventory");
				pdtTemplate.setAvailableQty(0);
			}
			
		}
		
		return setPdtTemplates;
	}

	@Override
	public Response addProductToSet(SetPdtTemplate newSetPdtTemplate) {
		logger.debug("ADD product to Set: " + newSetPdtTemplate);
		return setDAO.addProductToSet(newSetPdtTemplate);
	}

	@Override
	public SetPdtTemplate getProductInSet(Integer productId) {
		logger.debug("GET product for productId: " + productId);
		return setDAO.getProductInSet(productId);
	}

	@Override
	public Response updateProductInSet(SetPdtTemplate product) {
		logger.debug("UPDATE product: " + product);
		return setDAO.updateProductInSet(product);
	}

	@Override
	public Response deleteProductFromSet(SetPdtTemplate product) {
		logger.debug("DELETE product: " + product);
		return setDAO.deleteProductFromSet(product);
	}

	@Override
	public List<ProductGroup> getAllProductGroupForSet(Integer setId) {
		return setDAO.getAllProductGroupForSet(setId);
		
	}


   
}