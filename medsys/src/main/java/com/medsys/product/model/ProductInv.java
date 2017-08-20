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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "product_inv")
public class ProductInv implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5211433230312247579L;

	static Logger logger = LoggerFactory.getLogger(ProductInv.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_inv_id", columnDefinition = "serial")
	private Integer productInvId; 
	
	@OneToOne
	@JoinColumn(name="product_id",referencedColumnName="product_id")
	private ProductMaster product;

	@NotNull
	@Column(name = "org_qty")
	private Integer orgQty; 
	
	@NotNull
	@Column(name = "price",precision = 15, scale=0)
	private BigDecimal price; 

	@NotNull
	@Column(name = "mrp",precision = 15, scale=0)
	private BigDecimal mrp; 
	
	@NotNull
	@Column(name = "sold_qty")
	private Integer soldQty; 
	
	@NotNull
	@Column(name = "engaged_qty")
	private Integer engagedQty; 
	
	@NotNull
	@Column(name = "discarded_qty")
	private Integer discardedQty; 
	
	@NotNull
	@Column(name = "available_qty")
	private Integer availableQty; 
	
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	public Integer getProductInvId() {
		return productInvId;
	}

	public void setProductInvId(Integer productInvId) {
		this.productInvId = productInvId;
	}

	public ProductMaster getProduct() {
		return product;
	}

	public void setProduct(ProductMaster product) {
		this.product = product;
	}

	public Integer getOrgQty() {
		return orgQty;
	}

	public void setOrgQty(Integer orgQty) {
		this.orgQty = orgQty;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		if (price != null) {
			this.price = price.setScale(0, RoundingMode.HALF_UP);
		} else {
			this.price = price;
		}
	}

	public BigDecimal getMrp() {
		return mrp;
	}

	public void setMrp(BigDecimal mrp) {
		if (mrp != null) {
			this.mrp = mrp.setScale(0, RoundingMode.HALF_UP);
		} else {
			this.mrp = mrp;
		}
	}

	public Integer getSoldQty() {
		return soldQty;
	}

	public void setSoldQty(Integer soldQty) {
		this.soldQty = soldQty;
	}

	public Integer getEngagedQty() {
		return engagedQty;
	}

	public void setEngagedQty(Integer engagedQty) {
		this.engagedQty = engagedQty;
	}

	public Integer getDiscardedQty() {
		return discardedQty;
	}

	public void setDiscardedQty(Integer discardedQty) {
		this.discardedQty = discardedQty;
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
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		ProductInv other = (ProductInv) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductInv [productInvId=" + productInvId + ", product=" + product + ", orgQty=" + orgQty + ", price="
				+ price + ", mrp=" + mrp + ", soldQty=" + soldQty + ", engagedQty=" + engagedQty + ", discardedQty="
				+ discardedQty + ", availableQty=" + availableQty + ", updateBy=" + updateBy + ", updateTimestamp="
				+ updateTimestamp + "]";
	}

}
