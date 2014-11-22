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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the USER_ACHIEVEMENTS database table.
 * 
 */
@Entity
@Table(name = "USER_ACHIEVEMENT")
@SequenceGenerator(name = "SEQ_USER_ACHIEVEMENT")
public class UserAchievement extends AuditableEntity implements BaseEntity,
		Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_USER_ACHIEVEMENT")
	@Column(name = "USER_ACHIEVEMENT_ID", unique = true, nullable = false)
	private Long userAchievementId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMPLETED")
	private Date completed;

	@Column(name = "PUBLISHED")
	private String published;

	// bi-directional many-to-one association to UserMaster
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserMaster userMaster;
	
	// bi-directional many-to-one association to AchievementRule
	@ManyToOne
	@JoinColumn(name = "RULE_ID")
	private AchievementRule achievementRule;

	public Long getUserAchievementId() {
		return userAchievementId;
	}

	public void setUserAchievementId(Long userAchievementId) {
		this.userAchievementId = userAchievementId;
	}

	public Date getCompleted() {
		return completed;
	}

	public void setCompleted(Date completed) {
		this.completed = completed;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public UserMaster getUserMaster() {
		return userMaster;
	}

	public void setUserMaster(UserMaster userMaster) {
		this.userMaster = userMaster;
	}

	public AchievementRule getAchievementRule() {
		return achievementRule;
	}

	public void setAchievementRule(AchievementRule achievementRule) {
		this.achievementRule = achievementRule;
	}

	
}
