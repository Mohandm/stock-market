package com.misys.stockmarket.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the ORDER_EXECUTION database table.
 * 
 */
@Entity
@Table(name = "ORDER_EXECUTION")
@SequenceGenerator(name = "SEQ_ORDER_EXECUTION")
public class OrderExecution implements BaseEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDER_EXECUTION")
	@Column(name = "EXECUTION_ID", unique = true, nullable = false)
	private long executionId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXECUTION_DATE")
	private Date executionDate;

	@Column(name = "EXECUTION_PRICE", precision = 8, scale = 2)
	private BigDecimal executionPrice;

	@Column(name = "UNITS_TRADED")
	private BigDecimal unitsTraded;

	// bi-directional many-to-one association to OrderMaster
	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	private OrderMaster orderMaster;

	public OrderExecution() {
	}

	public long getExecutionId() {
		return this.executionId;
	}

	public void setExecutionId(long executionId) {
		this.executionId = executionId;
	}

	public Date getExecutionDate() {
		return this.executionDate;
	}

	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}

	public BigDecimal getExecutionPrice() {
		return this.executionPrice;
	}

	public void setExecutionPrice(BigDecimal executionPrice) {
		this.executionPrice = executionPrice;
	}

	public BigDecimal getUnitsTraded() {
		return this.unitsTraded;
	}

	public void setUnitsTraded(BigDecimal unitsTraded) {
		this.unitsTraded = unitsTraded;
	}

	public OrderMaster getOrderMaster() {
		return this.orderMaster;
	}

	public void setOrderMaster(OrderMaster orderMaster) {
		this.orderMaster = orderMaster;
	}

}