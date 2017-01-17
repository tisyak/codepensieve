package com.medsys.orders.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "invoice")
public class Invoice {
	
	static Logger logger = LoggerFactory.getLogger(Invoice.class);

	@Id
	@Column(name = "invoice_no")
	private Integer invoiceNo; 
	
	@OneToOne
	@JoinColumn(name="order_id",referencedColumnName="order_id")
	private Orders order;
	
	@Column(name = "invoice_date")
	@Type(type="date")
	private Date invoiceDate; 
	
	@Size(max = 100, message = "{error.field.max}")
	@Column(name = "payment_terms", length = 100)
	private String paymentTerms;
	
	@NotBlank(message = "{error.field.empty}")
	@Column(name = "invoice_status", length = 20)
	private String invoiceStatus; 
	
	@NotNull
	@Column(name = "total_amount")
	private BigDecimal totalAmount; 
	
	@NotNull
	@Column(name = "total_vat")
	private BigDecimal totalVat; 
	
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	public Integer getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(Integer invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalVat() {
		return totalVat;
	}

	public void setTotalVat(BigDecimal totalVat) {
		this.totalVat = totalVat;
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
		return "Invoice [invoiceNo=" + invoiceNo + ", order=" + order + ", invoiceDate=" + invoiceDate
				+ ", paymentTerms=" + paymentTerms + ", invoiceStatus=" + invoiceStatus + ", totalAmount=" + totalAmount
				+ ", totalVat=" + totalVat + ", updateBy=" + updateBy + ", updateTimestamp=" + updateTimestamp + "]";
	}
	
	

}
