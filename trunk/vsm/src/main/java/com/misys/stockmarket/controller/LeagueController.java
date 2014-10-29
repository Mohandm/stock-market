package com.misys.stockmarket.controller;

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

import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.LeagueException;
import com.misys.stockmarket.exception.ServiceException;
import com.misys.stockmarket.mbeans.FollowRequestFormBean;
import com.misys.stockmarket.mbeans.LeaderBoardFormBean;
import com.misys.stockmarket.mbeans.LeagueIdFormBean;
import com.misys.stockmarket.mbeans.LeaguePlayerFormBean;
import com.misys.stockmarket.mbeans.MyLeagueFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;
import com.misys.stockmarket.services.LeagueService;
import com.misys.stockmarket.services.UserService;

/**
 * @author Gurudath Reddy
 * @version 1.0
 */
@Controller
public class LeagueController {

	private static final Log LOG = LogFactory
			.getLog(LeagueController.class);
	
	@Inject
	LeagueService leagueService;
	
	@Inject
	UserService userService;
	
	@RequestMapping(value = "/gameLeaguesList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<MyLeagueFormBean> listAllUserGameLeagues() {
		try {
			return leagueService.getMyLeagues(userService.getLoggedInUser().getUserId());
		} catch (ServiceException e) {
			LOG.error(e);
			// TODO: HANDLE WHEN EXCEPTION
			return null;
		} catch (EmailNotFoundException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return null;
		}
	}
	
	@RequestMapping(value = "/leaguesUsersList", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<LeaguePlayerFormBean> getLeaguePlayers(
			@RequestParam("leagueId") long leagueId) {
		try {
			return leagueService.getLeaguePlayers(leagueId);
		}
		catch (LeagueException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return null;
		} 
	}
	
	@RequestMapping(value = "/unlockLeague", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage unlockLeague(
			@RequestBody LeagueIdFormBean leagueIdFormBean) {
		//TODO Guru : Add Logic
		return new ResponseMessage(ResponseMessage.Type.success,"");
	}
	
	@RequestMapping(value = "/leaderBoard", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public LeaderBoardFormBean getLeaderBoard() {
		//TODO Guru : Add Logic
		return new LeaderBoardFormBean();
	}
	
	@RequestMapping(value = "/followUser", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage followPlayer(
			@RequestBody FollowRequestFormBean followRequestFormBean) {
		//TODO Guru : Add Logic
		return new ResponseMessage(ResponseMessage.Type.success,"");
	}
}
