package com.misys.stockmarket.achievements;

import com.misys.stockmarket.domain.entity.UserMaster;


public interface IAchievement {
	
	boolean evaluate(UserMaster userMaster, String category);
	
}
