package com.medsys.product.dao;

import java.util.List;

import com.medsys.common.model.Response;
import com.medsys.product.model.ProductGroup;

public interface ProductGroupDAO {
 
    public Response addProductGroup(ProductGroup productGroup);
 
    public ProductGroup getProductGroup(Integer groupId);
    
    public Response updateProductGroup(ProductGroup productGroup);
 
    public Response deleteProductGroup(Integer groupId);
 
    public List<ProductGroup> getAllProductGroup();

	public List<ProductGroup> searchForProductGroup(ProductGroup productGroup);

	
	
}