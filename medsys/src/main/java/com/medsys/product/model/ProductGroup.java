package com.medsys.product.model;

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
@Table(name = "product_group")
public class ProductGroup {
	
	static Logger logger = LoggerFactory.getLogger(ProductGroup.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id", columnDefinition = "serial")
	private Integer groupId; 
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 150, message = "{error.field.max}")
	@Column(name = "group_name", length = 150)
	private String group_name;
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 250, message = "{error.field.max}")
	@Column(name = "group_desc", length = 250)
	private String groupDesc;
			
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
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
		return "ProductGroup [groupId=" + groupId + ", group_name=" + group_name + ", groupDesc=" + groupDesc
				+ ", updateBy=" + updateBy + ", updateTimestamp=" + updateTimestamp + "]";
	}

	

}
