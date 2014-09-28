package com.misys.stockmarket.handlers.user;

import com.misys.stockmarket.mbeans.user.UserRegFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;

public interface UserBizHandler {

	ResponseMessage registerUser(UserRegFormBean userRegFormBean);

	ResponseMessage resetPassword(UserRegFormBean userRegFormBean);

	ResponseMessage activateProfile(String verificationCode);

}
