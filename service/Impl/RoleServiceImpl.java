package com.pennant.prodmtr.service.Impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pennant.prodmtr.Dao.Interface.RoleDao;
import com.pennant.prodmtr.model.Entity.Role;
import com.pennant.prodmtr.service.Interface.RoleService;

@Component
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDAO;

	/**
	 * Sets the RoleDao dependency for the RoleService.
	 *
	 * @param roleDAO the RoleDao implementation to be set
	 */
	public void setRoleDAO(RoleDao roleDAO) {
		this.roleDAO = roleDAO;
	}

	/**
	 * Retrieves a list of all roles.
	 *
	 * @return a list of Role objects representing the roles
	 */
	@Transactional
	public List<Role> getAllRoles() {
		return roleDAO.getAllRoles();
	}
}
