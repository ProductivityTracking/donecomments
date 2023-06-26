package com.pennant.prodmtr.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pennant.prodmtr.Dao.Interface.ResourceDao;
import com.pennant.prodmtr.model.Dto.ResourceFilter;
import com.pennant.prodmtr.model.Dto.UserDto;
import com.pennant.prodmtr.model.Entity.User;
import com.pennant.prodmtr.model.Input.UserInput;
import com.pennant.prodmtr.service.Interface.ResourceService;

@Component
@Transactional
public class ResourceServiceImpl implements ResourceService {

	private final ResourceDao resourceDAO;

	@Autowired
	public ResourceServiceImpl(ResourceDao resourceDAO) {
		this.resourceDAO = resourceDAO;
	}

	/**
	 * Retrieves a list of all resources.
	 *
	 * @return a list of UserDto objects representing the resources
	 */
	public List<UserDto> getAllResources() {
		return resourceDAO.getAllResources();
	}

	/**
	 * Filters the resources based on the provided filter criteria.
	 *
	 * @param resourceFilter the filter criteria for resources
	 * @return a list of filtered UserDto objects representing the resources
	 */
	public List<UserDto> filterResources(ResourceFilter resourceFilter) {
		Short roleFilter = resourceFilter.getRoleFilter();
		Short projectFilter = resourceFilter.getProjectFilter();
		System.out.println("in service ");
		System.out.println("roleFilter is " + roleFilter);
		System.out.println("projectFilter is " + projectFilter);
		List<UserDto> userDtoList = resourceDAO.filterResources(roleFilter, projectFilter);
		return userDtoList;
	}

	/**
	 * Retrieves the resource with the given display name.
	 *
	 * @param displayName the display name of the resource
	 * @return the User object representing the resource
	 */
	public User getResourceByDisplayName(String displayName) {
		return resourceDAO.getResourceByDisplayName(displayName);
	}

	/**
	 * Adds a new user resource.
	 *
	 * @param userInput the UserInput object containing the user resource data
	 */
	public void addUser(UserInput userInput) {
		User user = userInput.toEntity();
		resourceDAO.addUser(user);
	}

	/**
	 * Saves the updated user resource.
	 *
	 * @param existingResource the UserInput object containing the updated user resource data
	 */
	public void save(UserInput existingResource) {

		existingResource.setUserCreationDate(existingResource.getUserCreationDate());
		existingResource.setUserLastUpdatedDate(new Date());

		User user = existingResource.toEntity();
		resourceDAO.saveUser(user);
	}

	/**
	 * Retrieves a list of all project managers.
	 *
	 * @return a list of User objects representing the project managers
	 */
	public List<User> getAllProjManagers() {
		return resourceDAO.getAllProjManagers();
	}

	/**
	 * Retrieves a list of users associated with the given project ID.
	 *
	 * @param projectId the ID of the project
	 * @return a list of User objects representing the users
	 */
	public List<User> getUsersByProjectId(int projectId) {
		List<User> userList = resourceDAO.getUsersByProjectId(projectId);
		return userList;
	}

	/**
	 * Retrieves the resource with the given user ID.
	 *
	 * @param userId the ID of the user resource
	 * @return the User object representing the resource
	 */
	@Override
	public User getResourceById(int userId) {
		return resourceDAO.getResourceById(userId);
	}
}
