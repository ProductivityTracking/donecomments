package com.pennant.prodmtr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pennant.prodmtr.model.Dto.PTFilterCriteria;
import com.pennant.prodmtr.model.Dto.ProjectDto;
import com.pennant.prodmtr.model.Dto.ProjectTaskDTO;
import com.pennant.prodmtr.model.Dto.UserDto;
import com.pennant.prodmtr.model.Entity.ProjectTask;
import com.pennant.prodmtr.model.Input.ProjectTaskInput;
import com.pennant.prodmtr.service.Interface.ProjectService;
import com.pennant.prodmtr.service.Interface.ProjectTaskService;
import com.pennant.prodmtr.service.Interface.ResourceService;

@Controller
public class ProjectTaskController {

	private final ProjectTaskService projectTaskService;
	private final ResourceService resourceService;
	private final ProjectService projectService;

	@Autowired
	public ProjectTaskController(ProjectTaskService projectTaskService, ResourceService resourceService,
			ProjectService projectService) {
		this.projectTaskService = projectTaskService;
		this.resourceService = resourceService;
		this.projectService = projectService;
	}

	/**
	 * Creates a new project task based on the provided request and returns the created task as a DTO.
	 *
	 * @param request the ProjectTaskInput object containing the new task data
	 * @return the created project task as a ProjectTaskDTO
	 */
	@RequestMapping(value = "/createprojecttasks", method = RequestMethod.GET)
	public ProjectTaskDTO createProjectTask(@RequestBody ProjectTaskInput request) {
		ProjectTask projectTask = request.toEntity();
		ProjectTask createdTask = projectTaskService.createProjectTask(projectTask);
		return ProjectTaskDTO.fromEntity(createdTask);
	}

	/**
	 * Retrieves all project tasks and adds them to the model along with the list of resources and projects.
	 *
	 * @param model the Model object
	 * @return the view name for displaying the project tasks
	 */
	@RequestMapping(value = "/projecttasks", method = RequestMethod.GET)
	public String getAllProjectTasks(Model model) {

		List<ProjectTaskDTO> projectTasks = projectTaskService.getProjectTaskDTOList();

		List<UserDto> resources = resourceService.getAllResources();
		List<ProjectDto> projects = projectService.getAllProjects();

		model.addAttribute("resources", resources);
		model.addAttribute("projects", projects);

		model.addAttribute("projectTasks", projectTasks);
		return "projecttasks";
	}

	/**
	 * Shows the form for adding a new project task and adds necessary data to the model, such as the list of projects and resources.
	 *
	 * @param model the Model object
	 * @return the view name for the add project task form
	 */
	@RequestMapping(value = "/addProjectTaskForm", method = RequestMethod.GET)
	public String showAddProjectTaskForm(Model model) {
		// Add any necessary data to the model

		// For example, if you need to populate a dropdown list with projects or resources:
		List<ProjectDto> projects = projectService.getAllProjects();
		List<UserDto> resources = resourceService.getAllResources();
		model.addAttribute("projects", projects);
		model.addAttribute("resources", resources);

		// Return the view name for the add project task form
		return "newemp";
	}

	/**
	 * Adds a new project task based on the provided ProjectTaskInput and redirects to the project tasks view.
	 *
	 * @param projectTaskInput the ProjectTaskInput object containing the new task data
	 * @return the redirect view name for displaying the project tasks
	 */
	@RequestMapping(value = "/addprojecttask", method = RequestMethod.POST)
	public String addProjectTask(@Validated ProjectTaskInput projectTaskInput) {
		System.out.println(projectTaskInput.getAssignedUserId());
		System.out.println(projectTaskInput);
		ProjectTask projectTask = projectTaskInput.toEntity();
		System.out.println(projectTask.getproject().getProjectName());
		projectTaskService.createProjectTask(projectTask);
		return "redirect:/projecttasks";
	}

	/**
	 * Shows the details of a specific task based on the provided task ID and adds it to the model.
	 *
	 * @param taskId the ID of the task to display
	 * @param model  the Model object
	 * @return the view name for displaying the task details
	 */
	@RequestMapping(value = "/taskdetails", method = RequestMethod.GET)
	public String showTaskDetails(@RequestParam("taskId") int taskId, Model model) {
		// Process the task object and pass it to the view
		System.out.println(taskId);
		ProjectTaskDTO task = projectTaskService.getProjectTaskById(taskId); // Implement the method to retrieve the task object by ID
		System.out.println(task); // object by ID

		model.addAttribute("task", task);
		// Add other necessary logic

		return "ProjectTaskDetails";
	}

	/**
	 * Filters project tasks based on the provided filter criteria and returns the filtered tasks as JSON.
	 *
	 * @param filterCriteria the PTFilterCriteria object containing the filter criteria
	 * @param bindingResult  the BindingResult object for validation
	 * @return the JSON response containing the filtered tasks
	 */
	@RequestMapping(value = "/projectfiltertasks", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String filterTasks(@Validated PTFilterCriteria filterCriteria, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// Handle validation errors
			// Return appropriate error response
			return "Validation Error";
		}

		// Retrieve the filtered tasks based on the filter criteria
		List<ProjectTaskDTO> filteredTasks = projectTaskService.filterTasks(filterCriteria);

		// Convert the filtered tasks to JSON using Gson
		Gson gson = new Gson();
		String json = gson.toJson(filteredTasks);

		// Return the JSON response
		return json;
	}

	/**
	 * Retrieves the tasks associated with a specific project ID and adds them to the model.
	 *
	 * @param projId the ID of the project
	 * @param model  the Model object
	 * @return the view name for displaying the tasks associated with the project
	 */
	@RequestMapping(value = "/normaltasks", method = RequestMethod.GET)
	public String viewIndvtasks(@RequestParam("projId") Integer projId, Model model) {
		List<ProjectTask> tasks = projectTaskService.getTasksByProjectId(projId);
		System.out.println("got this data " + tasks.get(0).getTaskId());
		model.addAttribute("tasks", tasks);
		return "normaltasks";
	}

	// Other controller methods for updating, deleting project tasks

}
