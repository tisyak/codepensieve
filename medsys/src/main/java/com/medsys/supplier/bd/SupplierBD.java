package com.medsys.supplier.bd;

import java.util.List;

import com.medsys.common.model.Response;
import com.medsys.supplier.model.Supplier;

public interface SupplierBD {

	public Response addSupplier(Supplier supplier);
	
	public Supplier getSupplier(Integer supplierId);

	public Response updateSupplier(Supplier supplier);

	public Response deleteSupplier(Integer supplierId);

	public List<Supplier> getAllSuppliers();
	
	public List<Supplier> searchForSuppliers(Supplier supplier);

}