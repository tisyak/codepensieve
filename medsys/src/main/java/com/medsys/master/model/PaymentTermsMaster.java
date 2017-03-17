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
@Table(name = "m_payment_terms")
public class PaymentTermsMaster {
	
	static Logger logger = LoggerFactory.getLogger(PaymentTermsMaster.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_terms_id", columnDefinition = "serial")
	private Integer paymentTermsId; 
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 20, message = "{error.field.max}")
	@Column(name = "payment_terms_code", length = 20)
	private String paymentTermsCode;
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 250, message = "{error.field.max}")
	@Column(name = "payment_terms_desc", length = 250)
	private String paymentTermsDesc;
			
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	public Integer getPaymentTermsId() {
		return paymentTermsId;
	}

	public void setPaymentTermsId(Integer paymentTermsId) {
		this.paymentTermsId = paymentTermsId;
	}

	public String getPaymentTermsCode() {
		return paymentTermsCode;
	}

	public void setPaymentTermsCode(String paymentTermsCode) {
		this.paymentTermsCode = paymentTermsCode;
	}

	public String getPaymentTermsDesc() {
		return paymentTermsDesc;
	}

	public void setPaymentTermsDesc(String paymentTermsDesc) {
		this.paymentTermsDesc = paymentTermsDesc;
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
		result = prime * result + ((paymentTermsCode == null) ? 0 : paymentTermsCode.hashCode());
		result = prime * result + ((paymentTermsId == null) ? 0 : paymentTermsId.hashCode());
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
		PaymentTermsMaster other = (PaymentTermsMaster) obj;
		if (paymentTermsCode == null) {
			if (other.paymentTermsCode != null)
				return false;
		} else if (!paymentTermsCode.equals(other.paymentTermsCode))
			return false;
		if (paymentTermsId == null) {
			if (other.paymentTermsId != null)
				return false;
		} else if (!paymentTermsId.equals(other.paymentTermsId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PaymentTermsMaster [paymentTermsId=" + paymentTermsId + ", paymentTermsCode=" + paymentTermsCode
				+ ", paymentTermsDesc=" + paymentTermsDesc + ", updateBy=" + updateBy + ", updateTimestamp="
				+ updateTimestamp + "]";
	}

}
