package com.misys.stockmarket.domain.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the ORDER_MASTER database table.
 * 
 */
@Entity
@Table(name = "ORDER_MASTER")
public class OrderMaster implements BaseEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDER_ID")
	private long orderId;

	@Column(length = 1)
	private String intraday;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ORDER_DATE")
	private Date orderDate;

	@Column(name = "ORDER_PRICE", precision = 8, scale = 2)
	private BigDecimal orderPrice;

	@Column(name = "PRICE_TYPE", length = 2)
	private String priceType;

	@Column(length = 2)
	private String status;

	@Column(name = "\"TYPE\"", length = 1)
	private String type;

	@ManyToOne
	@JoinColumn(name = "STOCK_ID")
	private StockMaster stockMaster;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserMaster userMaster;

	private BigDecimal volume;

	public OrderMaster() {
	}

	public long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getIntraday() {
		return this.intraday;
	}

	public void setIntraday(String intraday) {
		this.intraday = intraday;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getOrderPrice() {
		return this.orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getPriceType() {
		return this.priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getVolume() {
		return this.volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public StockMaster getStockMaster() {
		return stockMaster;
	}

	public void setStockMaster(StockMaster stockMaster) {
		this.stockMaster = stockMaster;
	}

	public UserMaster getUserMaster() {
		return userMaster;
	}

	public void setUserMaster(UserMaster userMaster) {
		this.userMaster = userMaster;
	}

}