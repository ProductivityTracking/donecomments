package com.pennant.prodmtr.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pennant.prodmtr.model.Dto.ProjectDto;
import com.pennant.prodmtr.model.Dto.ResTaskFilter;
import com.pennant.prodmtr.model.Dto.ResourceFilter;
import com.pennant.prodmtr.model.Dto.TaskDto;
import com.pennant.prodmtr.model.Dto.UserDto;
import com.pennant.prodmtr.model.Entity.Role;
import com.pennant.prodmtr.model.Entity.User;
import com.pennant.prodmtr.model.Input.UserInput;
import com.pennant.prodmtr.model.view.TaskCountview;
import com.pennant.prodmtr.service.Interface.ProjectService;
import com.pennant.prodmtr.service.Interface.ResourceService;
import com.pennant.prodmtr.service.Interface.RoleService;
import com.pennant.prodmtr.service.Interface.TaskService;

@Controller
public class ResourceController {

	private final ResourceService resourceService;
	private final ProjectService projectService;
	private final RoleService roleService;
	private final TaskService taskService;

	@Autowired
	private User user;

	@Autowired
	private Role role;

	@Autowired
	public ResourceController(ResourceService resourceService, ProjectService projectService, RoleService roleService,
			TaskService taskService) {
		this.resourceService = resourceService;
		this.projectService = projectService;
		this.roleService = roleService;
		this.taskService = taskService;
	}

	/**
	 * Retrieves all resources and adds them to the model along with the list of projects and roles.
	 * Calculates performance score, hours worked, and tasks completed for each resource.
	 *
	 * @param model the Model object
	 * @return the view name for displaying the resources
	 */
	@RequestMapping(value = "/resources", method = RequestMethod.GET)
	public String getAllResources(Model model) {
		List<UserDto> resources = resourceService.getAllResources();
		List<ProjectDto> projects = projectService.getAllProjects();
		List<Role> roles = roleService.getAllRoles();

		// Calculate performance score, hours worked, and tasks completed for each resource
		for (UserDto resource : resources) {
			int completedTasks = taskService.getCompletedTasksByUserId(resource.getUserId());
			int totalTasks = taskService.getTotalTasksByUserId(resource.getUserId());
			double performanceScore = taskService.calculatePerformanceScore(completedTasks, totalTasks);

			// Retrieve hours worked and tasks completed
			double hoursWorked = taskService.getHoursWorkedByUserId(resource.getUserId());

			resource.setPerformanceScore(performanceScore);
			resource.setHoursWorked(hoursWorked);
			resource.setTasksCompleted(completedTasks);
		}

		model.addAttribute("resources", resources);
		model.addAttribute("projects", projects);
		model.addAttribute("roles", roles);

		return "ResourceHome";
	}

	/**
	 * Filters resources based on the provided filter criteria and returns the filtered resources as JSON.
	 *
	 * @param resourceFilter the ResourceFilter object containing the filter criteria
	 * @param bindingResult  the BindingResult object for validation
	 * @return the JSON response containing the filtered resources
	 */
	@RequestMapping(value = "/resources/filter", method = RequestMethod.GET)
	@ResponseBody
	public String filterResources(@Validated ResourceFilter resourceFilter, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// Handle validation errors
			// Return appropriate error response
			return "Validation Error";
		}

		List<UserDto> filteredResources = resourceService.filterResources(resourceFilter);
		Gson gson = new Gson();
		String json = gson.toJson(filteredResources);

		return json;
	}

	/**
	 * Retrieves the details of a specific resource based on the provided display name and adds it to the model.
	 *
	 * @param displayName the display name of the resource
	 * @param model       the Model object
	 * @return the view name for displaying the resource details
	 */
	@RequestMapping(value = "/resources/details", method = RequestMethod.GET)
	public String getResourceDetails(@RequestParam(name = "displayName") String displayName, Model model) {
		User resource = resourceService.getResourceByDisplayName(displayName);
		model.addAttribute("resource", resource);

		return "user_details";
	}

	/**
	 * Retrieves a resource for updating based on the provided display name and adds it to the model along with the list of roles.
	 *
	 * @param displayName the display name of the resource to update
	 * @param model       the Model object
	 * @return the view name for updating the resource
	 */
	@RequestMapping(value = "/resources/update", method = RequestMethod.GET)
	public String updateResource(@RequestParam("displayName") String displayName, Model model) {
		User resource = resourceService.getResourceByDisplayName(displayName);
		List<Role> roles = roleService.getAllRoles();

		model.addAttribute("resource", resource);
		model.addAttribute("roles", roles);

		return "update_resource";
	}

	/**
	 * Updates a resource based on the provided UserInput and redirects to the resources page.
	 *
	 * @param userinput the UserInput object containing the updated resource data
	 * @return the redirect view name for displaying the resources page
	 */
	@RequestMapping(value = "/resources/updateSuccess", method = RequestMethod.POST)
	public String updateResource(@Validated UserInput userinput) {
		// Retrieve the existing resource from the database using the original display name

		// Save the updated resource
		resourceService.save(userinput);

		// Redirect to the resources page or show a success message
		return "redirect:/resources";
	}

	/**
	 * Displays the form for adding a new resource and adds the list of roles to the model.
	 *
	 * @param model the Model object
	 * @return the view name for adding a new resource
	 */
	@RequestMapping(value = "/resources/AddResource", method = RequestMethod.GET)
	public String addResource(Model model) {
		List<Role> roles = roleService.getAllRoles();
		model.addAttribute("roles", roles);

		return "AddResource";
	}

	/**
	 * Adds a new resource based on the provided UserInput and redirects to the resources page.
	 *
	 * @param userinput the UserInput object containing the new resource data
	 * @param model     the Model object
	 * @return the redirect view name for displaying the resources page
	 */
	@RequestMapping(value = "/resources/addSuccess", method = RequestMethod.POST)
	public String addResource(@Validated UserInput userinput, Model model) {
		userinput.setUserCreationDate(new Date()); // Set current date as the creation date
		userinput.setUserLastUpdatedDate(new Date());

		resourceService.addUser(userinput);

		return "redirect:/resources";
	}

	/**
	 * Retrieves the tasks associated with a specific user ID and adds them to the model along with the list of projects.
	 * Groups the tasks by project ID and counts the number of tasks per project.
	 *
	 * @param userId the ID of the user
	 * @param model  the Model object
	 * @return the view name for displaying the tasks
	 */
	@RequestMapping(value = "/resources/tasks", method = RequestMethod.GET)
	public String viewTasksForUser(@RequestParam("userId") int userId, Model model) {
		List<TaskDto> tasks = taskService.getTasksByUserId(userId);
		List<ProjectDto> projects = projectService.getAllProjects();

		Map<Integer, Integer> projectTaskCount = new HashMap<>();
		for (TaskDto task : tasks) {
			Integer projectId = task.getProjectId();
			int count = projectTaskCount.getOrDefault(projectId, 0);
			projectTaskCount.put(projectId, count + 1);
		}

		List<TaskCountview> taskCountList = new ArrayList<>();
		for (Map.Entry<Integer, Integer> entry : projectTaskCount.entrySet()) {
			int projectId = entry.getKey();
			int taskCount = entry.getValue();
			TaskCountview taskCountDto = new TaskCountview(projectId, taskCount);
			taskCountList.add(taskCountDto);
		}

		model.addAttribute("userId", userId);
		model.addAttribute("tasks", tasks);
		model.addAttribute("projects", projects);
		model.addAttribute("taskCountList", taskCountList);

		return "TasksByName";
	}

	/**
	 * Filters tasks based on the provided ResTaskFilter criteria and returns the filtered tasks as JSON.
	 *
	 * @param resTaskFilter the ResTaskFilter object containing the filter criteria
	 * @param bindingResult the BindingResult object for validation
	 * @return the JSON response containing the filtered tasks
	 */
	@RequestMapping(value = "resources/tasks/filter", method = RequestMethod.GET)
	@ResponseBody
	public String filterTasks(@Validated ResTaskFilter resTaskFilter, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// Handle validation errors
			// Return appropriate error response
			return "Validation Error";
		}

		List<TaskDto> tasks = taskService.filterTasks(resTaskFilter);
		Gson gson = new Gson();
		String json = gson.toJson(tasks);

		return json;
	}

	/**
	 * Retrieves the details of a resource based on the provided user ID and adds it to the model.
	 *
	 * @param userId the ID of the resource
	 * @param model  the Model object
	 * @return the view name for displaying the resource details
	 */
	@RequestMapping(value = "/user_details", method = RequestMethod.GET)
	public String getResourceDetailsById(@RequestParam(name = "userId") int userId, Model model) {
		User resource = resourceService.getResourceById(userId);
		model.addAttribute("resource", resource);

		return "user_details";
	}

}
