package com.pennant.prodmtr.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pennant.prodmtr.model.Dto.TFilterCriteria;
import com.pennant.prodmtr.model.Dto.TaskDto;
import com.pennant.prodmtr.model.Entity.Task;
import com.pennant.prodmtr.model.view.TaskUpdateFormModel;
import com.pennant.prodmtr.service.Interface.TaskService;

@Controller
public class TaskController {

	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	/**
	 * Retrieves the tasks associated with a user.
	 *
	 * @param model the Model object
	 * @return the view name for the task list page
	 */
	@RequestMapping(value = "/tasksbyid", method = RequestMethod.GET)
	public String viewTasksForUser(Model model) {
		// Use the userId to fetch the tasks
		int userId = 1; // Replace with actual userId
		List<TaskDto> tasks = taskService.getTasksByUserId(userId);

		// Add the tasks to the model
		model.addAttribute("tasks", tasks);

		// Return the view name
		return "Taskslist"; // Replace with actual view name
	}

	/**
	 * Retrieves all tasks.
	 *
	 * @param model the Model object
	 * @return the view name for the task list page
	 */
	@RequestMapping(value = "/tasks", method = RequestMethod.GET)
	public String viewAllTasks(Model model) {
		// Use the userId to fetch the tasks
		// Replace with actual userId
		List<TaskDto> tasks = taskService.getAllTasks();

		// Add the tasks to the model
		model.addAttribute("tasks", tasks);

		// Return the view name
		return "Taskslist"; // Replace with actual view name
	}

	/**
	 * Retrieves task details by task ID.
	 *
	 * @param taskId the ID of the task
	 * @param model  the Model object
	 * @return the view name for the task details page
	 */
	@RequestMapping(value = "/taskdetailsbyid", method = RequestMethod.GET)
	public String getAllTasks(@RequestParam("taskId") int taskId, Model model) {

		List<TaskDto> tasks = taskService.getTasksByUserId(taskId);

		model.addAttribute("tasks", tasks);

		return "taskdetailsbyid";
	}

	/**
	 * Updates the status of a task.
	 *
	 * @param taskId the ID of the task to update
	 * @param model  the Model object
	 * @return the view name for the task list page
	 */
	@RequestMapping(value = "/updateTaskStatus", method = RequestMethod.POST)
	public String updateTaskStatus(@RequestParam("taskId") int taskId, Model model) {
		// Retrieve the existing task from the database using the task ID
		Task task = taskService.getTaskById(taskId);
		model.addAttribute("task", task);
		return "Taskslist";
	}

	/**
	 * Handles the success scenario after updating the task status.
	 *
	 * @param taskId the ID of the task that was updated
	 * @return the redirect URL to the task list page
	 */
	@RequestMapping(value = "/updateSuccess", method = RequestMethod.POST)
	public String updateTaskStatusSuccess(@RequestParam("taskId") int taskId) {

		// Retrieve the existing task from the database using the task ID
		Boolean task = taskService.updateStatus(taskId);

		// Update the task status

		// Redirect to the task list page or show a success message
		return "redirect:/tasks";
	}

	/**
	 * Filters tasks based on the provided criteria.
	 *
	 * @param filterCriteria the TFilterCriteria object containing the filter criteria
	 * @param bindingResult  the BindingResult object for validation errors
	 * @return the JSON response containing the filtered tasks
	 */
	@PostMapping(value = "/Taskfilter", produces = "application/json")
	@ResponseBody
	public String PtfilterTasks(@Validated TFilterCriteria filterCriteria, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// Handle validation errors
			// Return appropriate error response
			return "Validation Error";
		}
		System.out.println(filterCriteria.getTaskSupervisorId());
		List<TaskDto> filteredTasks = taskService.PtfilterTasks(filterCriteria);

		Gson gson = new Gson();
		String json = gson.toJson(filteredTasks);

		// Return the JSON response
		return json;
	}

	/**
	 * Retrieves individual tasks based on the project ID.
	 *
	 * @param projId the ID of the project
	 * @param model  the Model object
	 * @return the view name for the individual tasks page
	 */
	@RequestMapping(value = "/Indvtasks", method = RequestMethod.GET)
	public String viewIndvtasks(@RequestParam("projId") Integer projId, Model model) {
		List<Task> tasks = taskService.getTasksByProjectId(projId);
		model.addAttribute("tasks", tasks);
		return "Indvtasks";
	}

	/**
	 * Sets the task status for updating.
	 *
	 * @param taskId  the ID of the task
	 * @param model   the Model object
	 * @param session the HttpSession object
	 * @return the view name for the task status update page
	 */
	@RequestMapping(value = "/setTaskStatus", method = RequestMethod.GET)
	public String setTaskStatus(@RequestParam int taskId, Model model, HttpSession session) {
		System.out.println("here in setTaskStatus");
		Task task = taskService.getTaskById(taskId);
		model.addAttribute("task", task);
		return "taskStatusUpdate";
	}

	/**
	 * Sets the task details for updating.
	 *
	 * @param taskUpdateFormModel the TaskUpdateFormModel object containing the updated task details
	 * @param model               the Model object
	 * @return the redirect URL to the activity page
	 */
	@RequestMapping(value = "/setTaskDetails", method = RequestMethod.GET)
	public String setTaskUpdateFormModel(@Validated TaskUpdateFormModel taskUpdateFormModel, Model model) {
		System.out.println("here in setTaskStatus");
		taskService.updateTaskStatus(taskUpdateFormModel);
		return "redirect:activity";
	}
}
