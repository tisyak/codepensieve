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
import org.springframework.transaction.annotation.Transactional;

import com.medsys.common.model.Response;
import com.medsys.exception.SysException;
import com.medsys.product.model.ProductInv;
import com.medsys.util.EpSystemError;

@Repository
public class ProductInvDAOImpl implements ProductInvDAO {

	static Logger logger = LoggerFactory.getLogger(ProductInvDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Response addProduct(ProductInv product) {
		logger.debug("Saving product to DB.");
		try {
			getCurrentSession().save(product);
		} catch (HibernateException he) {
			logger.debug("Unable to save object. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Saved product: " + product);
		return new Response(true, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductInv getProduct(Integer productId) {
		logger.debug("ProductInvDAOImpl.getProductByProductId() - [" + productId + "]");
		Query<ProductInv> query = getCurrentSession()
				.createQuery("from ProductInv where product.productId = " + productId + "");
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
		Query<ProductInv> query = getCurrentSession()
				.createQuery("from ProductInv where product.productCode = '" + productCode + "'");
		// query.productParameter("productId", productId.toString());

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("ProductInv [" + productCode + "] not found");
			throw new EmptyResultDataAccessException("ProductInv [" + productCode + "] not found", 1);
		} else {
			logger.debug("ProductInv List Size: " + query.getResultList().size());
			ProductInv product = query.getSingleResult();
			return product;
		}
	}

	/*
	 * Currently delete feature disabled in system
	 * 
	 * @Override public void deleteProductFromInv(Integer productId) {
	 * ProductInv product = getProduct(productId); if (product != null) {
	 * getCurrentSession().delete(product); } else { throw new
	 * EmptyResultDataAccessException("ProductInv [" + productId +
	 * "] not found", 1); } }
	 */

	@Override
	@SuppressWarnings("unchecked")
	public List<ProductInv> getAllProduct() {
		return getCurrentSession().createQuery("from ProductInv order by productName asc").getResultList();
	}

	private Response updateProductInv(ProductInv product) {
		try {
			getCurrentSession().update(product);
		} catch (HibernateException he) {
			logger.debug("Unable to save object. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Saved product: " + product);
		return new Response(true, null);
	}

	@Override
	@Transactional
	public void discardProduct(String productCode, Integer discardQty) throws SysException {
		logger.debug("Discard " + discardQty + " of product having id: " + productCode);
		ProductInv productToUpdate = getProductByCode(productCode);
		Integer availableQty = productToUpdate.getAvailableQty();
		Integer discardedQty = productToUpdate.getDiscardedQty();
		if (availableQty >= discardQty) {
			productToUpdate.setAvailableQty(availableQty - discardQty);
			productToUpdate.setDiscardedQty(discardedQty + discardQty);
			updateProductInv(productToUpdate);
		} else {
			logger.error("Discard qty " + discardQty + " exceeds available qty " + availableQty
					+ " of product having code: " + productCode);
			throw new SysException("Product Code", productToUpdate.getProduct().getProductCode(),
					EpSystemError.PI_DSCRD_QTY_EXCEEDS_AVBL);
		}

	}

	@Override
	@Transactional
	public void engageProduct(String productCode, Integer engageQty) throws SysException {
		logger.debug("Engage " + engageQty + " of product having id: " + productCode);
		ProductInv productToUpdate = getProductByCode(productCode);
		Integer availableQty = productToUpdate.getAvailableQty();
		if (availableQty >= engageQty) {
			productToUpdate.setAvailableQty(availableQty - engageQty);
			productToUpdate.setEngagedQty(productToUpdate.getEngagedQty() + engageQty);
			updateProductInv(productToUpdate);
		} else {
			logger.error("Engage qty " + engageQty + " exceeds available qty " + availableQty
					+ " of product having code: " + productCode);
			throw new SysException("Product Code", productToUpdate.getProduct().getProductCode(),
					EpSystemError.PI_ENGG_QTY_EXCEEDS_AVBL);
		}
	}

	@Override
	@Transactional
	public void disengageProduct(String productCode, Integer releaseQty) throws SysException {
		logger.debug("Release " + releaseQty + " of product having id: " + productCode);
		ProductInv productToUpdate = getProductByCode(productCode);
		Integer availableQty = productToUpdate.getAvailableQty();
		Integer engageQty = productToUpdate.getEngagedQty();
		if (engageQty <= releaseQty) {
			productToUpdate.setEngagedQty(engageQty - releaseQty);
			productToUpdate.setAvailableQty(availableQty + releaseQty);
			updateProductInv(productToUpdate);
		} else {
			logger.error("Release qty " + releaseQty + " exceeds engaged qty " + engageQty + " of product having code: "
					+ productCode);
			throw new SysException("Product Code", productToUpdate.getProduct().getProductCode(),
					EpSystemError.PI_RLES_QTY_EXCEEDS_ENGG);
		}
	}

	@Override
	@Transactional
	public void sellProduct(String productCode, Integer saleQty) throws SysException {
		logger.debug("Updating sale qty " + saleQty + " of product having id: " + productCode);
		ProductInv productToUpdate = getProductByCode(productCode);
		Integer availableQty = productToUpdate.getAvailableQty();
		if (availableQty >= saleQty) {
			productToUpdate.setAvailableQty(availableQty - saleQty);
			productToUpdate.setSoldQty(productToUpdate.getSoldQty() + saleQty);
			updateProductInv(productToUpdate);
		} else {
			logger.error("Sale qty " + saleQty + " exceeds available qty " + availableQty + " of product having code: "
					+ productCode);
			throw new SysException("Product Code", productToUpdate.getProduct().getProductCode(),
					EpSystemError.PI_SALE_QTY_EXCEEDS_AVBL);
		}

	}

	@Override
	@Transactional
	public void cancelProductSale(String productCode, Integer cancelledQty) throws SysException {
		logger.debug("Cancelling sale qty " + cancelledQty + " of product having id: " + productCode);
		ProductInv productToUpdate = getProductByCode(productCode);
		Integer availableQty = productToUpdate.getAvailableQty();
		Integer soldQty = productToUpdate.getSoldQty();
		if (soldQty >= cancelledQty) {
			productToUpdate.setSoldQty(soldQty - cancelledQty);
			productToUpdate.setAvailableQty(availableQty + cancelledQty);
			updateProductInv(productToUpdate);
		} else {
			logger.error("Sale Cancel qty " + cancelledQty + " exceeds sold qty " + soldQty
					+ " of product having code: " + productCode);
			throw new SysException("Product Code", productToUpdate.getProduct().getProductCode(),
					EpSystemError.PI_CANCEL_QTY_EXCEEDS_SOLD);
		}

	}

	@Override
	public List<ProductInv> searchForProduct(ProductInv product) {
		logger.debug("ProductInvDAOImpl.searchForProduct() - [" + product.toString() + "]");
		Query<ProductInv> query = getCurrentSession().createQuery(
				"from ProductInv where lower(product.productCode) like :productCode  order by product.productCode asc");

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

	@Override
	public Response updateProduct(ProductInv productInv) {
		return updateProductInv(productInv);
	}

	@Override
	public Response deleteProduct(ProductInv productInv) {
		try {
			getCurrentSession().delete(productInv);
		} catch (HibernateException he) {
			logger.debug("Unable to delete object. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Deleted Successfully: " + productInv.getProduct().getProductCode());
		return new Response(true, null);
	}

}