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

import com.medsys.product.model.ProductMaster;

@Repository
public class ProductMasterDAOImpl implements ProductMasterDAO {

	static Logger logger = LoggerFactory.getLogger(ProductMasterDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void addProduct(ProductMaster product) {
		logger.debug("Saving product to DB.");
		getCurrentSession().save(product);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductMaster getProduct(Integer productId) {
		logger.debug("ProductMasterDAOImpl.getProduct() - [" + productId + "]");
		Query<ProductMaster> query = getCurrentSession().createQuery("from ProductMaster where productId = " + productId + "");
		// query.productParameter("productId", productId.toString());

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No ProductMaster found.");
			throw new EmptyResultDataAccessException("ProductMaster [" + productId + "] not found", 1);
		} else {

			logger.debug("ProductMaster List Size: " + query.getResultList().size());
			List<ProductMaster> list = (List<ProductMaster>) query.getResultList();
			ProductMaster product = (ProductMaster) list.get(0);

			return product;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ProductMaster getProductByCode(String productCode) {
		logger.debug("ProductMasterDAOImpl.getProductByCode() - [" + productCode + "]");
		Query<ProductMaster> query = getCurrentSession().createQuery("from ProductMaster where productCode = '" + productCode + "'");
		// query.productParameter("productId", productId.toString());

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No ProductMaster found.");
			throw new EmptyResultDataAccessException("ProductMaster [" + productCode + "] not found", 1);
		} else {

			logger.debug("ProductMaster List Size: " + query.getResultList().size());
			List<ProductMaster> list = (List<ProductMaster>) query.getResultList();
			ProductMaster product = (ProductMaster) list.get(0);

			return product;
		}
	}

	@Override
	public void deleteProductFromMaster(Integer productId) {
		ProductMaster product = getProduct(productId);
		if (product != null) {
			getCurrentSession().delete(product);
		} else {
			throw new EmptyResultDataAccessException("ProductMaster [" + productId + "] not found", 1);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ProductMaster> getAllProduct() {
		return getCurrentSession().createQuery("from ProductMaster order by productCode asc").getResultList();
	}

	@Override
	public void updateProductInMaster(ProductMaster product) {
		ProductMaster productToUpdate = getProduct(product.getProductId());
		productToUpdate.setProductDesc(product.getProductDesc());
		getCurrentSession().update(productToUpdate);
	}

	@Override
	public List<ProductMaster> searchForProduct(ProductMaster product) {
		logger.debug("ProductMasterDAOImpl.searchForProduct() - [" + product.toString() + "]");
		Query<ProductMaster> query = getCurrentSession()
				.createQuery("from ProductMaster where lower(productCode) like :productCode  order by productCode asc");

		if (product.getProductCode() != null) {
			query.setParameter("productCode", "%" + product.getProductCode().toLowerCase() + "%");
		} else {
			query.setParameter("productCode", product.getProductCode());
		}

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No products found matching current search criteria.");
			return null;
		} else {

			logger.debug("Search ProductMaster List Size: " + query.getResultList().size());
			List<ProductMaster> list = (List<ProductMaster>) query.getResultList();
			return list;
		}
	}


}