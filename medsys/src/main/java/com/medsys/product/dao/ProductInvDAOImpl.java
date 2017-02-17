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

import com.medsys.product.model.ProductInv;

@Repository
public class ProductInvDAOImpl implements ProductInvDAO {

	static Logger logger = LoggerFactory.getLogger(ProductInvDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void addProduct(ProductInv product) {
		logger.debug("Saving product to DB.");
		getCurrentSession().save(product);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductInv getProduct(Integer productId) {
		logger.debug("ProductInvDAOImpl.getProduct() - [" + productId + "]");
		Query<ProductInv> query = getCurrentSession().createQuery("from ProductInv where product.productId = " + productId + "");
		// query.productParameter("productId", productId.toString());

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("ProductInv [" + productId + "] not found");
			throw new EmptyResultDataAccessException("ProductInv [" + productId + "] not found", 1);
		} else {

			logger.debug("ProductInv List Size: " + query.getResultList().size());
			List<ProductInv> list = (List<ProductInv>) query.getResultList();
			ProductInv product = (ProductInv) list.get(0);

			return product;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ProductInv getProductByCode(String productCode) {
		logger.debug("ProductInvDAOImpl.getProductByCode() - [" + productCode + "]");
		Query<ProductInv> query = getCurrentSession().createQuery("from ProductInv where product.productCode = '" + productCode + "'");
		// query.productParameter("productId", productId.toString());

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("ProductInv [" + productCode + "] not found");
			throw new EmptyResultDataAccessException("ProductInv [" + productCode + "] not found", 1);
		} else {
			logger.debug("ProductInv List Size: " + query.getResultList().size());
			ProductInv product  = query.getSingleResult();
			return product;
		}
	}

	@Override
	public void deleteProductFromInv(Integer productId) {
		ProductInv product = getProduct(productId);
		if (product != null) {
			getCurrentSession().delete(product);
		} else {
			throw new EmptyResultDataAccessException("ProductInv [" + productId + "] not found", 1);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ProductInv> getAllProduct() {
		return getCurrentSession().createQuery("from ProductInv order by productName asc").getResultList();
	}

	@Override
	public void updateProductInv(ProductInv product) {
		ProductInv productToUpdate = getProduct(product.getProductInvId());
		//TODO: Change this update properly!!!
		getCurrentSession().update(productToUpdate);
	}

	@Override
	public List<ProductInv> searchForProduct(ProductInv product) {
		logger.debug("ProductInvDAOImpl.searchForProduct() - [" + product.toString() + "]");
		Query<ProductInv> query = getCurrentSession()
				.createQuery("from ProductInv where lower(product.productCode) like :productCode  order by product.productCode asc");

		if (product.getProduct().getProductCode() != null) {
			query.setParameter("productCode", "%" + product.getProduct().getProductCode().toLowerCase() + "%");
		} else {
			query.setParameter("productCode", product.getProduct().getProductCode());
		}

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No products found matching current search criteria.");
			return null;
		} else {

			logger.debug("Search ProductInv List Size: " + query.getResultList().size());
			List<ProductInv> list = (List<ProductInv>) query.getResultList();
			return list;
		}
	}


}