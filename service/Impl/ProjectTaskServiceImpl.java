package com.pennant.prodmtr.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pennant.prodmtr.Dao.Interface.ProjectTaskdao;
import com.pennant.prodmtr.model.Dto.PTFilterCriteria;
import com.pennant.prodmtr.model.Dto.ProjectDto;
import com.pennant.prodmtr.model.Dto.ProjectTaskDTO;
import com.pennant.prodmtr.model.Entity.ProjectTask;
import com.pennant.prodmtr.model.Entity.Task;
import com.pennant.prodmtr.service.Interface.ProjectTaskService;
import com.pennant.prodmtr.service.Interface.TaskService;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {

	@Autowired
	private TaskService taskService;

	@Autowired
	private ProjectTaskdao projectTaskdao;

	@Autowired
	private ProjectTaskService projectTaskService;

	/**
	 * Creates a new project task.
	 *
	 * @param projectTask the project task to be created
	 * @return the created project task
	 */
	@Override
	public ProjectTask createProjectTask(ProjectTask projectTask) {
		// Additional business logic, if needed
		return projectTaskdao.save(projectTask);
	}

	/**
	 * Retrieves a list of ProjectTaskDTO objects.
	 *
	 * @return a list of ProjectTaskDTO objects
	 */
	@Override
	public List<ProjectTaskDTO> getProjectTaskDTOList() {
		return projectTaskdao.getProjectTaskDTOList();
	}

	/**
	 * Retrieves the ProjectTaskDTO object associated with the provided task ID.
	 *
	 * @param taskId the ID of the task
	 * @return the ProjectTaskDTO object
	 */
	@Override
	public ProjectTaskDTO getProjectTaskById(int taskId) {
		return projectTaskdao.getProjectTaskById(taskId);
	}

	/**
	 * Retrieves a filtered list of ProjectTaskDTO objects based on the provided filter criteria.
	 *
	 * @param filterCriteria the filter criteria for tasks
	 * @return a list of filtered ProjectTaskDTO objects
	 */
	@Override
	public List<ProjectTaskDTO> filterTasks(PTFilterCriteria filterCriteria) {
		return projectTaskdao.filterTasks(filterCriteria);
	}

	/**
	 * Sets the task-related information to the ProjectDto object.
	 * Updates the total and completed task counts for both individual tasks and project tasks.
	 *
	 * @param projectId  the ID of the project
	 * @param projectDto the ProjectDto object to be updated
	 */
	@Override
	public void setTasksToProjDto(int projectId, ProjectDto projectDto) {
		List<Task> tasks = taskService.getTasksByProjectId(projectId);
		List<Task> compTasks = taskService.getCompTasksByProjectId(projectId);

		List<ProjectTask> projectTasks = projectTaskService.getTasksByProjectId(projectId);
		List<ProjectTask> compProjectTasks = projectTaskService.getCompTasksByProjectId(projectId);

		projectDto.setTotalTasks(tasks.size());
		projectDto.setCompletedTasks(compTasks.size());

		projectDto.setTotalIndvTasks(projectTasks.size());
		projectDto.setCompletedIndvTasks(compProjectTasks.size());
	}

	/**
	 * Retrieves a list of ProjectTask objects associated with the provided project ID.
	 *
	 * @param projId the ID of the project
	 * @return a list of ProjectTask objects
	 */
	@Override
	public List<ProjectTask> getTasksByProjectId(Integer projId) {
		return projectTaskdao.getTasksByProjectId(projId);
	}

	/**
	 * Retrieves a list of completed ProjectTask objects associated with the provided project ID.
	 *
	 * @param projectId the ID of the project
	 * @return a list of completed ProjectTask objects
	 */
	public List<ProjectTask> getCompTasksByProjectId(int projectId) {
		return projectTaskdao.getCompTasksByProjectId(projectId);
	}

	/**
	 * Retrieves the number of completed tasks associated with the provided user ID.
	 *
	 * @param userId the ID of the user
	 * @return the number of completed tasks
	 */
	@Override
	public int getCompletedTasksByUserId(int userId) {
		return projectTaskdao.getCompletedTasksByUserId(userId);
	}

	/**
	 * Retrieves the total number of tasks associated with the provided user ID.
	 *
	 * @param userId the ID of the user
	 * @return the total number of tasks
	 */
	@Override
	public int getTotalTasksByUserId(int userId) {
		int tasks = projectTaskdao.getTotalTasksByUserId(userId);
		return tasks;
	}

	/**
	 * Retrieves the number of hours worked by the user associated with the provided user ID.
	 *
	 * @param userId the ID of the user
	 * @return the number of hours worked
	 */
	@Override
	public double getHoursWorkedByUserId(int userId) {
		// Implement the logic to retrieve the hours worked by the user
		// You can use the appropriate data access method or service call here
		// and return the number of hours worked
		return projectTaskdao.getHoursWorkedByUserId(userId);
	}

	/**
	 * Calculates the performance score based on the completed tasks and total tasks.
	 *
	 * @param completedTasks the number of completed tasks
	 * @param totalTasks     the total number of tasks
	 * @return the performance score as a percentage
	 */
	public double calculatePerformanceScore(int completedTasks, int totalTasks) {
		if (totalTasks == 0) {
			return 0.0; // Avoid division by zero
		}
		return (double) completedTasks / totalTasks * 100.0;
	}

	// Other service methods for updating, deleting project tasks
}
