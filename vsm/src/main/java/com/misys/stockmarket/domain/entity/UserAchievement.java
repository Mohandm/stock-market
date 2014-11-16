package com.misys.stockmarket.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;

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

	@Column(name = "NEXT_LEVEL")
	private BigDecimal nextLevel;


	@Column(name = "CURRENT_QUANTITY")
	private BigDecimal currentQuantity;
	
	@Column(name = "EVALUATED")
	private String evaluated;

	@Column(name = "PUBLISHED")
	private String published;

	// bi-directional many-to-one association to UserMaster
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserMaster userMaster;

	// bi-directional many-to-one association to AchievementType
	@ManyToOne
	@JoinColumn(name = "TYPE_ID")
	private AchievementType achievementType;

	public Long getUserAchievementId() {
		return userAchievementId;
	}

	public void setUserAchievementId(Long userAchievementId) {
		this.userAchievementId = userAchievementId;
	}

	public BigDecimal getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(BigDecimal userLevel) {
		this.nextLevel = userLevel;
	}

	public BigDecimal getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(BigDecimal currentQuantity) {
		this.currentQuantity = currentQuantity;
	}

	public String getEvaluated() {
		return evaluated;
	}

	public void setEvaluated(String evaluated) {
		this.evaluated = evaluated;
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

	public AchievementType getAchievementType() {
		return achievementType;
	}

	public void setAchievementType(AchievementType achievementType) {
		this.achievementType = achievementType;
	}
}
