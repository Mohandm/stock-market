package com.misys.stockmarket.controller;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.misys.stockmarket.mbeans.AchievementFormBean;
import com.misys.stockmarket.services.AchievementsService;

@Controller
public class AchievementController {

	private static final Log LOG = LogFactory
			.getLog(AchievementController.class);

	@Inject
	AchievementsService achievementsService;

	@RequestMapping(value = "/getUnpublishedAchievements", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<AchievementFormBean> getUnpublishedAchievements() {
		return achievementsService.getUnpublishedAchievements();
	}
	
	@RequestMapping(value = "/getCompletedAchievements", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<AchievementFormBean> getCompletedAchievments() {
		return achievementsService.getCompletedAchievements();
	}

	@RequestMapping(value = "/getPendingAchievements", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<AchievementFormBean> getPendingAchievements() {
		return achievementsService.getPendingAchievements();
	}
}
