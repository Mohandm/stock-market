package com.misys.stockmarket.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "REWARD_MASTER")
@SequenceGenerator(name = "SEQ_REWARD_MASTER")
public class RewardMaster implements BaseEntity, Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_REWARD_MASTER")
	@Column(name = "REWARD_ID")
	private long rewardId;

	@Column(name = "REWARD_TYPE", length = 2)
	private String rewardType;

	@Column(name = "REWARD_VALUE", precision = 8, scale = 2)
	private BigDecimal rewardValue;

	public long getRewardId() {
		return rewardId;
	}

	public void setRewardId(long rewardId) {
		this.rewardId = rewardId;
	}

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public BigDecimal getRewardValue() {
		return rewardValue;
	}

	public void setRewardValue(BigDecimal rewardValue) {
		this.rewardValue = rewardValue;
	}

	@Override
	public String toString() {
		return "RewardMaster [rewardId=" + rewardId + ", rewardType="
				+ rewardType + ", rewardValue=" + rewardValue + "]";
	}
}
