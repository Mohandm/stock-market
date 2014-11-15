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

import com.misys.stockmarket.constants.IApplicationConstants;
import com.misys.stockmarket.domain.entity.LeagueMaster;
import com.misys.stockmarket.domain.entity.UserMaster;
import com.misys.stockmarket.exception.EmailNotFoundException;
import com.misys.stockmarket.exception.LeagueException;
import com.misys.stockmarket.exception.service.UserServiceException;
import com.misys.stockmarket.mbeans.FollowRequestFormBean;
import com.misys.stockmarket.mbeans.MyFollowersFormBean;
import com.misys.stockmarket.mbeans.MyFollowingFormBean;
import com.misys.stockmarket.mbeans.PlayersRecentTradesFormBean;
import com.misys.stockmarket.platform.web.ResponseMessage;
import com.misys.stockmarket.services.AlertsService;
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
	
	@Inject
	AlertsService alertService;
	
	@RequestMapping(value = "/myFollowers", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<MyFollowersFormBean> myFollowers(@RequestParam("leagueId") long leagueId) {
		try {
			UserMaster playerMaster = userService.getLoggedInUser();
			LeagueMaster leagueMaster = leagueService.getLeagueById(leagueId);
			return leagueService.getMyFollowers(playerMaster,leagueMaster);
		} 
		catch (EmailNotFoundException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return null;
		}
		catch (LeagueException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return null;
		}
	}
	
	@RequestMapping(value = "/myFollowing", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<MyFollowingFormBean> myFollowing(@RequestParam("leagueId") long leagueId) {
		try {
			UserMaster userMaster = userService.getLoggedInUser();
			LeagueMaster leagueMaster = leagueService.getLeagueById(leagueId);
			return leagueService.getMyFollowing(userMaster,leagueMaster);
		} 
		catch (EmailNotFoundException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return null;
		}
		catch (LeagueException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return null;
		}
	}
	
	@RequestMapping(value = "/playersRecentTrades", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public List<PlayersRecentTradesFormBean> playersRecentTrades(
			@RequestParam("leagueId") long leagueId, @RequestParam("userId") long userId) {
		try {
			UserMaster userMaster = userService.getLoggedInUser();
			LeagueMaster leagueMaster = leagueService.getLeagueById(leagueId);
			UserMaster playerMaster = userService.findById(userId);
			if(!leagueService.getLeagueUserAccessCheck(leagueId, userId))
			{
				LOG.error("You have No Access to the League");
				return null;
			}
			if(!leagueService.checkFollowerAccess(userMaster, playerMaster, leagueMaster))
			{
				LOG.error("You are not following the Player");
				return null;
			}
			return leagueService.getPlayersRecentTrades(leagueMaster,playerMaster);
		} catch (EmailNotFoundException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return null;
		}
		catch (UserServiceException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return null;		
		}
		catch (LeagueException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return null;
		}
	}
	
	@RequestMapping(value = "/acceptFollowRequest", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage acceptFollowRequest(
			@RequestBody FollowRequestFormBean followRequestFormBean) {
		try {
			UserMaster playerMaster = userService.getLoggedInUser();
			UserMaster userMaster = userService.findById(Long.valueOf(followRequestFormBean.getUserId()).longValue());
			if(userMaster.getUserId() == playerMaster.getUserId())
			{
				return new ResponseMessage(ResponseMessage.Type.error,"You can't follow yourself");
			}
			else
			{
				LeagueMaster leagueMaster = leagueService.getLeagueById(Long.valueOf(followRequestFormBean.getLeagueId()).longValue());
				leagueService.followerStatuschange(userMaster, playerMaster, leagueMaster, IApplicationConstants.FOLLOWER_STATUS_PENDING, IApplicationConstants.FOLLOWER_STATUS_ACCEPTED);
				String message = "Your follower request has been accepted by " + playerMaster.getFirstName(); 
				alertService.saveUserAlerts(userMaster, message, IApplicationConstants.ALERT_TYPE_NOTIFICATIONS);
				return new ResponseMessage(ResponseMessage.Type.success,"Follow request accepted!");
			}
		} catch (EmailNotFoundException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.error,"There was a technical error while processing your order. Please try again");
		} catch (UserServiceException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.error,"There was a technical error while processing your order. Please try again");
		} catch (NumberFormatException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.error,"There was a technical error while processing your order. Please try again");
		} catch (LeagueException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.error,"There was a technical error while processing your order. Please try again");
		}
	}
	
	@RequestMapping(value = "/rejectFollowRequest", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage rejectFollowRequest(
			@RequestBody FollowRequestFormBean followRequestFormBean) {
		try {
			UserMaster playerMaster = userService.getLoggedInUser();
			UserMaster userMaster = userService.findById(Long.valueOf(followRequestFormBean.getUserId()).longValue());
			LeagueMaster leagueMaster = leagueService.getLeagueById(Long.valueOf(followRequestFormBean.getLeagueId()).longValue());
			leagueService.followerStatuschange(userMaster, playerMaster, leagueMaster, IApplicationConstants.FOLLOWER_STATUS_PENDING, IApplicationConstants.FOLLOWER_STATUS_REJECTED);
			return new ResponseMessage(ResponseMessage.Type.success,"Follow request rejected!");
		} catch (EmailNotFoundException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.error,"There was a technical error while processing your order. Please try again");
		} catch (UserServiceException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.error,"There was a technical error while processing your order. Please try again");
		} catch (NumberFormatException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.error,"There was a technical error while processing your order. Please try again");
		} catch (LeagueException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.error,"There was a technical error while processing your order. Please try again");
		}
	}
	
	@RequestMapping(value = "/disallowFollowRequest", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage disallowFollowRequest(
			@RequestBody FollowRequestFormBean followRequestFormBean) {
		try {
			UserMaster playerMaster = userService.getLoggedInUser();
			UserMaster userMaster = userService.findById(Long.valueOf(followRequestFormBean.getUserId()).longValue());
			LeagueMaster leagueMaster = leagueService.getLeagueById(Long.valueOf(followRequestFormBean.getLeagueId()).longValue());
			leagueService.followerStatuschange(userMaster, playerMaster, leagueMaster, IApplicationConstants.FOLLOWER_STATUS_ACCEPTED, IApplicationConstants.FOLLOWER_STATUS_REJECTED);
			return new ResponseMessage(ResponseMessage.Type.success,"Follower Disallowed!");
		} catch (EmailNotFoundException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.error,"There was a technical error while processing your order. Please try again");
		} catch (UserServiceException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.error,"There was a technical error while processing your order. Please try again");
		} catch (NumberFormatException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.error,"There was a technical error while processing your order. Please try again");
		} catch (LeagueException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.error,"There was a technical error while processing your order. Please try again");
		}
	}
	
	@RequestMapping(value = "/stopFollowingRequest", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMessage stopFollowingRequest(
			@RequestBody FollowRequestFormBean followRequestFormBean) {
		try {
			UserMaster userMaster = userService.getLoggedInUser();
			UserMaster playerMaster = userService.findById(Long.valueOf(followRequestFormBean.getUserId()).longValue());
			LeagueMaster leagueMaster = leagueService.getLeagueById(Long.valueOf(followRequestFormBean.getLeagueId()).longValue());
			leagueService.followerStatuschange(userMaster, playerMaster, leagueMaster, IApplicationConstants.FOLLOWER_STATUS_ACCEPTED, IApplicationConstants.FOLLOWER_STATUS_REJECTED);
			return new ResponseMessage(ResponseMessage.Type.success,"Following stopped!");

		} catch (EmailNotFoundException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.error,"There was a technical error while processing your order. Please try again");
		} catch (UserServiceException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.error,"There was a technical error while processing your order. Please try again");
		} catch (NumberFormatException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.error,"There was a technical error while processing your order. Please try again");
		} catch (LeagueException e) {
			// TODO: HANDLE WHEN EXCEPTION
			LOG.error(e);
			return new ResponseMessage(ResponseMessage.Type.error,"There was a technical error while processing your order. Please try again");
		}
	}
}
