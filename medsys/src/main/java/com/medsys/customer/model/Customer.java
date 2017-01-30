package com.medsys.customer.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;

@Entity
@Table(name = "customer")
public class Customer {

	static Logger logger = LoggerFactory.getLogger(Customer.class);

	@Id
	@GeneratedValue//(generator = "uuid2")
	//@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "customer_id")
	@org.hibernate.annotations.Type(type="pg-uuid")
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
	
	
	@Size(max = 512, message = "{error.field.max}")
	@Column(name = "address", length = 512)
	private String address;
	
	
	@Size(max = 512, message = "{error.field.max}")
	@Column(name = "city", length = 512)
	private String city;
	
	
	@Size(max = 512, message = "{error.field.max}")
	@Column(name = "pincode", length = 512)
	private String pincode;
	
	 @OneToMany( mappedBy = "customerId", cascade = CascadeType.REFRESH)
	 List<CustomerContact> contacts;

	
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

		
	public List<CustomerContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<CustomerContact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;

		if (o instanceof Customer) {
			final Customer other = (Customer) o;
			return Objects.equal(getCustomerId(), other.getCustomerId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getCustomerId());
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", name=" + name
				+ ", email=" + email + ", mobileNo=" + mobileNo + ", address="
				+ address + ", city=" + city + ", pincode=" + pincode
				+ ", contacts=" + contacts
				+ "]";
	}

	


}