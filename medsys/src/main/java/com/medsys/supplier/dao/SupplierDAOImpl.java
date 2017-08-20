package com.medsys.supplier.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.medsys.common.model.Response;
import com.medsys.supplier.model.Supplier;
import com.medsys.util.EpSystemError;

@Repository
public class SupplierDAOImpl implements SupplierDAO {

	static Logger logger = LoggerFactory.getLogger(SupplierDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Response addSupplier(Supplier supplier) {
		logger.debug("Saving supplier to DB.");
		try {
			getCurrentSession().save(supplier);
		} catch (HibernateException he) {
			logger.debug("Unable to save object. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Saved supplier: " + supplier);
		return new Response(true, null);
	}

	@Override
	public Supplier getSupplier(Integer supplierId) throws UsernameNotFoundException {
		logger.debug("SupplierDAOImpl.getSupplier() - [" + supplierId + "]");
		Query<Supplier> query = getCurrentSession()
				.createQuery("from Supplier where supplier_id = '" + supplierId + "'",Supplier.class);
		// query.setParameter("supplierId", supplierId.toString());

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No supplier found.");
			throw new UsernameNotFoundException("Supplier [" + supplierId + "] not found");
		} else {

			logger.debug("Supplier List not empty. ");
			Supplier userObject = query.getSingleResult();

			return userObject;
		}
	}

	@Override
	public Response deleteSupplier(Integer supplierId) throws UsernameNotFoundException {
		Supplier supplier = getSupplier(supplierId);
		try {
			if (supplier != null) {
				getCurrentSession().delete(supplier);
			} else {
				throw new UsernameNotFoundException("Supplier Username : [" + supplierId + "] not found");
			}
		} catch (HibernateException he) {
			logger.debug("Unable to delete object. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Deleted Successfully: " + supplier);
		return new Response(true, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Supplier> getAllSuppliers() {
		return getCurrentSession().createQuery("from Supplier order by name asc").getResultList();
	}

	@Override
	public Response updateSupplier(Supplier supplier) throws UsernameNotFoundException {
		Supplier supplierToUpdate = getSupplier(supplier.getSupplierId());
		supplierToUpdate.setEmail(supplier.getEmail());
		supplierToUpdate.setMobileNo(supplier.getMobileNo());
		supplierToUpdate.setName(supplier.getName());
		supplierToUpdate.setGstin(supplier.getGstin());
		supplierToUpdate.setAddress(supplier.getAddress());
		supplierToUpdate.setCity(supplier.getCity());
		supplierToUpdate.setPincode(supplier.getPincode());
		try {
			getCurrentSession().update(supplierToUpdate);
		} catch (HibernateException he) {
			logger.debug("Unable to save object. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Saved supplier: " + supplier);
		return new Response(true, null);
	}

	@Override
	public List<Supplier> searchForSuppliers(Supplier supplier) {
		logger.debug("SupplierDAOImpl.searchForSuppliers() - [" + supplier.toString() + "]");
		Query<Supplier> query = getCurrentSession()
				.createQuery("from Supplier where lower(name) like :name OR mobileNo like :mobileNo order by name asc",Supplier.class);

		if (supplier.getName() != null) {
			query.setParameter("name", "%" + supplier.getName().toLowerCase() + "%");
		} else {
			query.setParameter("name", supplier.getName());
		}

		if (supplier.getMobileNo() != null) {
			query.setParameter("mobileNo", "%" + supplier.getMobileNo().toLowerCase() + "%");
		} else {
			query.setParameter("mobileNo", supplier.getMobileNo());
		}

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No suppliers found matching current search criteria.");
			return null;
		} else {

			logger.debug("Search Supplier List Size: " + query.getResultList().size());
			List<Supplier> list = (List<Supplier>) query.getResultList();
			return list;
		}
	}


}