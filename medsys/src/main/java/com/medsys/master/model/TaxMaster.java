package com.medsys.master.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "m_tax")
public class TaxMaster extends MasterData{
	
	static Logger logger = LoggerFactory.getLogger(TaxMaster.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tax_id", columnDefinition = "serial")
	private Integer taxId; 
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 20, message = "{error.field.max}")
	@Column(name = "tax_type", length = 20)
	private String taxType;
	
	@NotNull
	@Column(name = "tax_percentage")
	private Integer tax_percentage; 
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 250, message = "{error.field.max}")
	@Column(name = "tax_desc", length = 250)
	private String taxDesc;
			
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	public Integer getTaxId() {
		return taxId;
	}

	public void setTaxId(Integer taxId) {
		this.taxId = taxId;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public Integer getTax_percentage() {
		return tax_percentage;
	}

	public void setTax_percentage(Integer tax_percentage) {
		this.tax_percentage = tax_percentage;
	}

	public String getTaxDesc() {
		return taxDesc;
	}

	public void setTaxDesc(String taxDesc) {
		this.taxDesc = taxDesc;
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
		return "TaxMaster [taxId=" + taxId + ", taxType=" + taxType + ", tax_percentage=" + tax_percentage
				+ ", taxDesc=" + taxDesc + ", updateBy=" + updateBy + ", updateTimestamp=" + updateTimestamp + "]";
	}

	@Override
	public Integer getUniqueId() {
		return taxId;
	}

	@Override
	public String getKeyColumnName() {
		return "taxType";
	}

	
	
	
}
