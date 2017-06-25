/*
 * 
 */
package com.medsys.master.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * The Class SeedMaster.
 */
@Entity
@Table(name="m_seed_master")
public class SeedMaster extends MasterData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seed_id", columnDefinition = "serial")
	private Integer seedId; 
	
	/** The seedname. */
	@NotNull(message = "{error.config.seedname.null}")
    @NotEmpty(message = "{error.config.seedname.empty}")
	@Column(name="seed_name")
	private String seedName;
	
	/** The seedvalue. */
	@NotNull(message = "{error.config.seedvalue.null}")
    @NotEmpty(message = "{error.config.seedvalue.empty}")
	@Column(name="seed_value")
	private String seedValue;
	
	/** The seeddesc. */
	@Column(name="seed_desc")
	private String seedDesc;

	/** The update by. */
	@NotNull(message = "{error.config.updateBy.null}")
    @NotEmpty(message = "{error.config.updateBy.empty}")
	@Column(name="update_by")
	private String updateBy;
	
	/** The update timestamp. */
	@NotNull(message = "{error.config.updateTimestamp.null}")
	@Column(name="update_timestamp")
	private Timestamp updateTimestamp;
	
	
	public Integer getSeedId() {
		return seedId;
	}

	public void setSeedId(Integer seedId) {
		this.seedId = seedId;
	}

	public String getSeedName() {
		return seedName;
	}

	public void setSeedName(String seedName) {
		this.seedName = seedName;
	}

	public String getSeedValue() {
		return seedValue;
	}

	public void setSeedValue(String seedValue) {
		this.seedValue = seedValue;
	}

	public String getSeedDesc() {
		return seedDesc;
	}

	public void setSeedDesc(String seedDesc) {
		this.seedDesc = seedDesc;
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
	public Integer getUniqueId() {
		return this.seedId;
	}

	@Override
	public String getKeyColumnName() {
		return "seedName";
	}
	
	
}
