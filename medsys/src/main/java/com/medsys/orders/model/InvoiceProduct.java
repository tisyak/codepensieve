package com.medsys.orders.model;

import java.math.BigDecimal;
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

import com.medsys.master.model.TaxMaster;
import com.medsys.product.model.ProductMaster;

@Entity
@Table(name = "invoice_product")
public class InvoiceProduct {

	static Logger logger = LoggerFactory.getLogger(InvoiceProduct.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_product_id", columnDefinition = "serial")
	private Integer invoiceProductId;
	
	@Column(name = "invoice_id")
	private Integer invoiceId;

	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	private ProductMaster product;

	@NotNull
	@Column(name = "rate_per_unit")
	private BigDecimal ratePerUnit;

	@NotNull
	@Column(name = "total")
	private BigDecimal totalPrice;

	@NotNull
	@Column(name = "vat_amount")
	private BigDecimal vatAmount;

	@ManyToOne
	@JoinColumn(name = "vat_type", referencedColumnName = "tax_id")
	private TaxMaster vatType;

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

	public Integer getInvoiceProductId() {
		return invoiceProductId;
	}

	public void setInvoiceProductId(Integer invoiceProductId) {
		this.invoiceProductId = invoiceProductId;
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public ProductMaster getProduct() {
		return product;
	}

	public void setProduct(ProductMaster product) {
		this.product = product;
	}

	public BigDecimal getRatePerUnit() {
		return ratePerUnit;
	}

	public void setRatePerUnit(BigDecimal ratePerUnit) {
		this.ratePerUnit = ratePerUnit;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	public TaxMaster getVatType() {
		return vatType;
	}

	public void setVatType(TaxMaster vatType) {
		this.vatType = vatType;
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
		result = prime * result + ((invoiceId == null) ? 0 : invoiceId.hashCode());
		result = prime * result + ((invoiceProductId == null) ? 0 : invoiceProductId.hashCode());
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
		InvoiceProduct other = (InvoiceProduct) obj;
		if (invoiceId == null) {
			if (other.invoiceId != null)
				return false;
		} else if (!invoiceId.equals(other.invoiceId))
			return false;
		if (invoiceProductId == null) {
			if (other.invoiceProductId != null)
				return false;
		} else if (!invoiceProductId.equals(other.invoiceProductId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvoiceProduct [invoiceProductId=" + invoiceProductId + ", invoiceId=" + invoiceId + ", product="
				+ product + ", ratePerUnit=" + ratePerUnit + ", totalPrice=" + totalPrice + ", vatAmount=" + vatAmount
				+ ", vatType=" + vatType + ", qty=" + qty + ", updateBy=" + updateBy + ", updateTimestamp="
				+ updateTimestamp + "]";
	}
	
	
	

}
