package com.misys.stockmarket.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the USER_MASTER database table.
 * 
 */
@Entity
@Table(name = "USER_MASTER")
public class UserMaster implements BaseEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID", unique = true, nullable = false)
	private Long userId;

	@Column(length = 2)
	private String active;

	@Column(length = 35)
	private String email;

	@Column(name = "FIRST_NAME", length = 35)
	private String firstName;

	@Column(name = "LAST_NAME", length = 35)
	private String lastName;

	@Column(length = 35)
	private String password;

	@Column(length = 2)
	private String verified;

	// bi-directional many-to-one association to OrderMaster
	/*
	 * @OneToMany
	 * 
	 * @JoinColumn(name = "ORDER_ID") private List<OrderMaster> orderMasters;
	 * 
	 * // bi-directional many-to-one association to PortfolioMaster
	 * 
	 * @OneToMany
	 * 
	 * @JoinColumn(name = "PORTFOLIO_ID") private List<PortfolioMaster>
	 * portfolioMasters;
	 */

	public UserMaster() {
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerified() {
		return this.verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	@Override
	public String toString() {
		return "UserMaster [userId=" + userId + ", active=" + active
				+ ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", verified=" + verified + "]";
	}

}