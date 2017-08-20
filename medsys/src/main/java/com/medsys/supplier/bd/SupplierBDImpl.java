package com.medsys.supplier.bd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.common.model.Response;
import com.medsys.supplier.dao.SupplierDAO;
import com.medsys.supplier.model.Supplier;
 
@Service
@Transactional
public class SupplierBDImpl implements SupplierBD {
    static Logger logger = LoggerFactory.getLogger(SupplierBDImpl.class);
     
    @Autowired
    private SupplierDAO supplierDAO;
 
    @Override
    public Response addSupplier(Supplier supplier) {
    	logger.debug("SupplierBD: Adding supplier.");
        return supplierDAO.addSupplier(supplier);
    }
 
    @Override
    public Supplier getSupplier(Integer supplierId)  {
        return supplierDAO.getSupplier(supplierId);
    }
 
    @Override
    public Response updateSupplier(Supplier supplier)  {
    	logger.debug("SupplierBD: Updating supplier.");
    	return supplierDAO.updateSupplier(supplier);
    }
 
    @Override
    public Response deleteSupplier(Integer supplierId)  {
    	return supplierDAO.deleteSupplier(supplierId);
    }
 
    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierDAO.getAllSuppliers();
    }

	@Override
	public List<Supplier> searchForSuppliers(Supplier supplier) {
		 return supplierDAO.searchForSuppliers(supplier);
	}

   
}