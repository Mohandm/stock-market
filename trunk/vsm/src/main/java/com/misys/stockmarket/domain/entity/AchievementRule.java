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
 * The persistent class for the ACHIEVEMENT_RULES database table.
 * 
 */
@Entity
@Table(name = "ACHIEVEMENT_RULE")
@SequenceGenerator(name = "SEQ_ACHIEVEMENT_RULE")
public class AchievementRule extends AuditableEntity  implements BaseEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ACHIEVEMENT_RULE")
	@Column(name = "ACHIEVEMENT_ID", unique = true, nullable = false)
	private Long achievementId;

	@Column(name = "QUANTITY")
	private Long quantity;

	@Column(name = "LEVEL")
	private int level;

	// TODO: Use max size 
	@Column(name = "DESCRIPTION", length = 35)
	private String description;
	
	@Column (name="coins")
	private Long coins;
	
	// bi-directional many-to-one association to AchievementType
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	private AchievementCategory achievementCategory;

	public Long getAchievementId() {
		return achievementId;
	}

	public void setAchievementId(Long achievementId) {
		this.achievementId = achievementId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCoins() {
		return coins;
	}

	public void setCoins(Long coins) {
		this.coins = coins;
	}

	public AchievementCategory getAchievementCategory() {
		return achievementCategory;
	}

	public void setAchievementCategory(AchievementCategory achievementCategory) {
		this.achievementCategory = achievementCategory;
	}

}
