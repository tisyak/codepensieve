package com.medsys.product.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "set_instrument")
public class SetInstrument implements Serializable,Comparable<SetInstrument> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -547583099563214158L;

	static Logger logger = LoggerFactory.getLogger(Set.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "set_instr_id", columnDefinition = "serial")
	private Integer setInstrId;

	@Column(name = "set_id")
	private Integer setId;

	@Size(max = 20, message = "{error.field.max}")
	@Column(name = "instr_id", length = 20)
	private String instrId;

	@Size(max = 250, message = "{error.field.max}")
	@Column(name = "instr_desc", length = 250)
	private String instrDesc;

	@Column(name = "qty")
	private Integer qty;

	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	public SetInstrument() {
	}

	public Integer getSetInstrId() {
		return setInstrId;
	}

	public void setSetInstrId(Integer setInstrId) {
		this.setInstrId = setInstrId;
	}

	public Integer getSetId() {
		return setId;
	}

	public void setSetId(Integer setId) {
		this.setId = setId;
	}

	public String getInstrId() {
		return instrId;
	}

	public void setInstrId(String instrId) {
		this.instrId = instrId;
	}

	public String getInstrDesc() {
		return instrDesc;
	}

	public void setInstrDesc(String instrDesc) {
		this.instrDesc = instrDesc;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
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
		return "SetInstrument [setInstrId=" + setInstrId + ", setId=" + setId + ", instrId=" + instrId + ", instrDesc="
				+ instrDesc + ", qty=" + qty + ", updateBy=" + updateBy + ", updateTimestamp=" + updateTimestamp + "]";
	}

	@Override
	public int compareTo(SetInstrument aThat) {
		return this.instrId.compareTo(aThat.instrId);
	}

}
