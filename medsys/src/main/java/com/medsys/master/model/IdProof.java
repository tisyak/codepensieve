package com.medsys.master.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "m_id_proof")
public class IdProof extends MasterData {

	@Id
	@NotNull(message = "{error.Id.code.null}")
	@Column(name="id_code")
	@GeneratedValue
	private String idCode;
	
	@NotNull(message = "{error.Id.detail.null}") 
	@Column(name="id_detail")
	private String idDetail;
	
	@Column(name="max_length")
	private Integer maxLength;
	
	@Column(name="valid_regex")
	private String validRegex;
	
	/** The update by. */
	@NotNull(message = "{error.config.updateBy.null}")
	@Column(name="update_by")
	private String updateBy;
	
	/** The update timestamp. */
	@NotNull(message = "{error.config.updateTimestamp.null}")
	@Column(name="update_timestamp")
	private Timestamp updateTimestamp;
	
	@Column(name="is_shared_id")
	private Boolean sharedId;
	
	@Column(name="verification_resend_count")
	private Integer verificationResendCount;

	
	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getIdDetail() {
		return idDetail;
	}

	public void setIdDetail(String idDetail) {
		this.idDetail = idDetail;
	}	

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String getValidRegex() {
		return validRegex;
	}

	public void setValidRegex(String validRegex) {
		this.validRegex = validRegex;
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

	public Boolean getSharedId() {
		return sharedId;
	}

	public void setSharedId(Boolean sharedId) {
		this.sharedId = sharedId;
	}

	public Integer getVerificationResendCount() {
		return verificationResendCount;
	}

	public void setVerificationResendCount(Integer verificationResendCount) {
		this.verificationResendCount = verificationResendCount;
	}
	
	@Override
	public String toString() {
		return "IdProof [idCode=" + idCode + ", idDetail=" + idDetail
				+ ", maxLength=" + maxLength + ", validRegex=" + validRegex
				+ ", updateBy=" + updateBy + ", updateTimestamp="
				+ updateTimestamp + ", sharedId=" + sharedId
				+ ", verificationResendCount=" + verificationResendCount + "]";
	}

	@Override
	public String getUniqueId() {
		return idCode;
	}

}
