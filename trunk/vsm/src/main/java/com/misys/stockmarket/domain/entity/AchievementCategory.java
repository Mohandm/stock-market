package com.misys.stockmarket.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the ACHIEVEMENT_CATEGORY database table.
 * 
 */
@Entity
@Table(name = "ACHIEVEMENT_CATEGORY")
@SequenceGenerator(name = "SEQ_ACHIEVEMENT_CATEGORY")
public class AchievementCategory extends AuditableEntity implements BaseEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ACHIEVEMENT_CATEGORY")
	@Column(name = "CATEGORY_ID", unique = true, nullable = false)
	private Long categoryId;

	@Column(length = 35)
	private String name;

	@Column(length = 35)
	private String alias;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
