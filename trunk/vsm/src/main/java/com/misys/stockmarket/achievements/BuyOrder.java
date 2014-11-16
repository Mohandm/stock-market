package com.misys.stockmarket.achievements;

import java.math.BigDecimal;

import com.misys.stockmarket.domain.entity.UserMaster;

public class BuyOrder implements Achievement {

	@Override
	public BigDecimal getQuantity(UserMaster user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "BuyOrder";
	}

}
