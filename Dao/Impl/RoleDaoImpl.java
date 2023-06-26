package com.pennant.prodmtr.Dao.Impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.pennant.prodmtr.Dao.Interface.RoleDao;
import com.pennant.prodmtr.model.Entity.Role;

@Component
public class RoleDaoImpl implements RoleDao {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Retrieves all roles.
	 *
	 * @return a list of Role objects representing the roles
	 */
	public List<Role> getAllRoles() {
		return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
	}

	/**
	 * Finds a Role entity by its ID.
	 *
	 * @param id the ID of the role
	 * @return the Role object if found, null otherwise
	 */
	public Role findById(short id) {
		return entityManager.find(Role.class, id);
	}
}
