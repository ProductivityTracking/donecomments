package com.pennant.prodmtr.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pennant.prodmtr.Dao.Interface.TaskDao;
import com.pennant.prodmtr.model.Dto.ResTaskFilter;
import com.pennant.prodmtr.model.Dto.TFilterCriteria;
import com.pennant.prodmtr.model.Dto.TaskDto;
import com.pennant.prodmtr.model.Entity.Task;
import com.pennant.prodmtr.model.view.TaskStatusRequest;
import com.pennant.prodmtr.model.view.TaskUpdateFormModel;
import com.pennant.prodmtr.service.Interface.TaskService;

@Component
@Transactional
@Service
public class TaskServiceImpl implements TaskService {
	private final TaskDao taskDao;

	@Autowired
	public TaskServiceImpl(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	/**
	 * Filters tasks based on the provided resource task filter criteria.
	 *
	 * @param resTaskFilter the ResTaskFilter object containing the filter criteria
	 * @return a list of TaskDto objects matching the filter criteria
	 */
	@Override
	public List<TaskDto> filterTasks(ResTaskFilter resTaskFilter) {
		return taskDao.filterTasks(resTaskFilter);
	}

	/**
	 * Retrieves tasks assigned to a specific user.
	 *
	 * @param userId the ID of the user
	 * @return a list of TaskDto objects assigned to the user
	 */
	@Override
	public List<TaskDto> getTasksByUserId(int userId) {
		return taskDao.getTasksByUserId(userId);
	}

	/**
	 * Retrieves all tasks.
	 *
	 * @return a list of all TaskDto objects
	 */
	@Override
	public List<TaskDto> getAllTasks() {
		return taskDao.getAllTasks();
	}

	/**
	 * Retrieves a task by its ID.
	 *
	 * @param taskId the ID of the task
	 * @return the Task object matching the ID
	 */
	@Override
	public Task getTaskById(int taskId) {
		return taskDao.getTaskById(taskId);
	}

	/**
	 * Saves a task.
	 *
	 * @param task the Task object to be saved
	 */
	@Override
	public void saveTask(Task task) {
		taskDao.saveTask(task);
	}

	/**
	 * Updates the status of a task.
	 *
	 * @param taskId the ID of the task
	 * @return true if the status update was successful, false otherwise
	 */
	@Override
	public Boolean updateStatus(int taskId) {
		return taskDao.updateStatus(taskId);
	}

	/**
	 * Filters tasks based on the provided task filter criteria.
	 *
	 * @param filterCriteria the TFilterCriteria object containing the filter criteria
	 * @return a list of TaskDto objects matching the filter criteria
	 */
	@Override
	public List<TaskDto> PtfilterTasks(TFilterCriteria filterCriteria) {
		// TODO: Implement this method
		return null;
	}

	/**
	 * Retrieves tasks by project ID.
	 *
	 * @param projId the ID of the project
	 * @return a list of Task objects associated with the project ID
	 */
	@Override
	public List<Task> getTasksByProjectId(Integer projId) {
		return taskDao.getTasksByProjectId(projId);
	}

	/**
	 * Retrieves completed tasks by project ID.
	 *
	 * @param projId the ID of the project
	 * @return a list of completed Task objects associated with the project ID
	 */
	@Override
	public List<Task> getCompTasksByProjectId(Integer projId) {
		return taskDao.getCompTasksByProjectId(projId);
	}

	/**
	 * Retrieves the number of completed tasks for a specific user.
	 *
	 * @param userId the ID of the user
	 * @return the number of completed tasks
	 */
	@Override
	public int getCompletedTasksByUserId(int userId) {
		return taskDao.getCompletedTasksByUserId(userId);
	}

	/**
	 * Retrieves the total number of tasks for a specific user.
	 *
	 * @param userId the ID of the user
	 * @return the total number of tasks
	 */
	@Override
	public int getTotalTasksByUserId(int userId) {
		List<TaskDto> tasks = taskDao.getTasksByUserId(userId);
		return tasks.size();
	}

	/**
	 * Retrieves the total hours worked by a user.
	 *
	 * @param userId the ID of the user
	 * @return the total hours worked
	 */
	@Override
	public double getHoursWorkedByUserId(int userId) {
		// Implement the logic to retrieve the hours worked by the user
		// You can use the appropriate data access method or service call here
		// and return the number of hours worked
		return taskDao.getHoursWorkedByUserId(userId);
	}

	/**
	 * Calculates the performance score based on the completed tasks and total tasks.
	 *
	 * @param completedTasks the number of completed tasks
	 * @param totalTasks     the total number of tasks
	 * @return the performance score
	 */
	public double calculatePerformanceScore(int completedTasks, int totalTasks) {
		if (totalTasks == 0) {
			return 0.0; // Avoid division by zero
		}
		return (double) completedTasks / totalTasks * 100.0;
	}

	/**
	 * Sets the status of a task based on the TaskStatusRequest object.
	 *
	 * @param request the TaskStatusRequest object containing the task ID, remarks, and status
	 */
	@Override
	public void setTaskStatus(TaskStatusRequest request) {
		System.out.println("in setTaskStatus");
		taskDao.updateEntity(request.getTaskId(), request.getRemarks(), request.getStatus());
		System.out.println("updated task successfully");
	}

	/**
	 * Updates the status of a task based on the TaskUpdateFormModel object.
	 *
	 * @param taskUpdateFormModel the TaskUpdateFormModel object containing the task ID, remarks, and status
	 */
	@Override
	public void updateTaskStatus(TaskUpdateFormModel taskUpdateFormModel) {
		taskDao.setTaskStatus(taskUpdateFormModel.getTaskId(), taskUpdateFormModel.getRemarks(), taskUpdateFormModel.getStatus());
	}
}
