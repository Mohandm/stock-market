package com.misys.stockmarket.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the USER_MASTER database table.
 * 
 */
@Entity
@Table(name = "USER_INVITATION")
@SequenceGenerator(name = "SEQ_USER_INVITATION")
public class UserInvitation extends AuditableEntity implements BaseEntity,
		Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_USER_INVITATION")
	@Column(name = "INVITATION_ID", unique = true, nullable = false)
	private Long invitationId;

	@Column(length = 1)
	private String accepted;

	@Column(length = 35)
	private String email;

	@Column(length = 60)
	private String token;

	// bi-directional many-to-one association to UserMaster
	@ManyToOne
	@JoinColumn(name = "REFERRER_ID")
	private UserMaster referer;

	public Long getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(Long invitationId) {
		this.invitationId = invitationId;
	}

	public String getAccepted() {
		return accepted;
	}

	public void setAccepted(String accepted) {
		this.accepted = accepted;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserMaster getReferer() {
		return referer;
	}

	public void setReferer(UserMaster userMaster) {
		this.referer = userMaster;
	}
}