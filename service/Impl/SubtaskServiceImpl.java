package com.pennant.prodmtr.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pennant.prodmtr.Dao.Interface.SubtaskDao;
import com.pennant.prodmtr.model.Entity.Subtask;
import com.pennant.prodmtr.model.Input.SubtaskInput;
import com.pennant.prodmtr.service.Interface.SubtaskService;

@Service
@Transactional
public class SubtaskServiceImpl implements SubtaskService {

	@Autowired
	private SubtaskDao subtaskDao;

	/**
	 * Saves a subtask.
	 *
	 * @param subtask the Subtask object to be saved
	 */
	public void saveSubtask(Subtask subtask) {
		subtaskDao.saveSubtask(subtask);
	}

	/**
	 * Sets a new subtask.
	 *
	 * @param subtaskInput the SubtaskInput object representing the new subtask
	 */
	@Override
	public void setNewSubTask(SubtaskInput subtaskInput) {
		Subtask subtask = subtaskInput.toEntity();
		subtaskDao.setNewSubTask(subtask);
	}
}
