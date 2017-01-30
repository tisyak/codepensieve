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
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medsys.product.model.ProductInv;


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
	private ProductInv productInv;
	
	@NotNull
	@Column(name = "qty")
	private Integer qty; 
	
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;
	
	

	public OrderProductSet() {
	}

	public OrderProductSet(Integer setId, String productCode, String lotNo, Integer qty) {
		// TODO Complete this Constructor
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

	public ProductInv getProductInv() {
		return productInv;
	}

	public void setProductInv(ProductInv productInv) {
		this.productInv = productInv;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
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
		result = prime * result + ((productInv == null) ? 0 : productInv.hashCode());
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
		if (productInv == null) {
			if (other.productInv != null)
				return false;
		} else if (!productInv.equals(other.productInv))
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
		return "OrderProductSet [orderProductSetId=" + orderProductSetId + ", order=" + orderId + ", productInv="
				+ productInv + ", qty=" + qty + ", updateBy=" + updateBy + ", updateTimestamp=" + updateTimestamp + "]";
	}
	
	

}
