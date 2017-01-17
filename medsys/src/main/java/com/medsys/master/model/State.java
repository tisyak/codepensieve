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
@Table(name="m_state")
public class State extends MasterData{

	/** The stateCode. */
	@Id
	@NotNull(message = "{error.state.code.null}")
    @NotEmpty(message = "{error.state.code.empty}")
	@Column(name="state_code")
	@GeneratedValue
	private String stateCode;
	
	/** The stateName. */
	@NotNull(message = "{error.state.name.null}")
    @NotEmpty(message = "{error.state.name.empty}")
	@Column(name="state_name")
	private String stateName;
	
	
	/** The update by. */
	@NotNull(message = "{error.spcategory.updateBy.null}")
    @NotEmpty(message = "{error.spcategory.updateBy.empty}")
	@Column(name="update_by")
	private String updateBy;
	
	/** The update timestamp. */
	@NotNull(message = "{error.spcategory.updateTimestamp.null}")
	@Column(name="update_timestamp")
	private Timestamp updateTimestamp;

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@Override
	public String getUpdateBy() {
		return updateBy;
	}

	@Override
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Override
	public Timestamp getUpdateTimestamp() {
		return updateTimestamp;
	}

	@Override
	public void setUpdateTimestamp(Timestamp updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	@Override
	public String toString() {
		return "State [stateCode=" + stateCode + ", stateName=" + stateName
				+ ", updateBy=" + updateBy + ", updateTimestamp="
				+ updateTimestamp + "]";
	}

	@Override
	public String getUniqueId() {
		return stateCode;
	}
}
