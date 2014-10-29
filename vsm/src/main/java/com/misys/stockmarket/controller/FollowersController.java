package com.misys.stockmarket.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.misys.stockmarket.mbeans.FollowRequestFormBean;
import com.misys.stockmarket.mbeans.MyFollowersFormBean;
import com.misys.stockmarket.mbeans.MyFollowingFormBean;
import com.misys.stockmarket.mbeans.PlayersRecentTradesFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;
import com.misys.stockmarket.services.LeagueService;
import com.misys.stockmarket.services.UserService;

/**
 * @author Gurudath Reddy
 * @version 1.0
 */
@Controller
public class FollowersController {

	private static final Log LOG = LogFactory
			.getLog(LeagueController.class);
	
	@Inject
	LeagueService leagueService;
	
	@Inject
	UserService userService;
	
	@RequestMapping(value = "/myFollowers", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<MyFollowersFormBean> myFollowers() {
		//TODO Guru : Add Logic
		return new ArrayList<MyFollowersFormBean>();
	}
	
	@RequestMapping(value = "/myFollowing", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<MyFollowingFormBean> myFollowing() {
		//TODO Guru : Add Logic
		return new ArrayList<MyFollowingFormBean>();
	}
	
	@RequestMapping(value = "/playersRecentTrades", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<PlayersRecentTradesFormBean> playersRecentTrades(
			@RequestParam("leagueId") long leagueId, @RequestParam("userId") long userId) {
		//TODO Guru : Add Logic
		//TODO Guru : Test if the logged-in user has access to the league and also is following the "userId" sent 
		return new ArrayList<PlayersRecentTradesFormBean>();
	}
	
	@RequestMapping(value = "/acceptFollowRequest", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage acceptFollowRequest(
			@RequestBody FollowRequestFormBean followRequestFormBean) {
		//TODO Guru : Add Logic
		return new ResponseMessage(ResponseMessage.Type.success,"");
	}
	
	@RequestMapping(value = "/rejectFollowRequest", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage rejectFollowRequest(
			@RequestBody FollowRequestFormBean followRequestFormBean) {
		//TODO Guru : Add Logic
		return new ResponseMessage(ResponseMessage.Type.success,"");
	}
	
	@RequestMapping(value = "/disallowFollowRequest", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage disallowFollowRequest(
			@RequestBody FollowRequestFormBean followRequestFormBean) {
		//TODO Guru : Add Logic
		return new ResponseMessage(ResponseMessage.Type.success,"");
	}
	
	@RequestMapping(value = "/stopFollowingRequest", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage stopFollowingRequest(
			@RequestBody FollowRequestFormBean followRequestFormBean) {
		//TODO Guru : Add Logic
		return new ResponseMessage(ResponseMessage.Type.success,"");
	}
}
