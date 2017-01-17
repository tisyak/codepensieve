package com.medsys.customer.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;

@Entity
@Table(name = "customer_contact")
public class CustomerContact {

	static Logger logger = LoggerFactory.getLogger(CustomerContact.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_contact_id", columnDefinition = "serial")
	private Integer customerContactId; 
	
	@Column(name = "customer_id", columnDefinition = "uuid")
	private UUID customerId; 
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 100, message = "{error.field.max}")
	@Column(name = "name", length = 100)
	private String name;
			
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 100, message = "{error.field.max}")
	@Column(name = "email", length = 100)
	private String email;
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 10, message = "{error.field.max}")
	@Column(name = "mobile_no", length = 10)
	private String mobileNo;
	
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;
	
	
	public Integer getCustomerContactId() {
		return customerContactId;
	}

	public void setCustomerContactId(Integer customerContactId) {
		this.customerContactId = customerContactId;
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

	public UUID getCustomerId() {
		return customerId;
	}

	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = StringUtils.capitalize(name);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerContactId == null) ? 0 : customerContactId.hashCode());
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
		CustomerContact other = (CustomerContact) obj;
		if (customerContactId == null) {
			if (other.customerContactId != null)
				return false;
		} else if (!customerContactId.equals(other.customerContactId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerContact [customerContactId=" + customerContactId + ", customerId=" + customerId + ", name="
				+ name + ", email=" + email + ", mobileNo=" + mobileNo + ", updateBy=" + updateBy + ", updateTimestamp="
				+ updateTimestamp + "]";
	}


}