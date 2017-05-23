package com.medsys.company.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "company_info")
public class Company implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4592326906219471138L;

	static Logger logger = LoggerFactory.getLogger(Company.class);

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "company_id", columnDefinition = "uuid")
	private UUID companyId; 
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 100, message = "{error.field.max}")
	@Column(name = "company_name", length = 100)
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
	
	
	@Size(max = 50, message = "{error.field.max}")
	@Column(name = "city", length = 50)
	private String city;
	
	
	@Size(max = 6, message = "{error.field.max}")
	@Column(name = "pincode", length = 6)
	private String pincode;
	
	@Size(max = 50, message = "{error.field.max}")
	@Column(name = "shop_est_lc_no", length = 50)
	private String shopEstLcNo;
	

	@Size(max = 50, message = "{error.field.max}")
	@Column(name = "vat_tin_no", length = 50)
	private String vatTinNo;
	

	@Size(max = 50, message = "{error.field.max}")
	@Column(name = "cst_no", length = 50)
	private String cstNo;
	

	@Size(max = 50, message = "{error.field.max}")
	@Column(name = "fda_20b_lc_no", length = 50)
	private String fda20bLcNo;
	
	@Size(max = 50, message = "{error.field.max}")
	@Column(name = "fda_21b_lc_no", length = 50)
	private String fda21bLcNo;

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		Company.logger = logger;
	}

	public UUID getCompanyId() {
		return companyId;
	}

	public void setCompanyId(UUID companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getShopEstLcNo() {
		return shopEstLcNo;
	}

	public void setShopEstLcNo(String shopEstLcNo) {
		this.shopEstLcNo = shopEstLcNo;
	}

	public String getVatTinNo() {
		return vatTinNo;
	}

	public void setVatTinNo(String vatTinNo) {
		this.vatTinNo = vatTinNo;
	}

	public String getCstNo() {
		return cstNo;
	}

	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}

	public String getFda20bLcNo() {
		return fda20bLcNo;
	}

	public void setFda20bLcNo(String fda20bLcNo) {
		this.fda20bLcNo = fda20bLcNo;
	}

	public String getFda21bLcNo() {
		return fda21bLcNo;
	}

	public void setFda21bLcNo(String fda21bLcNo) {
		this.fda21bLcNo = fda21bLcNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((cstNo == null) ? 0 : cstNo.hashCode());
		result = prime * result + ((fda20bLcNo == null) ? 0 : fda20bLcNo.hashCode());
		result = prime * result + ((fda21bLcNo == null) ? 0 : fda21bLcNo.hashCode());
		result = prime * result + ((shopEstLcNo == null) ? 0 : shopEstLcNo.hashCode());
		result = prime * result + ((vatTinNo == null) ? 0 : vatTinNo.hashCode());
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
		Company other = (Company) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (cstNo == null) {
			if (other.cstNo != null)
				return false;
		} else if (!cstNo.equals(other.cstNo))
			return false;
		if (fda20bLcNo == null) {
			if (other.fda20bLcNo != null)
				return false;
		} else if (!fda20bLcNo.equals(other.fda20bLcNo))
			return false;
		if (fda21bLcNo == null) {
			if (other.fda21bLcNo != null)
				return false;
		} else if (!fda21bLcNo.equals(other.fda21bLcNo))
			return false;
		if (shopEstLcNo == null) {
			if (other.shopEstLcNo != null)
				return false;
		} else if (!shopEstLcNo.equals(other.shopEstLcNo))
			return false;
		if (vatTinNo == null) {
			if (other.vatTinNo != null)
				return false;
		} else if (!vatTinNo.equals(other.vatTinNo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", name=" + name + ", email=" + email + ", mobileNo=" + mobileNo
				+ ", address=" + address + ", city=" + city + ", pincode=" + pincode + ", shopEstLcNo=" + shopEstLcNo
				+ ", vatTinNo=" + vatTinNo + ", cstNo=" + cstNo + ", fda20bLcNo=" + fda20bLcNo + ", fda21bLcNo="
				+ fda21bLcNo + "]";
	}
	
	

}