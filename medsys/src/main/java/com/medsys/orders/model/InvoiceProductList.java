package com.medsys.orders.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medsys.master.model.TaxMaster;
import com.medsys.product.model.ProductInv;

@Entity
@Table(name = "invoice_product_list")
public class InvoiceProductList {
	
	static Logger logger = LoggerFactory.getLogger(InvoiceProductList.class);
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_set_id", columnDefinition = "serial")
	private Integer productSetId; 
	
	@ManyToOne
	@JoinColumn(name="invoice_no",referencedColumnName="invoice_no")
	private Invoice invoice;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="product_id",referencedColumnName="product_id"),
		@JoinColumn(name="lot_no",referencedColumnName="lot_no")})
	private ProductInv productInv;
	
	@NotNull
	@Column(name = "rate_per_unit")
	private BigDecimal ratePerUnit; 
	
	@NotNull
	@Column(name = "total")
	private BigDecimal total; 
	
	@NotNull
	@Column(name = "vat_amount")
	private BigDecimal vatAmount; 
	
	@ManyToOne
	@JoinColumn(name="vat_type",referencedColumnName="tax_id")
	private TaxMaster vatType;
	
	@NotNull
	@Column(name = "price")
	private BigDecimal price; 
	
	@NotNull
	@Column(name = "qty")
	private Integer qty; 
	
	/** The update by. */
	@Column(name = "update_by")
	private String updateBy;

	/** The update timestamp. */
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;
	
	

}
