package com.medsys.orders.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medsys.product.model.ProductMaster;


@Entity
@Table(name = "order_product_set")
public class OrderProductSet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4745389593451287026L;
	
	static Logger logger = LoggerFactory.getLogger(OrderProductSet.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_set_id", columnDefinition = "serial")
	private Integer orderProductSetId; 

	@Column(name = "order_id")
	private Integer orderId;
	
	@ManyToOne
	@JoinColumn(name="product_id",referencedColumnName="product_id")
	private ProductMaster product;
	
	@NotNull
	@Column(name = "qty")
	private Integer qty; 
	
	@Transient
	private Integer availableQty; 
	
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;
	
	

	public OrderProductSet() {
	}

	public OrderProductSet(Integer orderId,Integer productSetId,ProductMaster product, Integer qty) {
		this.orderId = orderId;
		this.orderProductSetId =  productSetId;
		this.product = product;
		this.qty = qty;

	}

	public Integer getOrderProductSetId() {
		return orderProductSetId;
	}

	public void setOrderProductSetId(Integer orderProductSetId) {
		this.orderProductSetId = orderProductSetId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public ProductMaster getProduct() {
		return product;
	}

	public void setProduct(ProductMaster product) {
		this.product = product;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(Integer availableQty) {
		this.availableQty = availableQty;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Timestamp getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Timestamp updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((orderProductSetId == null) ? 0 : orderProductSetId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderProductSet other = (OrderProductSet) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (orderProductSetId == null) {
			if (other.orderProductSetId != null)
				return false;
		} else if (!orderProductSetId.equals(other.orderProductSetId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderProductSet [orderProductSetId=" + orderProductSetId + ", order=" + orderId + ", product="
				+ product + ", qty=" + qty + ", availableQty=" + availableQty + ", updateBy=" + updateBy + ", updateTimestamp=" + updateTimestamp + "]";
	}
	
	

}
