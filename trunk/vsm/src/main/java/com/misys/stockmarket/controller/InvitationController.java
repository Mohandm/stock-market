package com.misys.stockmarket.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.misys.stockmarket.mbeans.UserFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;
import com.misys.stockmarket.services.InvitationService;

@Controller
public class InvitationController {

	@Inject
	private InvitationService invitationService;

	@RequestMapping(value = "/inviteuser", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage inviteUser(@RequestBody UserFormBean userFormBean) {
		return invitationService.inviteUser(userFormBean);
	}
	
	@RequestMapping(value = "/acceptinvitation/{token:.+}", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseMessage acceptInvitation(@PathVariable("token") String token) {
		return invitationService.acceptInvitation(token);
	}
}
