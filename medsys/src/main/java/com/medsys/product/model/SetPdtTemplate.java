package com.medsys.product.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "set_pdt_template")
public class SetPdtTemplate implements Serializable,Comparable<SetPdtTemplate> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -547583099563214158L;

	static Logger logger = LoggerFactory.getLogger(Set.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "set_pdt_id", columnDefinition = "serial")
	private Integer setPdtId;

	@Column(name = "set_id")
	private Integer setId;

	@OneToOne
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	@OrderBy("productCode")
	private ProductMaster product;
	
	@Formula("(select pdtInv.price from product_inv pdtInv where pdtInv.product_id= product_id)")
	private BigDecimal pricePerUnit;

	@Column(name = "qty")
	private Integer qty;

	@Formula("(select pdtInv.available_qty from product_inv pdtInv where pdtInv.product_id= product_id)")
	private Integer availableQty;

	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;
	

	public SetPdtTemplate() {
	}

	public SetPdtTemplate(Integer setId,Integer setPdtId,ProductMaster product, Integer qty) {
		this.setId = setId;
		this.setPdtId =  setPdtId;
		this.product = product;
		this.qty = qty;

	}


	public Integer getSetPdtId() {
		return setPdtId;
	}

	public void setSetPdtId(Integer setPdtId) {
		this.setPdtId = setPdtId;
	}

	public ProductMaster getProduct() {
		return product;
	}

	public void setProduct(ProductMaster product) {
		this.product = product;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(BigDecimal pricePerUnit) {
		if (pricePerUnit != null) {
			this.pricePerUnit = pricePerUnit.setScale(0, RoundingMode.HALF_UP);
		} else {
			this.pricePerUnit = pricePerUnit;
		}
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
	public String toString() {
		return "SetPdtTemplate [setPdtId=" + setPdtId + ", setId=" + setId + ", product=" + product
				+ ", pricePerUnit=" + pricePerUnit + ", qty=" + qty + ", availableQty=" + availableQty + ", updateBy="
				+ updateBy + ", updateTimestamp=" + updateTimestamp + "]";
	}
	
	@Override
	public int compareTo(SetPdtTemplate aThat) {

		return this.product.compareTo(aThat.product);
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((setId == null) ? 0 : setId.hashCode());
		result = prime * result + ((setPdtId == null) ? 0 : setPdtId.hashCode());
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
		SetPdtTemplate other = (SetPdtTemplate) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (setId == null) {
			if (other.setId != null)
				return false;
		} else if (!setId.equals(other.setId))
			return false;
		if (setPdtId == null) {
			if (other.setPdtId != null)
				return false;
		} else if (!setPdtId.equals(other.setPdtId))
			return false;
		return true;
	}
	
	

}
