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
@Table(name = "m_order_status")
public class OrderStatusMaster extends MasterData  {
	
	static Logger logger = LoggerFactory.getLogger(OrderStatusMaster.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_status_id", columnDefinition = "serial")
	private Integer orderStatusId; 
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 20, message = "{error.field.max}")
	@Column(name = "order_status_code", length = 5)
	private String orderStatusCode;
	
	@NotBlank(message = "{error.field.empty}")
	@Size(max = 250, message = "{error.field.max}")
	@Column(name = "order_status_desc", length = 250)
	private String orderStatusDesc;
			
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	public Integer getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(Integer orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public String getOrderStatusCode() {
		return orderStatusCode;
	}

	public void setOrderStatusCode(String orderStatusCode) {
		this.orderStatusCode = orderStatusCode;
	}

	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}

	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
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
		result = prime * result + ((orderStatusCode == null) ? 0 : orderStatusCode.hashCode());
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
		OrderStatusMaster other = (OrderStatusMaster) obj;
		if (orderStatusCode == null) {
			if (other.orderStatusCode != null)
				return false;
		} else if (!orderStatusCode.equals(other.orderStatusCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderStatusMaster [orderStatusId=" + orderStatusId + ", orderStatusCode=" + orderStatusCode
				+ ", orderStatusDesc=" + orderStatusDesc + ", updateBy=" + updateBy + ", updateTimestamp="
				+ updateTimestamp + "]";
	}

	@Override
	public Integer getUniqueId() {
		return this.getOrderStatusId();
	}

	@Override
	public String getKeyColumnName() {
		return "orderStatusCode";
	}

}
