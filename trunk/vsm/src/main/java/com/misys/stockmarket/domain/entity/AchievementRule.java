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
 * The persistent class for the ACHIEVEMENT_RULES database table.
 * 
 */
@Entity
@Table(name = "ACHIEVEMENT_RULE")
@SequenceGenerator(name = "SEQ_ACHIEVEMENT_RULE")
public class AchievementRule implements BaseEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ACHIEVEMENT_RULE")
	@Column(name = "ACHIEVEMENT_RULE_ID", unique = true, nullable = false)
	private Long achievementRuleId;

	@Column(name = "QUANTITY")
	private BigDecimal quantity;

	@Column(name = "ACHIEVEMENT_LEVEL")
	private BigDecimal level;

	// TODO: Use max size 
	@Column(name = "DESCRIPTION", length = 35)
	private String description;
	
	// bi-directional many-to-one association to AchievementType
	@ManyToOne
	@JoinColumn(name = "TYPE_ID")
	private AchievementType achievementType;

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getLevel() {
		return level;
	}

	public void setLevel(BigDecimal level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AchievementType getAchievementType() {
		return achievementType;
	}

	public void setAchievementType(AchievementType achievementType) {
		this.achievementType = achievementType;
	}

	public Long getAchievementRuleId() {
		return achievementRuleId;
	}

	public void setAchievementRuleId(Long achievementRuleId) {
		this.achievementRuleId = achievementRuleId;
	}

}
