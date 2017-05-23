package com.medsys.product.dao;

import java.util.List;

import org.hibernate.HibernateException;
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
import com.medsys.util.EpSystemError;

@Repository
public class ProductGroupDAOImpl implements ProductGroupDAO {

	static Logger logger = LoggerFactory.getLogger(ProductGroupDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Response addProductGroup(ProductGroup productGroup) {
		logger.debug("Saving product group to DB.");
		try {
			getCurrentSession().save(productGroup);
		} catch (HibernateException he) {
			logger.debug("Unable to save object. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Saved group: " + productGroup);
		return new Response(true, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductGroup getProductGroup(Integer groupId) {
		logger.debug("ProductGroupDAOImpl.getProduct() - [" + groupId + "]");
		Query<ProductGroup> query = getCurrentSession()
				.createQuery("from ProductGroup where groupId = " + groupId + "");
		// query.productParameter("groupId", groupId.toString());

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No productGroup found.");
			throw new EmptyResultDataAccessException("ProductGroup [" + groupId + "] not found", 1);
		} else {

			logger.debug("ProductGroup List Size: " + query.getResultList().size());
			List<ProductGroup> list = (List<ProductGroup>) query.getResultList();
			ProductGroup product = (ProductGroup) list.get(0);

			return product;
		}
	}

	@Override
	public Response deleteProductGroup(Integer groupId) {
		ProductGroup productGroup = getProductGroup(groupId);
		try {
			if (productGroup != null) {
				getCurrentSession().delete(productGroup);
			} else {
				throw new EmptyResultDataAccessException("ProductGroup [" + groupId + "] not found", 1);
			}
		} catch (HibernateException he) {
			logger.debug("Unable to delete object. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Deleted Successfully: " + productGroup);
		return new Response(true, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ProductGroup> getAllProductGroup() {
		return getCurrentSession().createQuery("from ProductGroup order by groupName asc").getResultList();
	}

	@Override
	public Response updateProductGroup(ProductGroup productGroup) {
		ProductGroup productGroupToUpdate = getProductGroup(productGroup.getGroupId());
		productGroupToUpdate.setGroupName(productGroup.getGroupName());
		productGroupToUpdate.setGroupDesc(productGroup.getGroupDesc());
		try {
			getCurrentSession().update(productGroupToUpdate);
		} catch (HibernateException he) {
			logger.debug("Unable to save object. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Saved productGroup: " + productGroup);
		return new Response(true, null);
	}

	@Override
	public List<ProductGroup> searchForProductGroup(ProductGroup productGroup) {
		logger.debug("ProductGroupDAOImpl.searchForProduct() - [" + productGroup.toString() + "]");
		Query<ProductGroup> query = getCurrentSession().createQuery(
				"from ProductGroup where lower(groupName) like :groupName  order by productName asc",
				ProductGroup.class);

		if (productGroup.getGroupName() != null) {
			query.setParameter("groupName", "%" + productGroup.getGroupName().toLowerCase() + "%");
		} else {
			query.setParameter("groupName", productGroup.getGroupName());
		}

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No products found matching current search criteria.");
			return null;
		} else {

			logger.debug("Search ProductGroup List Size: " + query.getResultList().size());
			List<ProductGroup> list = (List<ProductGroup>) query.getResultList();
			return list;
		}
	}

}