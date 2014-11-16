package com.misys.stockmarket.achievements;

import java.math.BigDecimal;

import com.misys.stockmarket.domain.entity.UserMaster;

public interface Achievement {
	
	/**
	 * Calculate the quantity for a particular user
	 * @return
	 */
	public BigDecimal getQuantity(UserMaster user);
	
	public String getName();
	
}
