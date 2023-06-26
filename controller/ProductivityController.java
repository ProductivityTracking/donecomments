package com.pennant.prodmtr.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pennant.prodmtr.model.Entity.Subtask;
import com.pennant.prodmtr.model.Entity.Task;
import com.pennant.prodmtr.model.Entity.User;
import com.pennant.prodmtr.service.Interface.UserService;

@Controller
public class ProductivityController {

	UserService userService;

	@Autowired
	public ProductivityController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Retrieves the index page for the productivity section.
	 *
	 * @param model the Model object
	 * @return the view name for the index page
	 */
	@RequestMapping(value = "/productivity", method = RequestMethod.GET)
	public String getIndex(Model model) {
		return "productivity";
	}

	/**
	 * Alternative method for retrieving the index page for the productivity section.
	 *
	 * @param model the Model object
	 * @return the view name for the index page
	 */
	@RequestMapping(value = "/prod", method = RequestMethod.GET)
	public String getIndexAlternative(Model model) {
		return "productivity";
	}

	/**
	 * Retrieves the dashboard page.
	 *
	 * @param model the Model object
	 * @return the view name for the dashboard page
	 */
	@RequestMapping(value = "/dash", method = RequestMethod.GET)
	public String dash(Model model) {
		return "dash";
	}

	/**
	 * Retrieves the past due page.
	 *
	 * @param model the Model object
	 * @return the view name for the past due page
	 */
	@RequestMapping(value = "/pastdue", method = RequestMethod.GET)
	public String pastdue(Model model) {
		return "pastdue";
	}

	/**
	 * Retrieves the resource page.
	 *
	 * @param model the Model object
	 * @return the view name for the resource page
	 */
	@RequestMapping(value = "/resource", method = RequestMethod.GET)
	public String resource(Model model) {
		return "AddResource";
	}

	/**
	 * Retrieves the project page.
	 *
	 * @param model the Model object
	 * @return the view name for the project page
	 */
	@RequestMapping(value = "/project", method = RequestMethod.GET)
	public String project(Model model) {
		System.out.println("project called");
		return "project";
	}


	/**
	 * Retrieves the dashboard page.
	 *
	 * @param model the Model object
	 * @return the view name for the dashboard page
	 */
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String getdashboard(Model model) {
		System.out.println("project called");
		return "dashboard";
	}

	/**
	 * Retrieves the analytics page.
	 *
	 * @param model the Model object
	 * @return the view name for the analytics page
	 */
	@RequestMapping(value = "/analytics", method = RequestMethod.GET)
	public String getanalytics(Model model) {
		System.out.println("project called");
		return "analytics";
	}

	/**
	 * Retrieves the backlog page.
	 *
	 * @param model the Model object
	 * @return the view name for the backlog page
	 */
	@RequestMapping(value = "/backlog", method = RequestMethod.GET)
	public String getmodules(Model model) {
		System.out.println("backlog called");
		return "backlog";
	}

	/**
	 * Retrieves the profile page.
	 *
	 * @param model the Model object
	 * @return the view name for the profile page
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String getprofile(Model model) {
		System.out.println("profile called");
		// List<User> User = ProfileService.getProfile(id);
		return "profile";
	}

	/**
	 * Retrieves the edit page.
	 *
	 * @param model the Model object
	 * @param id    the user ID
	 * @return the view name for the edit page
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String getedit(Model model, @RequestParam("id") Integer id) {
		System.out.println("edit requested");
		model.addAttribute("id", id);
		return "edit";
	}

	/**
	 * Retrieves the edit success page after updating the user's password.
	 *
	 * @param model    the Model object
	 * @param id       the user ID
	 * @param password the updated password
	 * @return the view name for the edit success page
	 */
	@RequestMapping(value = "/editsuccess", method = RequestMethod.POST)
	public String geteditsuccess(Model model, @RequestParam("id") Integer id,
			@RequestParam("Password") String password) {
		System.out.println("editsuccess");
		userService.UpdatePassword(id, password);
		model.addAttribute("id", id);
		return "edit";
	}

	/**
	 * Retrieves the activity page and fetches the activities for the logged-in user.
	 *
	 * @param model   the Model object
	 * @param session the HttpSession object
	 * @return the view name for the activity page
	 */
	@RequestMapping(value = "/activity", method = RequestMethod.GET)
	public String getActivity(Model model, HttpSession session) {
		System.out.println("Called Activity jsp");
		User user = (User) session.getAttribute("user");
		List<Task> activityTasks = userService.getUserActivities(user.getUserRole());
		List<Subtask> activitySubTasks = userService.getUserSubtaskActivities(user.getUserRole());
		model.addAttribute("activityTasks", activityTasks);
		model.addAttribute("activitySubTasks", activitySubTasks);
		return "activity";
	}
}
