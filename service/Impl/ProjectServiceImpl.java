package com.pennant.prodmtr.service.Impl;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pennant.prodmtr.Dao.Interface.ProjectDao;
import com.pennant.prodmtr.model.Dto.ProjectDto;
import com.pennant.prodmtr.model.Dto.ProjectFilter;
import com.pennant.prodmtr.model.Entity.Project;
import com.pennant.prodmtr.model.Input.ProjectInput;
import com.pennant.prodmtr.service.Interface.ProjectService;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

	private final ProjectDao projectDao;

	@Autowired
	public ProjectServiceImpl(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	/**
	 * Retrieves a list of all ProjectDto objects.
	 *
	 * @return a list of ProjectDto objects
	 */
	public List<ProjectDto> getAllProjects() {
		return projectDao.getAllProjects();
	}

	/**
	 * Retrieves a filtered list of ProjectDto objects based on the provided filter criteria.
	 *
	 * @param projectFilter the filter criteria for projects
	 * @return a list of filtered ProjectDto objects
	 */
	public List<ProjectDto> filterProjects(ProjectFilter projectFilter) {
		// TODO Auto-generated method stub
		return projectDao.getFilterProjects(projectFilter);
	}

	/**
	 * Retrieves the ProjectDto object associated with the provided project ID.
	 *
	 * @param projId the ID of the project
	 * @return the ProjectDto object
	 */
	public ProjectDto getProjectById(Integer projId) {
		return projectDao.getProjectById(projId);
	}

	/**
	 * Creates a new project using the information provided in the ProjectInput object.
	 * Sets the project start date to the current date and the project status to "O".
	 *
	 * @param projectInput the input object containing project information
	 */
	public void setNewProject(ProjectInput projectInput) {
		Date date = new Date(System.currentTimeMillis());
		projectInput.setProjectStartDate(date);
		projectInput.setProjectStatus("O");
		Project project = projectInput.toEntity();
		projectDao.setNewProject(project);
	}

	// Add methods for creating, updating, and deleting projects if needed
}
