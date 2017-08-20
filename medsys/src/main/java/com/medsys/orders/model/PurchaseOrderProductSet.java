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

import com.medsys.product.model.ProductMaster;


@Entity
@Table(name = "po_product_set")
public class PurchaseOrderProductSet implements Serializable,Comparable<PurchaseOrderProductSet> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4745389593451287026L;
	
	static Logger logger = LoggerFactory.getLogger(PurchaseOrderProductSet.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_set_id", columnDefinition = "serial")
	private Integer productSetId; 

	@Column(name = "po_id")
	private Integer purchaseOrderId;
	
	@ManyToOne
	@JoinColumn(name="product_id",referencedColumnName="product_id")
	private ProductMaster product;
	
	@NotNull
	@Column(name = "qty")
	private Integer qty; 
		
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;
	
	

	public PurchaseOrderProductSet() {
	}

	public PurchaseOrderProductSet(Integer purchaseOrderId,Integer productSetId,ProductMaster product, Integer qty) {
		this.purchaseOrderId = purchaseOrderId;
		this.productSetId =  productSetId;
		this.product = product;
		this.qty = qty;

	}

	public Integer getProductSetId() {
		return productSetId;
	}

	public void setProductSetId(Integer productSetId) {
		this.productSetId = productSetId;
	}

	public Integer getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Integer purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
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
		result = prime * result + ((purchaseOrderId == null) ? 0 : purchaseOrderId.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((productSetId == null) ? 0 : productSetId.hashCode());
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
		PurchaseOrderProductSet other = (PurchaseOrderProductSet) obj;
		if (purchaseOrderId == null) {
			if (other.purchaseOrderId != null)
				return false;
		} else if (!purchaseOrderId.equals(other.purchaseOrderId))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (productSetId == null) {
			if (other.productSetId != null)
				return false;
		} else if (!productSetId.equals(other.productSetId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "POProductSet [productSetId=" + productSetId + ", purchaseOrder=" + purchaseOrderId + ", product="
				+ product + ", qty=" + qty + ", updateBy=" + updateBy + ", updateTimestamp=" + updateTimestamp + "]";
	}

	@Override
	public int compareTo(PurchaseOrderProductSet aThat) {
		return this.product.getProductCode().compareTo(aThat.product.getProductCode());
	}
	
	

}
