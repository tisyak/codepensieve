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
@Table(name = "m_po_status")
public class POStatusMaster extends MasterData  {
	
	static Logger logger = LoggerFactory.getLogger(POStatusMaster.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "po_status_id", columnDefinition = "serial")
	private Integer poStatusId; 
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 20, message = "{error.field.max}")
	@Column(name = "po_status_code", length = 5)
	private String poStatusCode;
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 250, message = "{error.field.max}")
	@Column(name = "po_status_desc", length = 250)
	private String poStatusDesc;
			
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	public Integer getPOStatusId() {
		return poStatusId;
	}

	public void setPOStatusId(Integer poStatusId) {
		this.poStatusId = poStatusId;
	}

	public String getPOStatusCode() {
		return poStatusCode;
	}

	public void setPOStatusCode(String poStatusCode) {
		this.poStatusCode = poStatusCode;
	}

	public String getPOStatusDesc() {
		return poStatusDesc;
	}

	public void setPOStatusDesc(String poStatusDesc) {
		this.poStatusDesc = poStatusDesc;
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
		result = prime * result + ((poStatusCode == null) ? 0 : poStatusCode.hashCode());
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
		POStatusMaster other = (POStatusMaster) obj;
		if (poStatusCode == null) {
			if (other.poStatusCode != null)
				return false;
		} else if (!poStatusCode.equals(other.poStatusCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "POStatusMaster [poStatusId=" + poStatusId + ", poStatusCode=" + poStatusCode
				+ ", poStatusDesc=" + poStatusDesc + ", updateBy=" + updateBy + ", updateTimestamp="
				+ updateTimestamp + "]";
	}

	@Override
	public Integer getUniqueId() {
		return this.getPOStatusId();
	}

	@Override
	public String getKeyColumnName() {
		return "poStatusCode";
	}

}
