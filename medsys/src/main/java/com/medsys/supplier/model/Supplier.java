package com.medsys.supplier.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;

@Entity
@Table(name = "supplier")
public class Supplier implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8163961530934562334L;

	static Logger logger = LoggerFactory.getLogger(Supplier.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "supplier_id", columnDefinition = "serial")
	private Integer supplierId; 
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 100, message = "{error.field.max}")
	@Column(name = "name", length = 100)
	private String name;
			
	@Size(max = 100, message = "{error.field.max}")
	@Column(name = "email", length = 100)
	private String email;
	
	@Size(max = 10, message = "{error.field.max}")
	@Column(name = "mobile_no", length = 10)
	private String mobileNo;
	
	
	@Size(max = 512, message = "{error.field.max}")
	@Column(name = "address", length = 512)
	private String address;
	
	
	@Size(max = 512, message = "{error.field.max}")
	@Column(name = "city", length = 512)
	private String city;
	
	
	@Size(max = 512, message = "{error.field.max}")
	@Column(name = "pincode", length = 512)
	private String pincode;
	
	@Size(max = 50, message = "{error.field.max}")
	@Column(name = "gstin", length = 100)
	private String gstin;

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getName() {
		
		if(name!=null){
			name = StringUtils.capitalize(name);
		}
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

		
	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
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

	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;

		if (o instanceof Supplier) {
			final Supplier other = (Supplier) o;
			return Objects.equal(getSupplierId(), other.getSupplierId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getSupplierId());
	}

	@Override
	public String toString() {
		return "Supplier [supplierId=" + supplierId + ", name=" + name + ", email=" + email + ", mobileNo=" + mobileNo
				+ ", address=" + address + ", city=" + city + ", pincode=" + pincode + ", gstin=" + gstin
				+ ", updateBy=" + updateBy + ", updateTimestamp=" + updateTimestamp + "]";
	}

	


}