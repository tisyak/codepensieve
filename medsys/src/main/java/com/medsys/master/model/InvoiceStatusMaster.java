package com.medsys.master.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "m_invoice_status")
public class InvoiceStatusMaster {
	
	static Logger logger = LoggerFactory.getLogger(InvoiceStatusMaster.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_status_id", columnDefinition = "serial")
	private Integer invoiceStatusId; 
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 20, message = "{error.field.max}")
	@Column(name = "invoice_status_code", length = 5)
	private String invoiceStatusCode;
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 250, message = "{error.field.max}")
	@Column(name = "invoice_status_desc", length = 250)
	private String invoiceStatusDesc;
			
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	public Integer getInvoiceStatusId() {
		return invoiceStatusId;
	}

	public void setInvoiceStatusId(Integer invoiceStatusId) {
		this.invoiceStatusId = invoiceStatusId;
	}

	public String getInvoiceStatusCode() {
		return invoiceStatusCode;
	}

	public void setInvoiceStatusCode(String invoiceStatusCode) {
		this.invoiceStatusCode = invoiceStatusCode;
	}

	public String getInvoiceStatusDesc() {
		return invoiceStatusDesc;
	}

	public void setInvoiceStatusDesc(String invoiceStatusDesc) {
		this.invoiceStatusDesc = invoiceStatusDesc;
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
		result = prime * result + ((invoiceStatusCode == null) ? 0 : invoiceStatusCode.hashCode());
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
		InvoiceStatusMaster other = (InvoiceStatusMaster) obj;
		if (invoiceStatusCode == null) {
			if (other.invoiceStatusCode != null)
				return false;
		} else if (!invoiceStatusCode.equals(other.invoiceStatusCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvoiceStatusMaster [invoiceStatusId=" + invoiceStatusId + ", invoiceStatusCode=" + invoiceStatusCode
				+ ", invoiceStatusDesc=" + invoiceStatusDesc + ", updateBy=" + updateBy + ", updateTimestamp="
				+ updateTimestamp + "]";
	}

}
