package com.medsys.orders.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medsys.customer.model.Customer;
import com.medsys.master.model.InvoiceStatusMaster;
import com.medsys.master.model.PaymentTermsMaster;
import com.medsys.master.model.TaxMaster;

@Entity
@Table(name = "invoice")
public class Invoice {
	
	static Logger logger = LoggerFactory.getLogger(Invoice.class);

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id", columnDefinition = "serial")
	private Integer invoiceId;

	@NotBlank(message = "{error.field.empty}")
	@Size(max = 20, message = "{error.field.max}")
	@Column(name = "invoice_no", length = 20)
	private String invoiceNo;
	
	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "order_id")
	private Orders order;
	
	@ManyToOne
	@JoinColumn(columnDefinition = "uuid", name = "customer_id", referencedColumnName = "customer_id")
	private Customer customer;

	@Size(max = 100, message = "{error.field.max}")
	@Column(name = "patient_name", length = 100)
	private String patientName;

	@Size(max = 250, message = "{error.field.max}")
	@Column(name = "reference_source", length = 250)
	private String refSource;

	@NotNull(message = "{error.field.empty}")
	@Column(name = "invoice_date")
	@Type(type="date")
	private Date invoiceDate; 
	
	@ManyToOne
	@JoinColumn(name = "payment_terms_id", referencedColumnName = "payment_terms_id")
	private PaymentTermsMaster paymentTerms;
	
	@ManyToOne
	@JoinColumn(name = "invoice_status_id", referencedColumnName = "invoice_status_id")
	private InvoiceStatusMaster invoiceStatus; 
	
	@NotNull
	@Column(name = "total_amount")
	private BigDecimal totalAmount; 
	
	@ManyToOne
	@JoinColumn(name = "vat_type", referencedColumnName = "tax_id")
	private TaxMaster vatType;
	
	@Column(name = "total_vat")
	private BigDecimal totalVat; 
	
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	public Invoice() {}

	public Invoice(boolean generateInvoiceNo) {
		super();
		if (generateInvoiceNo) {
			Date date = new Date();
			String modifiedDate = new SimpleDateFormat("yyMMdd").format(date);
			this.setInvoiceNo("INV-" + modifiedDate + "-" + RandomStringUtils.random(4, true, true));
		}
	}
	
	
	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getRefSource() {
		return refSource;
	}

	public void setRefSource(String refSource) {
		this.refSource = refSource;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public PaymentTermsMaster getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(PaymentTermsMaster paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public InvoiceStatusMaster getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(InvoiceStatusMaster invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public TaxMaster getVatType() {
		return vatType;
	}

	public void setVatType(TaxMaster vatType) {
		this.vatType = vatType;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((invoiceId == null) ? 0 : invoiceId.hashCode());
		result = prime * result + ((invoiceNo == null) ? 0 : invoiceNo.hashCode());
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
		Invoice other = (Invoice) obj;
		if (invoiceId == null) {
			if (other.invoiceId != null)
				return false;
		} else if (!invoiceId.equals(other.invoiceId))
			return false;
		if (invoiceNo == null) {
			if (other.invoiceNo != null)
				return false;
		} else if (!invoiceNo.equals(other.invoiceNo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Invoice [invoiceId=" + invoiceId + ", invoiceNo=" + invoiceNo + ", order=" + order + ", customer="
				+ customer + ", patientName=" + patientName + ", refSource=" + refSource + ", invoiceDate="
				+ invoiceDate + ", paymentTerms=" + paymentTerms + ", invoiceStatus=" + invoiceStatus + ", totalAmount="
				+ totalAmount + ", vatType=" + vatType + ", totalVat=" + totalVat + ", updateBy=" + updateBy
				+ ", updateTimestamp=" + updateTimestamp + "]";
	}

}
