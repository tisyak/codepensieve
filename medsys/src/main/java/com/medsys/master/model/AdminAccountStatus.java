package com.medsys.master.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="m_admin_account_status")
public class AdminAccountStatus extends MasterData {
	
	@Id
	@NotNull(message = "{error.status.code.null}")
    @NotEmpty(message = "{error.status.code.empty}")
	@Column(name="status_code")
	@GeneratedValue
	private String statusCode;
	
	/** The stateName. */
	@NotNull(message = "{error.status.name.null}")
    @NotEmpty(message = "{error.status.name.empty}")
	@Column(name="status")
	private String status;
	
	/** The update by. */
	@NotNull(message = "{error.status.updateBy.null}")
    @NotEmpty(message = "{error.status.updateBy.empty}")
	@Column(name="update_by")
	private String updateBy;
	
	/** The update timestamp. */
	@NotNull(message = "{error.status.updateTimestamp.null}")
	@Column(name="update_timestamp")
	private Timestamp updateTimestamp;

	@Override
	public String getUniqueId() {
		return statusCode;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		return "AdminAccountStatus [statusCode=" + statusCode + ", status="
				+ status + ", updateBy=" + updateBy + ", updateTimestamp="
				+ updateTimestamp + "]";
	}

}
