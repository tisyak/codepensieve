package com.medsys.product.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "set_pdt_template")
public class SetPdtTemplate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -547583099563214158L;

	static Logger logger = LoggerFactory.getLogger(Set.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "set_pdt_id", columnDefinition = "serial")
	private Integer setPdtId;

	@ManyToOne
	@JoinColumn(name = "set_id", referencedColumnName = "set_id")
	private Set parentSet;

	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	@OrderBy("productCode")
	private ProductMaster product;

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

	public SetPdtTemplate(Set set,Integer setPdtId,ProductMaster product, Integer qty) {
		this.parentSet = set;
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

	public Set getParentSet() {
		return parentSet;
	}

	public void setParentSet(Set parentSet) {
		this.parentSet = parentSet;
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
	public String toString() {
		return "SetPdtTemplate [setPdtId=" + setPdtId + ", parentSet=" + parentSet + ", product=" + product + ", qty=" + qty
				+ ", availableQty=" + availableQty + ", updateBy=" + updateBy + ", updateTimestamp=" + updateTimestamp
				+ "]";
	}

}
