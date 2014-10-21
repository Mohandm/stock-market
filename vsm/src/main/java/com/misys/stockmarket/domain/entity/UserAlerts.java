package com.misys.stockmarket.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the USER_ALERTS database table.
 * 
 */
@Entity
@Table(name = "USER_ALERTS")
public class UserAlerts implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ALERTS_ID")
	private long userAlertsId;
	
	// bi-directional many-to-one association to UserMaster
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserMaster userMaster;
	
	@Column(name = "MESSAGE", length = 500)
	private String message;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "NOTIFIED_DATE")
	private Date notifiedDate;
	
	public UserAlerts() {
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "WatchStock [userAlertsId="+ userAlertsId +", message=" + message + ", notifiedDate=" + notifiedDate + "]";
	}

	public long getUserAlertsId() {
		return userAlertsId;
	}

	public void setUserAlertsId(long userAlertsId) {
		this.userAlertsId = userAlertsId;
	}

	public UserMaster getUserMaster() {
		return userMaster;
	}

	public void setUserMaster(UserMaster userMaster) {
		this.userMaster = userMaster;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getNotifiedDate() {
		return notifiedDate;
	}

	public void setNotifiedDate(Date notifiedDate) {
		this.notifiedDate = notifiedDate;
	}
}
