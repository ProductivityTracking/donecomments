package com.pennant.prodmtr.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pennant.prodmtr.model.Dto.AnalyticsDto;
import com.pennant.prodmtr.model.view.Past30CompletionSummary;
import com.pennant.prodmtr.service.Interface.AnalyticService;

@Controller
public class AnalyticsController {
	
	@Autowired
	AnalyticService analyticService;

	/**
	 * Retrieves the analytics for a specific user based on the provided user_id.
	 *
	 * @param user_id the ID of the user
	 * @return JSON representation of the analytics for the user
	 * @throws SQLException if there is an error while retrieving the analytics
	 */
	@RequestMapping(value = "/getUserAnalytics", method = RequestMethod.POST)
	public @ResponseBody String getProjAnalysisById(@RequestParam("user_id") Integer user_id) throws SQLException {
		// Get the analytics DTO for the user
		AnalyticsDto analyticsDto = analyticService.getSummariesByUserId(user_id);
		
		// Convert the DTO to JSON using Gson
		Gson gson = new Gson();
		String json = gson.toJson(analyticsDto);
		
		System.out.println(user_id + "\n" + json);
		return json;
	}

	/**
	 * Retrieves the analytics summary for the past 30 days to be displayed on the dashboard.
	 *
	 * @param model the model object to add the analytics summary
	 * @return JSON representation of the past 30 days completion summary
	 * @throws SQLException if there is an error while retrieving the analytics
	 */
	@RequestMapping(value = "/getDashboardAnalytics", method = RequestMethod.POST)
	public @ResponseBody String getDashboardAnalytics(Model model) throws SQLException {
		// Get the past 30 days completion summary
		List<Past30CompletionSummary> past30CompletionSummary = analyticService.getPast30CompletionSummary();
		
		// Convert the summary to JSON using Gson
		Gson gson = new Gson();
		String json = gson.toJson(past30CompletionSummary);
		
		System.out.println("data" + json);
		return json;
	}

}
