package com.medsys.orders.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medsys.master.model.PurchaseOrderStatusMaster;
import com.medsys.supplier.model.Supplier;

@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {

	static Logger logger = LoggerFactory.getLogger(PurchaseOrder.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "po_id", columnDefinition = "serial")
	private Integer purchaseOrderId;

	@NotBlank(message = "{error.field.empty}")
	@Size(max = 20, message = "{error.field.max}")
	@Column(name = "po_number", length = 20)
	private String purchaseOrderNumber;

	@ManyToOne
	@JoinColumn(name = "supplier_id", referencedColumnName = "supplier_id")
	private Supplier supplier;

	@Column(name = "po_date")
	@Type(type = "date")
	@NotNull
	private Date purchaseOrderDate;

	@Column(name = "recieve_date")
	@Type(type = "date")
	@NotNull
	private Date receiveDate;

	@ManyToOne
	@JoinColumn(name = "po_status_id", referencedColumnName = "po_status_id")
	private PurchaseOrderStatusMaster purchaseOrderStatus; 

	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	@OneToMany(mappedBy = "purchaseOrderId")
	@javax.persistence.OrderBy("product ASC")
	java.util.SortedSet<PurchaseOrderProductSet> products;

	public Integer getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Integer purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public Date getPurchaseOrderDate() {
		return purchaseOrderDate;
	}

	public void setPurchaseOrderDate(Date purchaseOrderDate) {
		this.purchaseOrderDate = purchaseOrderDate;
	}

	public PurchaseOrderStatusMaster getPurchaseOrderStatus() {
		return purchaseOrderStatus;
	}

	public void setPurchaseOrderStatus(PurchaseOrderStatusMaster purchaseOrderStatus) {
		this.purchaseOrderStatus = purchaseOrderStatus;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
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

	public java.util.SortedSet<PurchaseOrderProductSet> getProducts() {
		return products;
	}

	public void setProducts(java.util.SortedSet<PurchaseOrderProductSet> products) {
		this.products = products;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((purchaseOrderId == null) ? 0 : purchaseOrderId.hashCode());
		result = prime * result + ((purchaseOrderNumber == null) ? 0 : purchaseOrderNumber.hashCode());
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
		PurchaseOrder other = (PurchaseOrder) obj;
		if (purchaseOrderId == null) {
			if (other.purchaseOrderId != null)
				return false;
		} else if (!purchaseOrderId.equals(other.purchaseOrderId))
			return false;
		if (purchaseOrderNumber == null) {
			if (other.purchaseOrderNumber != null)
				return false;
		} else if (!purchaseOrderNumber.equals(other.purchaseOrderNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PurchasePO [poId=" + purchaseOrderId + ", poNumber=" + purchaseOrderNumber + ", supplier=" + supplier + ", poDate=" + purchaseOrderDate
				+ ", recieveDate=" + receiveDate + ", poStatus=" + purchaseOrderStatus + ", updateBy=" + updateBy
				+ ", updateTimestamp=" + updateTimestamp + "]";
	}


}
