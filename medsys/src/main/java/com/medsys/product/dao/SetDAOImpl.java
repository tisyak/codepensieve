package com.medsys.product.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.medsys.common.model.Response;
import com.medsys.product.model.ProductGroup;
import com.medsys.product.model.ProductMaster;
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

	@Override
	public Set getSet(Integer setId) {
		logger.debug("SetDAOImpl.getSet() - [" + setId + "]");
		Query<Set> query = getCurrentSession().createQuery("from Set where setId = :setId",Set.class);
		
		query.setParameter("setId", setId);

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No set found.");
			throw new EmptyResultDataAccessException("Set [" + setId + "] not found", 1);
		} else {

			logger.debug("Set List Size: " + query.getSingleResult());
			Set set = query.getSingleResult();

			return set;
		}
	}
	
	@Override
	public Set getSetWithInstr(Integer setId) {
		logger.debug("SetDAOImpl.getSetWithInstr() - [" + setId + "]");
		Query<Set> query = getCurrentSession().createQuery("from Set st LEFT JOIN FETCH st.instruments  where st.setId = :setId",Set.class);
		
		query.setParameter("setId", setId);

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No set found.");
			throw new EmptyResultDataAccessException("Set [" + setId + "] not found", 1);
		} else {

			logger.debug("Set List Size: " + query.getSingleResult());
			Set set = query.getSingleResult();

			return set;
		}
	}

	@Override
	public Set getSetWithProducts(Integer setId) {
		logger.debug("SetDAOImpl.getSetWithProducts() - [" + setId + "]");
		Query<Set> query = getCurrentSession().createQuery("from Set st LEFT JOIN FETCH st.pdtTemplates  where st.setId = :setId",Set.class);
		
		query.setParameter("setId", setId);

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No set found.");
			throw new EmptyResultDataAccessException("Set [" + setId + "] not found", 1);
		} else {

			logger.debug("Set List Size: " + query.getSingleResult());
			Set set = query.getSingleResult();

			return set;
		}
	}
	
	@Override
	public List<Set> getAllSetsWithProducts() {
		logger.debug("SetDAOImpl.getAllSetsWithProducts()");
		Query<Set> query = getCurrentSession().createQuery("select distinct(st) from Set st LEFT JOIN FETCH st.pdtTemplates",Set.class);

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No sets found.");
			throw new EmptyResultDataAccessException("No sets in system", 1);
		} else {

			logger.debug("Set List Size: " + query.getResultList().size());
			List<Set> lstSet = query.getResultList();

			return lstSet;
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
		return getCurrentSession().createQuery("from Set order by setName asc").getResultList();
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
		Query<Set> query = getCurrentSession()
				.createQuery("from Set where lower(setName) like :setName  order by setName asc",Set.class);

		if (set.getSetName() != null) {
			query.setParameter("setName", "%" + set.getSetName().toLowerCase() + "%");
		} else {
			query.setParameter("setName", set.getSetName());
		}

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No sets found matching current search criteria.");
			return null;
		} else {

			logger.debug("Search Set List Size: " + query.getResultList().size());
			List<Set> list = (List<Set>) query.getResultList();
			return list;
		}
	}

	@Override
	public List<SetPdtTemplate> getAllProductsInSet(Integer setId) {
		logger.debug("Fetching all products in Set: " + setId);

		List<SetPdtTemplate> pdtList = getCurrentSession()
				.createQuery(" from SetPdtTemplate WHERE  setId = " + setId, SetPdtTemplate.class).getResultList();
		logger.debug("pdtList: " + pdtList);
		return pdtList;
	}

	@Override
	public List<ProductMaster> getAllProductsInSetAndGroup(Integer setId, Integer groupId) {
		logger.debug("Fetching all products in Set: " + setId + " and Group: " + groupId);
		String queryForFilteredPdts = "select spt.product from SetPdtTemplate spt ";

		if ((setId != null && !setId.equals(0)) || (groupId != null && !groupId.equals(0))) {
			queryForFilteredPdts += " WHERE ";
		}

		if (setId != null && !setId.equals(0)) {
			queryForFilteredPdts += " spt.setId = " + setId;
		}

		if (groupId != null && !groupId.equals(0)) {

			if (setId != null && !setId.equals(0)) {
				queryForFilteredPdts += " AND ";
			}
			queryForFilteredPdts += " spt.product.group.groupId = " + groupId;
		}
		
		queryForFilteredPdts += " order by spt.product.productCode ";
		
		List<ProductMaster> pdtList = getCurrentSession().createQuery(queryForFilteredPdts,ProductMaster.class).getResultList();
		logger.debug("pdtList: " + pdtList);
		return pdtList;
	}

	@Override
	public Response addProductToSet(SetPdtTemplate newSetPdtTemplate) {
		logger.debug("Adding product to Set: " + newSetPdtTemplate);
		getCurrentSession().save(newSetPdtTemplate);
		// TODO: change return appropriately
		return new Response(true, null);
	}

	@Override
	public SetPdtTemplate getProductInSet(Integer setPdtId) {
		logger.debug("Getting product having productId: " + setPdtId);

		Query<SetPdtTemplate> query = getCurrentSession()
				.createQuery("from SetPdtTemplate where setPdtId = " + setPdtId.toString() + "",SetPdtTemplate.class);
		// query.setParameter("setId", setId.toString());

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("Product not found in set.");
			throw new EmptyResultDataAccessException("SetPdtTemplate [" + setPdtId + "] not found", 1);
		} else {

			logger.debug("SetPdtTemplate List Size: " + query.getResultList().size());
			List<SetPdtTemplate> list = (List<SetPdtTemplate>) query.getResultList();
			SetPdtTemplate product = (SetPdtTemplate) list.get(0);
			return product;
		}
	}

	@Override
	public Response updateProductInSet(SetPdtTemplate product) {
		SetPdtTemplate productToUpdate = getProductInSet(product.getSetPdtId());
		productToUpdate.setQty(product.getQty());
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

	@Override
	public List<ProductGroup> getAllProductGroupForSet(Integer setId) {
		logger.debug("Fetching all products in Set: " + setId);

		List<ProductGroup> pdtList = getCurrentSession()
				.createQuery("select distinct spt.product.group from SetPdtTemplate spt WHERE set_id = " + setId + " order by spt.product.group.groupName", ProductGroup.class).getResultList();
		logger.debug("pdtList: " + pdtList);
		return pdtList;
	}

	@Override
	public List<ProductMaster> getMiscellaneousProducts(Integer setId) {
		logger.debug("Fetching all Miscellaneous products: ");
		String queryForFilteredPdts = "select spt.product from SetPdtTemplate spt  WHERE ";

		queryForFilteredPdts += " spt.setId = " + setId;

		queryForFilteredPdts += " order by spt.product.productCode ";
		
		List<ProductMaster> pdtList = getCurrentSession().createQuery(queryForFilteredPdts,ProductMaster.class).getResultList();
		logger.debug("pdtList: " + pdtList);
		return pdtList;
	}

}