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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medsys.customer.model.Customer;
import com.medsys.master.model.OrderStatusMaster;
import com.medsys.product.model.Set;

@Entity
@Table(name = "orders")
public class Orders {

	static Logger logger = LoggerFactory.getLogger(Orders.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id", columnDefinition = "serial")
	private Integer orderId;

	@Size(max = 20, message = "{error.field.max}")
	@Column(name = "order_number", length = 20)
	private String orderNumber;

	@ManyToOne
	@JoinColumn(columnDefinition = "uuid", name = "customer_id", referencedColumnName = "customer_id")
	private Customer customer;

	@Size(max = 100, message = "{error.field.max}")
	@Column(name = "patient_name", length = 100)
	private String patientName;

	@Size(max = 250, message = "{error.field.max}")
	@Column(name = "reference_source", length = 250)
	private String refSource;

	@Column(name = "order_date")
	@Type(type = "date")
	@NotNull
	private Date orderDate;

	@Column(name = "delivery_date")
	@Type(type = "date")
	@NotNull
	private Date deliveryDate;

	@ManyToOne
	@JoinColumn(name = "order_status_id", referencedColumnName = "order_status_id")
	private OrderStatusMaster orderStatus; 

	@ManyToOne
	@JoinColumn(name = "set_id", referencedColumnName = "set_id")
	private Set set;

	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	@OneToMany(mappedBy = "orderId")
	@javax.persistence.OrderBy("product ASC")
	java.util.SortedSet<OrderProductSet> products;

	public static void setLogger(Logger logger) {
		Orders.logger = logger;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getRefSource() {
		return refSource;
	}

	public void setRefSource(String refSource) {
		this.refSource = refSource;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public OrderStatusMaster getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatusMaster orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Set getSet() {
		return set;
	}

	public void setSet(Set set) {
		this.set = set;
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

	public java.util.SortedSet<OrderProductSet> getProducts() {
		return products;
	}

	public void setProducts(java.util.SortedSet<OrderProductSet> products) {
		this.products = products;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((orderNumber == null) ? 0 : orderNumber.hashCode());
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
		Orders other = (Orders) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (orderNumber == null) {
			if (other.orderNumber != null)
				return false;
		} else if (!orderNumber.equals(other.orderNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Orders [orderId=" + orderId + ", orderNumber=" + orderNumber + ", customer=" + customer
				+ ", patientName=" + patientName + ", refSource=" + refSource + ", orderDate=" + orderDate
				+ ", deliveryDate=" + deliveryDate + ", orderStatus=" + orderStatus + ", set=" + set + ", updateBy="
				+ updateBy + ", updateTimestamp=" + updateTimestamp + ", products=" + products + "]";
	}

}
