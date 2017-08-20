package com.medsys.product.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "set")
public class Set implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2595844272504689472L;

	static Logger logger = LoggerFactory.getLogger(Set.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "set_id", columnDefinition = "serial")
	private Integer setId; 
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 100, message = "{error.field.max}")
	@Column(name = "set_name", length = 100)
	private String setName;
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 250, message = "{error.field.max}")
	@Column(name = "set_desc", length = 250)
	private String setDesc;
			
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;


	@OneToMany(mappedBy = "setId")
	@javax.persistence.OrderBy("instrDesc ASC")
	java.util.SortedSet<SetInstrument> instruments;
	
	@OneToMany(mappedBy = "setId")
	@javax.persistence.OrderBy("product ASC")
	java.util.SortedSet<SetPdtTemplate> pdtTemplates;
	
	public Integer getSetId() {
		return setId;
	}

	public void setSetId(Integer setId) {
		this.setId = setId;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public String getSetDesc() {
		return setDesc;
	}

	public void setSetDesc(String setDesc) {
		this.setDesc = setDesc;
	}

	public java.util.SortedSet<SetInstrument> getInstruments() {
		return instruments;
	}

	public void setInstruments(java.util.SortedSet<SetInstrument> instruments) {
		this.instruments = instruments;
	}

	public java.util.SortedSet<SetPdtTemplate> getPdtTemplates() {
		return pdtTemplates;
	}

	public void setPdtTemplates(java.util.SortedSet<SetPdtTemplate> pdtTemplates) {
		this.pdtTemplates = pdtTemplates;
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
		return "Set [setId=" + setId + ", setName=" + setName + ", setDesc=" + setDesc + ", updateBy=" + updateBy
				+ ", updateTimestamp=" + updateTimestamp + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((setId == null) ? 0 : setId.hashCode());
		result = prime * result + ((setName == null) ? 0 : setName.hashCode());
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
		Set other = (Set) obj;
		if (setId == null) {
			if (other.setId != null)
				return false;
		} else if (!setId.equals(other.setId))
			return false;
		if (setName == null) {
			if (other.setName != null)
				return false;
		} else if (!setName.equals(other.setName))
			return false;
		return true;
	}
	
	

}
