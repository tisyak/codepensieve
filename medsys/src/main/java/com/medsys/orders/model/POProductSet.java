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
public class POProductSet implements Serializable,Comparable<POProductSet> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4745389593451287026L;
	
	static Logger logger = LoggerFactory.getLogger(POProductSet.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_set_id", columnDefinition = "serial")
	private Integer poProductSetId; 

	@Column(name = "po_id")
	private Integer poId;
	
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
	
	

	public POProductSet() {
	}

	public POProductSet(Integer poId,Integer productSetId,ProductMaster product, Integer qty) {
		this.poId = poId;
		this.poProductSetId =  productSetId;
		this.product = product;
		this.qty = qty;

	}

	public Integer getPOProductSetId() {
		return poProductSetId;
	}

	public void setPOProductSetId(Integer poProductSetId) {
		this.poProductSetId = poProductSetId;
	}

	public Integer getPOId() {
		return poId;
	}

	public void setPOId(Integer poId) {
		this.poId = poId;
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
		result = prime * result + ((poId == null) ? 0 : poId.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((poProductSetId == null) ? 0 : poProductSetId.hashCode());
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
		POProductSet other = (POProductSet) obj;
		if (poId == null) {
			if (other.poId != null)
				return false;
		} else if (!poId.equals(other.poId))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (poProductSetId == null) {
			if (other.poProductSetId != null)
				return false;
		} else if (!poProductSetId.equals(other.poProductSetId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "POProductSet [poProductSetId=" + poProductSetId + ", po=" + poId + ", product="
				+ product + ", qty=" + qty + ", updateBy=" + updateBy + ", updateTimestamp=" + updateTimestamp + "]";
	}

	@Override
	public int compareTo(POProductSet aThat) {
		return this.product.getProductCode().compareTo(aThat.product.getProductCode());
	}
	
	

}
