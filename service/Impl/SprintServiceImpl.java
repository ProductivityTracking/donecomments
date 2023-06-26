package com.pennant.prodmtr.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pennant.prodmtr.Dao.Interface.SprintDao;
import com.pennant.prodmtr.model.Dto.ModuleDTO;
import com.pennant.prodmtr.model.Entity.FunctionalUnit;
import com.pennant.prodmtr.model.Entity.Sprint;
import com.pennant.prodmtr.model.Entity.SprintResource;
import com.pennant.prodmtr.model.Entity.SprintTasks;
import com.pennant.prodmtr.model.Entity.Task;
import com.pennant.prodmtr.model.Entity.User;
import com.pennant.prodmtr.service.Interface.SprintService;

@Component
@Transactional
@Service
public class SprintServiceImpl implements SprintService {
	private final SprintDao sprintDao;

	@Autowired
	public SprintServiceImpl(SprintDao sprintDao) {
		this.sprintDao = sprintDao;
	}

	/**
	 * Retrieves a list of backlogs.
	 *
	 * @return a list of Sprint objects representing the backlogs
	 */
	@Override
	public List<Sprint> getBacklogs() {
		return sprintDao.getBaskLogs();
	}

	/**
	 * Retrieves the details of a specific sprint.
	 *
	 * @param sprintId the ID of the sprint
	 * @return a Sprint object representing the sprint details
	 */
	@Override
	public Sprint getSprintDetails(int sprintId) {
		return sprintDao.getSprintDetails(sprintId);
	}

	/**
	 * Retrieves a list of tasks for a specific module.
	 *
	 * @param modlId the ID of the module
	 * @return a list of Task objects representing the tasks
	 */
	@Override
	public List<Task> getTasks(int modlId) {
		return sprintDao.getTasks(modlId);
	}

	/**
	 * Retrieves a list of all sprints.
	 *
	 * @return a list of Sprint objects representing all sprints
	 */
	@Override
	public List<Sprint> getAllSprints() {
		return sprintDao.getAllSprints();
	}

	/**
	 * Retrieves a list of all tasks for a specific sprint.
	 *
	 * @param sprintId the ID of the sprint
	 * @return a list of SprintTasks objects representing the tasks
	 */
	@Override
	public List<SprintTasks> getAllTasksBySprintId(Sprint sprintId) {
		return sprintDao.getAllTasksBySprintId(sprintId);
	}

	/**
	 * Stores a new sprint.
	 *
	 * @param sprint the Sprint object to be stored
	 * @return the stored Sprint object
	 */
	@Override
	public Sprint storeSprint(Sprint sprint) {
		return sprintDao.storeSprint(sprint);
	}

	/**
	 * Retrieves a list of modules for a specific project in a sprint.
	 *
	 * @param projectId the ID of the project
	 * @return a list of ModuleDTO objects representing the modules
	 */
	@Override
	public List<ModuleDTO> getSprintModulesByProjectId(int projectId) {
		return sprintDao.getSprintModulesByProjectId(projectId);
	}

	/**
	 * Retrieves a list of functional units for a specific module in a sprint.
	 *
	 * @param modlId the ID of the module
	 * @return a list of FunctionalUnit objects representing the functional units
	 */
	@Override
	public List<FunctionalUnit> getFunctionalUnitsByModId(int modlId) {
		return sprintDao.getFunctionalUnitsByModId(modlId);
	}

	/**
	 * Stores a new sprint resource.
	 *
	 * @param src the SprintResource object to be stored
	 */
	public void storeSprintResource(SprintResource src) {
		sprintDao.storeSprintResource(src);
	}

	/**
	 * Stores a new sprint task.
	 *
	 * @param sprintTask the SprintTasks object to be stored
	 */
	public void storeSprintTasks(SprintTasks sprintTask) {
		System.out.println(sprintTask);
		sprintDao.storeSprintTasks(sprintTask);
	}

	/**
	 * Stores a new task.
	 *
	 * @param task the Task object to be stored
	 * @return the stored Task object
	 */
	@Override
	public Task storeTask(Task task) {
		return null;
	}

	/**
	 * Retrieves a list of all users.
	 *
	 * @return a list of User objects representing all users
	 */
	@Override
	public List<User> getAllUsers() {
		return null;
	}

	/**
	 * Retrieves a list of sprints for a specific project.
	 *
	 * @param projId the ID of the project
	 * @return a list of Sprint objects representing the sprints
	 */
	@Override
	public List<Sprint> getSprintsByProjId(int projId) {
		return sprintDao.getSprintByProjId(projId);
	}
}
