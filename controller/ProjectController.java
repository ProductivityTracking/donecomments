package com.pennant.prodmtr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.pennant.prodmtr.model.Dto.ProjectDto;
import com.pennant.prodmtr.model.Dto.ProjectFilter;
import com.pennant.prodmtr.model.Entity.User;
import com.pennant.prodmtr.model.Input.ProjectInput;
import com.pennant.prodmtr.service.Interface.ProjectService;
import com.pennant.prodmtr.service.Interface.ResourceService;

@RestController
public class ProjectController {

	private final ProjectService projectService;
	private final ResourceService resourceService;

	@Autowired
	public ProjectController(ProjectService projectService, ResourceService resourceService) {
		this.projectService = projectService;
		this.resourceService = resourceService;
	}

	/**
	 * Retrieves all projects and adds them to the model.
	 *
	 * @param model the Model object
	 * @return the view name for displaying the list of projects
	 */
	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public String getAllProjects(Model model) {
		System.out.println("projects called");
		List<ProjectDto> projectDto = projectService.getAllProjects();
		model.addAttribute("projectDto", projectDto);
		return "emplist";
	}

	/**
	 * Filters projects based on the specified project filter and returns the filtered projects as JSON.
	 *
	 * @param projectFilter the ProjectFilter object containing the filter criteria
	 * @return the filtered projects as JSON
	 */
	@RequestMapping(value = "/projectfilter", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String filterProjects(@Validated ProjectFilter projectFilter) {
		System.out.println(projectFilter.getProjectId());
		System.out.println(projectFilter.getStatus());
		System.out.println("getFilterProjects called");

		List<ProjectDto> filteredProjects = projectService.filterProjects(projectFilter);
		System.out.println("filteredProjects");

		Gson gson = new Gson();
		String json = gson.toJson(filteredProjects);

		return json;
	}

	/**
	 * Saves the new project data.
	 *
	 * @param projectInput the ProjectInput object containing the new project data
	 * @param model        the Model object
	 * @return a redirect to the "/projects" URL
	 */
	@RequestMapping(value = "/savenewproject", method = RequestMethod.GET)
	public String setNewProjectData(@Validated ProjectInput projectInput, Model model) {
		System.out.println("proj manager " + projectInput.getProjectManager());
		projectService.setNewProject(projectInput);
		return "redirect:/projects";
	}

	/**
	 * Retrieves the details of a specific project by ID and adds them to the model.
	 *
	 * @param projectId the ID of the project
	 * @param model     the Model object
	 * @return the view name for displaying the project details
	 */
	@RequestMapping(value = "/projectDetails", method = RequestMethod.GET)
	public String projectDetails(@RequestParam("projectId") Integer projectId, Model model) {
		// Retrieve the selected sprint details from the database and add them to the model
		ProjectDto projectDto = projectService.getProjectById(projectId);
		model.addAttribute("projectDto", projectDto);
		System.out.println("here " + projectDto);
		// Return the appropriate view or redirect
		return "projectDetails";
	}

	/**
	 * Retrieves the view for creating a new project and adds the list of all project managers to the model.
	 *
	 * @param model the Model object
	 * @return the view name for creating a new project
	 */
	@RequestMapping(value = "/createproject", method = RequestMethod.GET)
	public String setNewProject(Model model) {
		List<User> allMngs = resourceService.getAllProjManagers();
		System.out.println("proj mngs " + allMngs);
		System.out.println("add new project jsp called");
		model.addAttribute("allMngs", allMngs);
		return "safety";
	}

	/**
	 * Retrieves the resource details for a specific project ID and adds them to the model.
	 *
	 * @param projectId the ID of the project
	 * @param model     the Model object
	 * @return the view name for displaying the resource details
	 */
	@RequestMapping(value = "/resourceDetailsByProjId", method = RequestMethod.GET)
	public String getResourceDetailsByProjectId(@RequestParam("projectId") int projectId, Model model) {
		List<User> resources = resourceService.getUsersByProjectId(projectId);
		System.out.println("resources " + resources);
		model.addAttribute("resources", resources);
		return "projectResourceDetails";
	}
}
