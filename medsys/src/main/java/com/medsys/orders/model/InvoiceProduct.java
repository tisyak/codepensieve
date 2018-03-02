package com.medsys.orders.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medsys.master.model.TaxMaster;
import com.medsys.product.model.ProductMaster;

@Entity
@Table(name = "invoice_product")
public class InvoiceProduct  implements Serializable,Comparable<InvoiceProduct>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 250, message = "{error.field.max}")
	@Column(name = "product_desc", length = 250)
	private String productDesc;

	@Formula("(select pdtInv.mrp from product_inv pdtInv where pdtInv.product_id= product_id)")
	private BigDecimal mrp;
	
	@NotNull
	@Column(name = "rate_per_unit",precision = 15, scale = 1)
	private BigDecimal ratePerUnit;

	@NotNull
	@Column(name = "qty")
	private Integer qty;
	
	@Column(name = "discount",precision = 15, scale=1)
	private BigDecimal discount;
	
	@NotNull
	@Column(name = "total_before_tax",precision = 15, scale=1)
	private BigDecimal totalBeforeTax;

	@Column(name = "vat_amount",precision = 15, scale=1)
	private BigDecimal vatAmount;

	@ManyToOne
	@JoinColumn(name = "vat_type", referencedColumnName = "tax_id")
	private TaxMaster vatType;
	
	@Column(name = "cgst_amount",precision = 15, scale=1)
	private BigDecimal cgstAmount;

	@ManyToOne
	@JoinColumn(name = "cgst_type", referencedColumnName = "tax_id")
	private TaxMaster cgstType;
	
	@Column(name = "sgst_amount",precision = 15, scale=1)
	private BigDecimal sgstAmount;

	@ManyToOne
	@JoinColumn(name = "sgst_type", referencedColumnName = "tax_id")
	private TaxMaster sgstType;
	

	@NotNull
	@Column(name = "total",precision = 15, scale=1)
	private BigDecimal totalPrice;

	
	@Formula("(select pdtInv.available_qty from product_inv pdtInv where pdtInv.product_id= product_id)")
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
	
	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
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

	public BigDecimal getRatePerUnit() {
		return ratePerUnit;
	}

	public void setRatePerUnit(BigDecimal ratePerUnit) {
		if (ratePerUnit != null) {
			this.ratePerUnit = ratePerUnit.setScale(0, RoundingMode.HALF_UP);
		} else {
			this.ratePerUnit = ratePerUnit;
		}
	}

	public BigDecimal getTotalBeforeTax() {
		return totalBeforeTax;
	}

	public void setTotalBeforeTax(BigDecimal totalBeforeTax) {
		if (totalBeforeTax != null) {
			this.totalBeforeTax = totalBeforeTax.setScale(0, RoundingMode.HALF_UP);
		} else {
			this.totalBeforeTax = totalBeforeTax;
		}
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		if (totalPrice != null) {
			this.totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
		} else {
			this.totalPrice = totalPrice;
		}
	}

	public BigDecimal getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(BigDecimal vatAmount) {
		if (vatAmount != null) {
			this.vatAmount = vatAmount.setScale(2, RoundingMode.HALF_UP);
		} else {
			this.vatAmount = vatAmount;
		}
	}

	public TaxMaster getVatType() {
		return vatType;
	}

	public void setVatType(TaxMaster vatType) {
		this.vatType = vatType;
	}
	
	public BigDecimal getCgstAmount() {
		return cgstAmount;
	}

	public void setCgstAmount(BigDecimal cgstAmount) {
		if (cgstAmount != null) {
			this.cgstAmount = cgstAmount.setScale(2, RoundingMode.HALF_UP);
		} else {
			this.cgstAmount = cgstAmount;
		}
	}

	public TaxMaster getCgstType() {
		return cgstType;
	}

	public void setCgstType(TaxMaster cgstType) {
		this.cgstType = cgstType;
	}

	public BigDecimal getSgstAmount() {
		return sgstAmount;
	}

	public void setSgstAmount(BigDecimal sgstAmount) {
		if (sgstAmount != null) {
			this.sgstAmount = sgstAmount.setScale(2, RoundingMode.HALF_UP);
		} else {
			this.sgstAmount = sgstAmount;
		}
	}

	public TaxMaster getSgstType() {
		return sgstType;
	}

	public void setSgstType(TaxMaster sgstType) {
		this.sgstType = sgstType;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		if (discount != null) {
			this.discount = discount.setScale(0, RoundingMode.HALF_UP);
		} else {
			this.discount = discount;
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
				+ product + ", mrp=" + mrp + ", ratePerUnit=" + ratePerUnit + ", qty=" + qty + ", discount=" + discount
				+ ", totalBeforeTax=" + totalBeforeTax + ", vatAmount=" + vatAmount + ", vatType=" + vatType
				+ ", cgstAmount=" + cgstAmount + ", cgstType=" + cgstType + ", sgstAmount=" + sgstAmount + ", sgstType="
				+ sgstType + ", totalPrice=" + totalPrice + ", availableQty=" + availableQty + ", updateBy=" + updateBy
				+ ", updateTimestamp=" + updateTimestamp + "]";
	}

	@Override
	public int compareTo(InvoiceProduct aThat) {
		return this.product.getProductCode().compareTo(aThat.product.getProductCode());
	}

}
