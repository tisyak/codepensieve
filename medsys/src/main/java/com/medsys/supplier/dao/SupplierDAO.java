package com.medsys.supplier.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.medsys.common.model.Response;
import com.medsys.supplier.model.Supplier;
 
public interface SupplierDAO {
 
    public Response addSupplier(Supplier supplier);
 
    public Supplier getSupplier(Integer supplierId) throws UsernameNotFoundException;
 
    public Response updateSupplier(Supplier supplier) throws UsernameNotFoundException;
 
    public Response deleteSupplier(Integer supplierId) throws UsernameNotFoundException;
 
    public List<Supplier> getAllSuppliers();

	public List<Supplier> searchForSuppliers(Supplier supplier);
	
	
	
	
}