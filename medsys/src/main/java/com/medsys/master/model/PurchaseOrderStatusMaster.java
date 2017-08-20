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
public class PurchaseOrderStatusMaster extends MasterData  {
	
	static Logger logger = LoggerFactory.getLogger(PurchaseOrderStatusMaster.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "po_status_id", columnDefinition = "serial")
	private Integer purchaseOrderStatusId; 
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 20, message = "{error.field.max}")
	@Column(name = "po_status_code", length = 5)
	private String purchaseOrderStatusCode;
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 250, message = "{error.field.max}")
	@Column(name = "po_status_desc", length = 250)
	private String purchaseOrderStatusDesc;
			
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	public Integer getPurchaseOrderStatusId() {
		return purchaseOrderStatusId;
	}

	public void setPurchaseOrderStatusId(Integer purchaseOrderStatusId) {
		this.purchaseOrderStatusId = purchaseOrderStatusId;
	}

	public String getPurchaseOrderStatusCode() {
		return purchaseOrderStatusCode;
	}

	public void setPurchaseOrderStatusCode(String purchaseOrderStatusCode) {
		this.purchaseOrderStatusCode = purchaseOrderStatusCode;
	}

	public String getPurchaseOrderStatusDesc() {
		return purchaseOrderStatusDesc;
	}

	public void setPurchaseOrderStatusDesc(String purchaseOrderStatusDesc) {
		this.purchaseOrderStatusDesc = purchaseOrderStatusDesc;
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
		result = prime * result + ((purchaseOrderStatusCode == null) ? 0 : purchaseOrderStatusCode.hashCode());
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
		PurchaseOrderStatusMaster other = (PurchaseOrderStatusMaster) obj;
		if (purchaseOrderStatusCode == null) {
			if (other.purchaseOrderStatusCode != null)
				return false;
		} else if (!purchaseOrderStatusCode.equals(other.purchaseOrderStatusCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PurchaseOrderStatusMaster [purchaseOrderStatusId=" + purchaseOrderStatusId + ", purchaseOrderStatusCode=" + purchaseOrderStatusCode
				+ ", purchaseOrderStatusDesc=" + purchaseOrderStatusDesc + ", updateBy=" + updateBy + ", updateTimestamp="
				+ updateTimestamp + "]";
	}

	@Override
	public Integer getUniqueId() {
		return this.getPurchaseOrderStatusId();
	}

	@Override
	public String getKeyColumnName() {
		return "purchaseOrderStatusCode";
	}

}
