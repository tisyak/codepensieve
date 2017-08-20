package com.medsys.product.bd;

import java.util.List;

import com.medsys.common.model.Response;
import com.medsys.product.model.ProductGroup;
import com.medsys.product.model.ProductMaster;
import com.medsys.product.model.Set;
import com.medsys.product.model.SetPdtTemplate;

public interface SetBD {

	public void addSet(Set set);
	
	public Set getSet(Integer setId);
	
	public Set getSetWithInstr(Integer setId);
	
	public Set getSetWithProducts(Integer setId);

	public void updateSet(Set set);

	public void deleteSet(Integer setId);

	public List<Set> getAllSet();
	
	public List<Set> searchForSet(Set set);

	public Response addProductToSet(SetPdtTemplate product);

	public Response updateProductInSet(SetPdtTemplate product);

	public Response deleteProductFromSet(SetPdtTemplate product);

	public SetPdtTemplate getProductInSet(Integer productId);
	
	public List<SetPdtTemplate> getAllProductsInSet(Integer setId);

	public List<ProductMaster> getAllProductsInSetAndGroup(Integer setId, Integer groupId);

	public List<ProductGroup> getAllProductGroupForSet(Integer setId);

	public Set getSetWithAlteredPricelist(Integer setId, Integer pricelistPercent);
	
	public List<Set>  getQuotation(Integer pricelistPercent);

	

	

}