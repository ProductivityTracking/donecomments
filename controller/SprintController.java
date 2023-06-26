package com.pennant.prodmtr.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pennant.prodmtr.model.Dto.FunctionalUnitdto;
import com.pennant.prodmtr.model.Dto.ModuleDTO;
import com.pennant.prodmtr.model.Dto.ProjectDto;
import com.pennant.prodmtr.model.Dto.TaskDto;
import com.pennant.prodmtr.model.Entity.FunctionalUnit;
import com.pennant.prodmtr.model.Entity.Sprint;
import com.pennant.prodmtr.model.Entity.SprintResource;
import com.pennant.prodmtr.model.Entity.SprintTasks;
import com.pennant.prodmtr.model.Entity.Task;
import com.pennant.prodmtr.model.Entity.User;
import com.pennant.prodmtr.model.Input.SprintInput;
import com.pennant.prodmtr.model.Input.SprintResourceInput;
import com.pennant.prodmtr.model.Input.SprintTasksInput;
import com.pennant.prodmtr.model.Input.TaskInput;
import com.pennant.prodmtr.model.view.FunctionalTask;
import com.pennant.prodmtr.service.Interface.ModuleService;
import com.pennant.prodmtr.service.Interface.ProjectService;
import com.pennant.prodmtr.service.Interface.SprintService;
import com.pennant.prodmtr.service.Interface.TaskService;

@Controller
public class SprintController {

	@Autowired
	private SprintService sprintService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private TaskService taskService;
	
	private static int sprintid = 0;

	@Autowired
	public SprintController(SprintService sprintService, ProjectService projectService, ModuleService moduleService,
			TaskService taskService) {
		super();
		this.sprintService = sprintService;
		this.projectService = projectService;
		this.moduleService = moduleService;
		this.taskService = taskService;

	}

	/**
	 * Handles the creation of a new task and sets up the model for rendering the functional units.
	 *
	 * @param sprintInput         the SprintInput object containing the sprint details
	 * @param sprintResourceInput the SprintResourceInput object containing the sprint resource details
	 * @param model               the Model object
	 * @return the view name for displaying the functional units
	 * @throws ParseException if there is an error in parsing the input
	 */
	@RequestMapping(value = "/ShowFunctionalUnits", method = RequestMethod.POST)
	public String createTask(@Validated SprintInput sprintInput,
			@ModelAttribute SprintResourceInput sprintResourceInput, Model model) throws ParseException {
		Sprint sprint = sprintService.storeSprint(sprintInput.toEntity());
		SprintResource sprintResource = sprintResourceInput.toEntity();
		sprintid = sprint.getSprintId();
		sprintResource.setSprintId(sprint.getSprintId());
		sprintService.storeSprintResource(sprintResource);
		
		List<FunctionalUnit> functionalUnits = sprintService.getFunctionalUnitsByModId(sprintInput.getModuleId());
		List<FunctionalUnitdto> functionalUnitDtos = new ArrayList<>();

		for (FunctionalUnit functionalUnit : functionalUnits) {
			FunctionalUnitdto functionalUnitDto = FunctionalUnitdto.fromEntity(functionalUnit);
			functionalUnitDtos.add(functionalUnitDto);
		}

		model.addAttribute("funlist", functionalUnitDtos);
		model.addAttribute("pro_id", sprintInput.getProjectId());
		return "ShowFunctionalUnits";
	}

	/**
	 * Retrieves and displays the functional units based on the selected module and project.
	 *
	 * @param modid   the module ID
	 * @param prodid  the project ID
	 * @param model   the Model object
	 * @return the view name for displaying the functional units
	 * @throws ParseException if there is an error in parsing the input
	 */
	@RequestMapping(value = "/ShowFunUnits", method = RequestMethod.POST)
	public String showFunctionalUnits(@RequestParam("modid") int modid, @RequestParam("prodid") int prodid, Model model)
			throws ParseException {
		List<FunctionalUnit> functionalUnits = sprintService.getFunctionalUnitsByModId(modid);
		List<FunctionalUnitdto> functionalUnitDtos = new ArrayList<>();

		for (FunctionalUnit functionalUnit : functionalUnits) {
			FunctionalUnitdto functionalUnitDto = FunctionalUnitdto.fromEntity(functionalUnit);
			functionalUnitDtos.add(functionalUnitDto);
		}
		model.addAttribute("funlist", functionalUnitDtos);
		model.addAttribute("pro_id", prodid);
		return "ShowFunctionalUnits";
	}

	/**
	 * Displays the sprint home page.
	 *
	 * @param model the Model object
	 * @return the view name for the sprint home page
	 */
	@RequestMapping(value = "/sprint", method = RequestMethod.GET)
	public String sprint(Model model) {
		List<Sprint> allSprints = sprintService.getAllSprints();
		model.addAttribute("allSprints", allSprints);
		return "sprint_home";
	}

	/**
	 * Retrieves and displays the details of a sprint.
	 *
	 * @param model    the Model object
	 * @param sprintId the ID of the sprint
	 * @return the view name for displaying the sprint details
	 */
	@RequestMapping(value = "/sprint_details", method = RequestMethod.GET)
	public String getSprintDetails(Model model, @RequestParam int sprintId) {
		Sprint sprint = sprintService.getSprintDetails(sprintId);
		model.addAttribute("sprint", sprint);
		Sprint s = new Sprint();
		s.setSprintId(sprintId);
		List<SprintTasks> tasksByIdSprints = sprintService.getAllTasksBySprintId(s);
		model.addAttribute("tasksByIdSprints", tasksByIdSprints);
		return "sprint_details";
	}

	/**
	 * Displays the form for adding a new sprint.
	 *
	 * @param model the Model object
	 * @return the view name for adding a new sprint
	 */
	@RequestMapping(value = "/add_sprint", method = RequestMethod.GET)
	public String addSprint(Model model) {
		List<ProjectDto> projects = projectService.getAllProjects();
		model.addAttribute("projects", projects);
		List<User> users = sprintService.getAllUsers();
		model.addAttribute("users", users);

		return "add_sprint";
	}

	/**
	 * Displays the functional unit page.
	 *
	 * @return the view name for the functional unit page
	 */
	@RequestMapping(value = "/FunctionalUnit", method = RequestMethod.GET)
	public String addSprint() {
		return "FunctionalUnit";
	}

	/**
	 * Displays the subtask details page.
	 *
	 * @return the view name for the subtask details page
	 */
	@RequestMapping(value = "/SubTaskdetails", method = RequestMethod.GET)
	public String SubtaskDetails() {
		return "SubtaskDetails";
	}

	/**
	 * Displays the create subtask page.
	 *
	 * @return the view name for the create subtask page
	 */
	@RequestMapping(value = "/CreateSubTask", method = RequestMethod.GET)
	public String CreateSubtask() {
		return "CreateSubtask";
	}

	/**
	 * Displays the backlog page.
	 *
	 * @param model the Model object
	 * @return the view name for the backlog page
	 */
	@RequestMapping(value = "/backlogs", method = RequestMethod.GET)
	public String pastdue(Model model) {
		ArrayList<Sprint> sprintList = (ArrayList<Sprint>) sprintService.getBacklogs();
		model.addAttribute("sprintList", sprintList);
		return "backlog";
	}

	/**
	 * Retrieves and displays the tasks of a backlog.
	 *
	 * @param model       the Model object
	 * @param sprnModlId  the sprint module ID
	 * @param sprnId      the sprint ID
	 * @return the view name for displaying the backlog tasks
	 */
	@RequestMapping(value = "/BacklogTasks", method = RequestMethod.GET)
	public String getBacklogTasks(Model model, @RequestParam("sprnModlId") int sprnModlId,
			@RequestParam("sprnId") int sprnId) {

		Sprint sprint = sprintService.getSprintDetails(sprnId);
		List<Task> taskList = sprintService.getTasks(sprnModlId);
		model.addAttribute("sprint", sprint);
		model.addAttribute("taskList", taskList);
		return "BacklogTasks";
	}

	/**
	 * Retrieves the module details by project ID and returns them as JSON.
	 *
	 * @param projectId the project ID
	 * @return the JSON representation of module details
	 */
	@ResponseBody
	@RequestMapping(value = "/getModuleById", method = RequestMethod.POST, produces = "application/json")
	public String getModuleById(@RequestParam("projectId") int projectId) {
		List<ModuleDTO> moduleList = sprintService.getSprintModulesByProjectId(projectId);
		Gson gson = new Gson();
		String json = gson.toJson(moduleList);
		return json;
	}

	/**
	 * Displays the create task page.
	 *
	 * @param ft    the FunctionalTask object
	 * @param model the Model object
	 * @return the view name for the create task page
	 */
	@RequestMapping(value = "/Task", method = RequestMethod.POST)
	public String createTask(@ModelAttribute FunctionalTask ft, Model model) {
		model.addAttribute("funtask", ft);
		List<User> users = sprintService.getAllUsers();
		model.addAttribute("users", users);
		List<TaskDto> tasks = taskService.getAllTasks();
		model.addAttribute("tasks", tasks);
		return "Task";
	}

	/**
	 * Handles the submission of the create task form.
	 *
	 * @param taskInput         the TaskInput object
	 * @param sprintTasksInput  the SprintTasksInput object
	 * @param model             the Model object
	 * @return the view name for the task added page
	 */
	@RequestMapping(value = "/TaskAdded", method = RequestMethod.POST)
	public String TaskAdded(@ModelAttribute TaskInput taskInput, @ModelAttribute SprintTasksInput sprintTasksInput,
			Model model) {
		Task task = sprintService.storeTask(taskInput.toEntity());
		sprintTasksInput.setSprintId(sprintid);
		sprintTasksInput.setTaskId(task.getTaskId());
		sprintTasksInput.setUserId(task.getTaskSupervisor().getUserId());
		SprintTasks sprintTasks = sprintTasksInput.toEntity();
		sprintService.storeSprintTasks(sprintTasks);
		return "TaskAdded";
	}

}
