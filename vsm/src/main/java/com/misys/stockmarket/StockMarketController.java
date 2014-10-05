package com.misys.stockmarket;


import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.misys.stockmarket.handlers.user.UserBizHandler;
import com.misys.stockmarket.mbeans.user.UserRegFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;
import com.misys.stockmarket.services.user.UserValidatorService;
 
/**
 * @author Gurudath Reddy
 * @version 1.0
 */
@Controller
public class StockMarketController {
     
	@Inject
	UserBizHandler userBizHandler;

	@Inject
	UserValidatorService userValidatorService;

	@RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage registerUser(@RequestBody UserRegFormBean userRegFormBean) {
		return userBizHandler.registerUser(userRegFormBean);
	}
	
	@RequestMapping(value = "/activateprofile/{token}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public ResponseMessage activateProfile(@PathVariable("token") String token) {
		return userBizHandler.activateProfile(token);
	}

	@RequestMapping(value = "/resetpassword", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage resetPassword(@RequestBody UserRegFormBean userRegFormBean) {
		return userBizHandler.resetPassword(userRegFormBean);
	}
	@RequestMapping("/")
    public String index(Model model) {
        return "index";
    }
    
    @RequestMapping("/game")
    public String app(Model model) {
        return "game";
    }
}

