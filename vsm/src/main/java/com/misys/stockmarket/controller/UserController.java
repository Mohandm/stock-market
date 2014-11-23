package com.misys.stockmarket.controller;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.mbeans.UserFormBean;
import com.misys.stockmarket.mbeans.UserProfileFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;
import com.misys.stockmarket.security.LoginResponse;
import com.misys.stockmarket.services.RegistrationService;
import com.misys.stockmarket.services.UserService;

@Controller
public class UserController {

	private static final Log LOG = LogFactory
			.getLog(UserController.class);
	
	@Inject
	private UserService userService;
	
	@Inject
	private RegistrationService registrationService;

	@RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage registerUser(@RequestBody UserFormBean userFormBean) {
		return registrationService.registerUser(userFormBean);
	}

	@RequestMapping(value = "/activateprofile/{token:.+}", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseMessage activateProfile(@PathVariable("token") String token) {
		return registrationService.activateProfile(token);
	}

	@RequestMapping(value = "/resetpassword", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage resetPassword(@RequestBody UserFormBean userFormBean) {
		return registrationService.resetPassword(userFormBean);
	}

	@RequestMapping(value = "/changepassword", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage changePassword(@RequestBody UserFormBean userFormBean) {
		return registrationService.changePassword(userFormBean);
	}
	
	@RequestMapping(value = "/getUser", method = { RequestMethod.GET,
			RequestMethod.POST})
	@ResponseBody
	public UserProfileFormBean getUser() {
		UserMaster user;
		try {
			user = userService.getLoggedInUser();
		
			UserProfileFormBean bean = new UserProfileFormBean();
			bean.setEmail(user.getEmail());
			bean.setFirstName(user.getFirstName());
			bean.setLastName(user.getLastName());
			bean.setGender(user.getGender());
			if (user.getCoins() != null) {
			bean.setCoins(user.getCoins());
			}
			else {
				bean.setCoins(0);
			}
		return bean;
		} catch (EmailNotFoundException e) {
			LOG.error(e);
			return null;
		}
	}
	
	@RequestMapping(value = "/getuserdetails", method = { RequestMethod.GET,
			RequestMethod.POST, RequestMethod.HEAD })
	@ResponseBody
	public LoginResponse getUserDetails() {
			return userService.getLoggedInUserResponse();
	}

}
