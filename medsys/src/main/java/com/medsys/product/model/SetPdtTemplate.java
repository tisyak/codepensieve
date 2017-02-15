package com.medsys.product.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

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
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="set_id",referencedColumnName="set_id")
	private Set set;
		
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="product_id",referencedColumnName="product_id")
	@OrderBy("productCode")
	private ProductMaster product;
	
	@Column(name = "qty")
	private Integer qty; 
	
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	public Integer getSetPdtId() {
		return setPdtId;
	}

	public void setSetPdtId(Integer setPdtId) {
		this.setPdtId = setPdtId;
	}

	public Set getSet() {
		return set;
	}

	public void setSet(Set set) {
		this.set = set;
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
	public String toString() {
		return "SetPdtTemplate [setPdtId=" + setPdtId + ", set=" + set + ", product=" + product + ", qty=" + qty
				+ ", updateBy=" + updateBy + ", updateTimestamp=" + updateTimestamp + "]";
	}
	
	

}
