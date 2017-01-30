package com.medsys.product.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.medsys.common.model.Response;
import com.medsys.product.model.Set;
import com.medsys.product.model.SetPdtTemplate;
import com.medsys.util.EpSystemError;

@Repository
public class SetDAOImpl implements SetDAO {

	static Logger logger = LoggerFactory.getLogger(SetDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void addSet(Set set) {
		logger.debug("Saving set to DB.");
		getCurrentSession().save(set);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set getSet(Integer setId) {
		logger.debug("SetDAOImpl.getSet() - [" + setId + "]");
		Query query = getCurrentSession().createQuery("from Set where setId = " + setId + "");
		// query.setParameter("setId", setId.toString());

		logger.debug(query.toString());
		if (query.list().size() == 0) {
			logger.debug("No user found.");
			throw new EmptyResultDataAccessException("Set [" + setId + "] not found", 1);
		} else {

			logger.debug("Set List Size: " + query.list().size());
			List<Set> list = (List<Set>) query.list();
			Set set = (Set) list.get(0);

			return set;
		}
	}

	@Override
	public void deleteSet(Integer setId) {
		Set set = getSet(setId);
		if (set != null) {
			getCurrentSession().delete(set);
		} else {
			throw new EmptyResultDataAccessException("Set [" + setId + "] not found", 1);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Set> getAllSet() {
		return getCurrentSession().createQuery("from Set order by setName asc").list();
	}

	@Override
	public void updateSet(Set set) {
		Set setToUpdate = getSet(set.getSetId());
		setToUpdate.setSetName(set.getSetName());
		setToUpdate.setSetDesc(set.getSetDesc());
		getCurrentSession().update(setToUpdate);
	}

	@Override
	public List<Set> searchForSet(Set set) {
		logger.debug("SetDAOImpl.searchForSet() - [" + set.toString() + "]");
		Query query = getCurrentSession()
				.createQuery("from Set where lower(setName) like :setName  order by setName asc");

		if (set.getSetName() != null) {
			query.setString("setName", "%" + set.getSetName().toLowerCase() + "%");
		} else {
			query.setString("setName", set.getSetName());
		}

		logger.debug(query.toString());
		if (query.list().size() == 0) {
			logger.debug("No sets found matching current search criteria.");
			return null;
		} else {

			logger.debug("Search Set List Size: " + query.list().size());
			List<Set> list = (List<Set>) query.list();
			return list;
		}
	}

	@Override
	public List<SetPdtTemplate> getAllProductsInSet(Integer setId) {
		logger.debug("Fetching all products in Set: " + setId);
		return getCurrentSession().createQuery(
				"from SetPdtTemplate " + " where setId = " + setId + " order by product.productCode asc ")
				.list();
	}

	@Override
	public Response addProductToSet(SetPdtTemplate newSetPdtTemplate) {
		logger.debug("Adding product to Set: " + newSetPdtTemplate);
		getCurrentSession().save(newSetPdtTemplate);
		// TODO: change return appropriately
		return new Response(true, null);
	}

	@Override
	public SetPdtTemplate getProductInSet(Integer productId) {
		logger.debug("Getting product having productId: " + productId);

		Query query = getCurrentSession()
				.createQuery("from SetPdtTemplate where productId = " + productId.toString() + "");
		// query.setParameter("setId", setId.toString());

		logger.debug(query.toString());
		if (query.list().size() == 0) {
			logger.debug("Product not found in set.");
			throw new EmptyResultDataAccessException("SetPdtTemplate [" + productId + "] not found", 1);
		} else {

			logger.debug("SetPdtTemplate List Size: " + query.list().size());
			List<SetPdtTemplate> list = (List<SetPdtTemplate>) query.list();
			SetPdtTemplate product = (SetPdtTemplate) list.get(0);
			return product;
		}
	}

	@Override
	public Response updateProuctInSet(SetPdtTemplate product) {
		SetPdtTemplate productToUpdate = getProductInSet(product.getSetPdtId());
		getCurrentSession().update(productToUpdate);
		// TODO: change return appropriately
		return new Response(true, null);
	}

	@Override
	public Response deleteProductFromSet(SetPdtTemplate product) {
		SetPdtTemplate existingSetPdtTemplate = getProductInSet(product.getSetPdtId());

		if (existingSetPdtTemplate != null) {
			getCurrentSession().delete(existingSetPdtTemplate);
			return new Response(true, null);
		}

		return new Response(false, EpSystemError.NO_RECORD_FOUND);

	}

}