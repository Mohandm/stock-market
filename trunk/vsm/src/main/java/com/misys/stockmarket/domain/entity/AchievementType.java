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
 * The persistent class for the ACHIEVEMENT_TYPE database table.
 * 
 */
@Entity
@Table(name = "ACHIEVEMENT_TYPE")
@SequenceGenerator(name = "SEQ_ACHIEVEMENT_TYPE")
public class AchievementType implements BaseEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ACHIEVEMENT_TYPE")
	@Column(name = "TYPE_ID", unique = true, nullable = false)
	private Long typeId;

	@Column(length = 35)
	private String name;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
