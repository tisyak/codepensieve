/*
 * 
 */
package com.medsys.master.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * The Class ConfigPara.
 */
@Entity
@Table(name="config_para")
public class ConfigPara extends MasterData {

	/** The paraname. */
	@Id
	@NotNull(message = "{error.config.paraname.null}")
    @NotEmpty(message = "{error.config.paraname.empty}")
	@Column(name="para_name")
	private String paraname;
	
	/** The paravalue. */
	@NotNull(message = "{error.config.paravalue.null}")
    @NotEmpty(message = "{error.config.paravalue.empty}")
	@Column(name="para_value")
	private String paravalue;
	
	/** The paradesc. */
	@Column(name="para_desc")
	private String paradesc;
	
	/** The paraValueInBytes. */
	@Column(name="para_byte_values")
	private byte[] parabytevalue;
	
	/** The update by. */
	@NotNull(message = "{error.config.updateBy.null}")
    @NotEmpty(message = "{error.config.updateBy.empty}")
	@Column(name="update_by")
	private String updateBy;
	
	/** The update timestamp. */
	@NotNull(message = "{error.config.updateTimestamp.null}")
	@Column(name="update_timestamp")
	private Timestamp updateTimestamp;
	
	/**
	 * Gets the paraname.
	 *
	 * @return the paraname
	 */
	public String getParaname() {
		return paraname;
	}
	
	/**
	 * Sets the paraname.
	 *
	 * @param paraname the new paraname
	 */
	public void setParaname(String paraname) {
		this.paraname = paraname;
	}
	
	/**
	 * Gets the paravalue.
	 *
	 * @return the paravalue
	 */
	public String getParavalue() {
		return paravalue;
	}
	
	/**
	 * Sets the paravalue.
	 *
	 * @param paravalue the new paravalue
	 */
	public void setParavalue(String paravalue) {
		this.paravalue = paravalue;
	}
	
	/**
	 * Gets the paradesc.
	 *
	 * @return the paradesc
	 */
	public String getParadesc() {
		return paradesc;
	}
	
	/**
	 * Sets the paradesc.
	 *
	 * @param paradesc the new paradesc
	 */
	public void setParadesc(String paradesc) {
		this.paradesc = paradesc;
	}

	/**
	 * Gets the parabytevalue.
	 *
	 * @return the parabytevalue
	 */
	public byte[] getParabytevalue() {
		return parabytevalue;
	}

	/**
	 * Sets the parabytevalue.
	 *
	 * @param parabytevalue the new parabytevalue
	 */
	public void setParabytevalue(byte[] parabytevalue) {
		this.parabytevalue = parabytevalue;
	}

	/**
	 * Gets the update by.
	 *
	 * @return the update by
	 */
	@Override
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * Sets the update by.
	 *
	 * @param updateBy the new update by
	 */
	@Override
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * Gets the update timestamp.
	 *
	 * @return the update timestamp
	 */
	@Override
	public Timestamp getUpdateTimestamp() {
		return updateTimestamp;
	}

	/**
	 * Sets the update timestamp.
	 *
	 * @param updateTimestamp the new update timestamp
	 */
	@Override
	public void setUpdateTimestamp(Timestamp updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	@Override
	public String getUniqueId() {
		return paraname;
	}
	
	
}
