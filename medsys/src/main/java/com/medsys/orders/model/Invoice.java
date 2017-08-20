package com.medsys.orders.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medsys.customer.model.Customer;
import com.medsys.master.model.InvoiceStatusMaster;
import com.medsys.master.model.PaymentTermsMaster;

@Entity
@Table(name = "invoice")
public class Invoice {

	static Logger logger = LoggerFactory.getLogger(Invoice.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id", columnDefinition = "serial")
	private Integer invoiceId;

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

	@Size(max = 50, message = "{error.field.max}")
	@Column(name = "patient_info", length = 100)
	private String patientInfo;

	@Size(max = 250, message = "{error.field.max}")
	@Column(name = "reference_source", length = 250)
	private String refSource;

	@NotNull(message = "{error.field.empty}")
	@Column(name = "invoice_date")
	@Type(type = "date")
	private Date invoiceDate;

	@ManyToOne
	@JoinColumn(name = "payment_terms_id", referencedColumnName = "payment_terms_id")
	private PaymentTermsMaster paymentTerms;

	@ManyToOne
	@JoinColumn(name = "invoice_status_id", referencedColumnName = "invoice_status_id")
	private InvoiceStatusMaster invoiceStatus;

	@Formula("(select SUM(invPdt.vat_amount) from invoice_product invPdt where invPdt.invoice_id= invoice_id)")
	private BigDecimal totalVat;

	@Formula("(select SUM(invPdt.cgst_amount) from invoice_product invPdt where invPdt.invoice_id= invoice_id)")
	private BigDecimal totalCgst;

	@Formula("(select SUM(invPdt.sgst_amount) from invoice_product invPdt where invPdt.invoice_id= invoice_id)")
	private BigDecimal totalSgst;

	@Transient
	private BigDecimal totalGst;
	
	@Formula("(select SUM(invPdt.total_before_tax) from invoice_product invPdt where invPdt.invoice_id= invoice_id)")
	private BigDecimal totalBeforeTax;

	@Formula("(select SUM(invPdt.total) from invoice_product invPdt where invPdt.invoice_id= invoice_id)")
	private BigDecimal totalAmount;

	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	@Column(name = "bill_to_patient")
	private boolean billToPatient;
	
	@Column(name = "print_mrp")
	private boolean printMRP;

	@Transient
	private String billVersion;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	@OneToMany(mappedBy = "invoiceId")
	@javax.persistence.OrderBy("product ASC")
	java.util.Set<InvoiceProduct> products;

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
		this.patientName = StringUtils.capitalize(patientName);
	}

	public String getPatientInfo() {
		return patientInfo;
	}

	public void setPatientInfo(String patientInfo) {
		this.patientInfo = patientInfo;
	}

	public boolean isBillToPatient() {
		return billToPatient;
	}

	public boolean getBillToPatient() {
		return billToPatient;
	}

	public void setBillToPatient(boolean billToPatient) {
		this.billToPatient = billToPatient;
	}
	

	public boolean isPrintMRP() {
		return printMRP;
	}
	
	public boolean getPrintMRP() {
		return printMRP;
	}

	public void setPrintMRP(boolean printMRP) {
		this.printMRP = printMRP;
	}

	public String getBillVersion() {
		return billVersion;
	}

	public void setBillVersion(String billVersion) {
		this.billVersion = billVersion;
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

	public BigDecimal getTotalVat() {
		return totalVat;
	}

	public void setTotalVat(BigDecimal totalVat) {
		if (totalVat != null) {
			this.totalVat = totalVat.setScale(0, RoundingMode.HALF_UP);
		} else {
			this.totalVat = totalVat;
		}
	}

	public BigDecimal getTotalCgst() {
		return totalCgst;
	}

	public void setTotalCgst(BigDecimal totalCgst) {
		if (totalCgst != null) {
			this.totalCgst = totalCgst.setScale(0, RoundingMode.HALF_UP);
		} else {
			this.totalCgst = totalCgst;
		}
	}

	public BigDecimal getTotalSgst() {
		return totalSgst;
	}

	public void setTotalSgst(BigDecimal totalSgst) {
		if (totalSgst != null) {
			this.totalSgst = totalSgst.setScale(0, RoundingMode.HALF_UP);
		} else {
			this.totalSgst = totalSgst;
		}
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		if (totalAmount != null) {
			this.totalAmount = totalAmount.setScale(0, RoundingMode.HALF_UP);
		} else {
			this.totalAmount = totalAmount;
		}
	}

	public BigDecimal getTotalGst() {
		BigDecimal tempTotalGst = totalCgst;

		if (tempTotalGst != null) {
			tempTotalGst = totalCgst.add(totalSgst);
		} else {
			tempTotalGst = totalSgst;
		}

		return tempTotalGst;
	}

	public BigDecimal getTotalBeforeTax() {
		return totalBeforeTax;
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

	public java.util.Set<InvoiceProduct> getProducts() {
		return products;
	}

	public void setProducts(java.util.Set<InvoiceProduct> products) {
		this.products = products;
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
				+ customer + ", patientName=" + patientName + ", patientInfo=" + patientInfo + ", refSource="
				+ refSource + ", invoiceDate=" + invoiceDate + ", paymentTerms=" + paymentTerms + ", invoiceStatus="
				+ invoiceStatus + ", totalVat=" + totalVat + ", totalCgst=" + totalCgst + ", totalSgst=" + totalSgst
				+ ", totalGst=" + totalGst + ", totalBeforeTax=" + totalBeforeTax + ", totalAmount=" + totalAmount
				+ ", updateBy=" + updateBy + ", billToPatient=" + billToPatient
				+ ", printMRP=" + printMRP + ", billVersion=" + billVersion + ", updateTimestamp=" + updateTimestamp
				+ "]";
	}

}
