package com.medsys.orders.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medsys.master.model.POStatusMaster;
import com.medsys.supplier.model.Supplier;

@Entity
@Table(name = "purchase_po")
public class PurchaseOrder {

	static Logger logger = LoggerFactory.getLogger(PurchaseOrder.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "po_id", columnDefinition = "serial")
	private Integer poId;

	@NotBlank(message = "{error.field.empty}")
	@Size(max = 20, message = "{error.field.max}")
	@Column(name = "po_number", length = 20)
	private String poNumber;

	@ManyToOne
	@JoinColumn(columnDefinition = "uuid", name = "customer_id", referencedColumnName = "customer_id")
	private Supplier supplier;

	@Column(name = "po_date")
	@Type(type = "date")
	@NotNull
	private Date poDate;

	@Column(name = "recieve_date")
	@Type(type = "date")
	@NotNull
	private Date recieveDate;

	@ManyToOne
	@JoinColumn(name = "po_status_id", referencedColumnName = "po_status_id")
	private POStatusMaster poStatus; 

	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	@OneToMany(mappedBy = "poId")
	java.util.SortedSet<POProductSet> products;

	public PurchaseOrder() {}

	public PurchaseOrder(boolean generatePONumber) {
		super();
		if (generatePONumber) {
			Date date = new Date();
			String modifiedDate = new SimpleDateFormat("yyMMdd").format(date);
			this.setPONumber("RD-" + modifiedDate + "-" + RandomStringUtils.random(4, true, true));
		}
	}

	public Integer getPOId() {
		return poId;
	}

	public void setPOId(Integer poId) {
		this.poId = poId;
	}

	public String getPONumber() {
		return poNumber;
	}

	public void setPONumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Date getPODate() {
		return poDate;
	}

	public void setPODate(Date poDate) {
		this.poDate = poDate;
	}

	public Integer getPoId() {
		return poId;
	}

	public void setPoId(Integer poId) {
		this.poId = poId;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Date getPoDate() {
		return poDate;
	}

	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}

	public Date getRecieveDate() {
		return recieveDate;
	}

	public void setRecieveDate(Date recieveDate) {
		this.recieveDate = recieveDate;
	}

	public POStatusMaster getPoStatus() {
		return poStatus;
	}

	public void setPoStatus(POStatusMaster poStatus) {
		this.poStatus = poStatus;
	}

	public POStatusMaster getPOStatus() {
		return poStatus;
	}

	public void setPOStatus(POStatusMaster poStatus) {
		this.poStatus = poStatus;
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

	public java.util.SortedSet<POProductSet> getProducts() {
		return products;
	}

	public void setProducts(java.util.SortedSet<POProductSet> products) {
		this.products = products;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((poId == null) ? 0 : poId.hashCode());
		result = prime * result + ((poNumber == null) ? 0 : poNumber.hashCode());
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
		if (poId == null) {
			if (other.poId != null)
				return false;
		} else if (!poId.equals(other.poId))
			return false;
		if (poNumber == null) {
			if (other.poNumber != null)
				return false;
		} else if (!poNumber.equals(other.poNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PurchasePO [poId=" + poId + ", poNumber=" + poNumber + ", supplier=" + supplier + ", poDate=" + poDate
				+ ", recieveDate=" + recieveDate + ", poStatus=" + poStatus + ", updateBy=" + updateBy
				+ ", updateTimestamp=" + updateTimestamp + ", products=" + products + "]";
	}


}
