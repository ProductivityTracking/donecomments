package com.pennant.prodmtr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pennant.prodmtr.model.Input.SubtaskInput;
import com.pennant.prodmtr.service.Interface.SubtaskService;
import com.pennant.prodmtr.service.Interface.TaskService;

@Controller
public class SubtaskController {

	@Autowired
	public SubtaskService subtaskService;
	private TaskService taskService;

	/**
	 * Saves a new subtask.
	 *
	 * @param subtaskInput the SubtaskInput object containing subtask details
	 * @param model        the Model object
	 * @return the view name for the task list page
	 */
	@RequestMapping(value = "/saveSubtask", method = RequestMethod.GET)
	public String saveSubtask(@Validated SubtaskInput subtaskInput, Model model) {
		try {
			// Save the subtask
			subtaskService.setNewSubTask(subtaskInput);
			return "Taskslist";
		} catch (DataIntegrityViolationException ex) {
			// Handle the constraint violation exception
			ex.printStackTrace(); // or log the error
			model.addAttribute("error", "Constraint violation occurred. Please try again.");
			return "Taskslist"; // Show an error page to the user
		}
	}

	/**
	 * Retrieves the subtask form for creating a new subtask.
	 *
	 * @param taskId the ID of the task associated with the subtask
	 * @param model  the Model object
	 * @return the view name for the create subtask form
	 */
	@RequestMapping(value = "/createSubtask", method = RequestMethod.GET)
	public String getSubtaskForm(@RequestParam("taskId") int taskId, Model model) {
		SubtaskInput subtaskInput = new SubtaskInput();
		subtaskInput.setTaskId(taskId); // Set the task_id in the SubtaskInput object
		model.addAttribute("subtaskInput", subtaskInput);
		model.addAttribute("taskId", taskId);
		return "createsubtask";
	}
}
