package com.pennant.prodmtr.service.Impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pennant.prodmtr.Dao.Impl.FunctionalunitDaoImpl;
import com.pennant.prodmtr.model.Dto.FunctionalUnitdto;
import com.pennant.prodmtr.model.Entity.FunctionalUnit;
import com.pennant.prodmtr.model.Input.FunctionalUnitinput;
import com.pennant.prodmtr.service.Interface.FunctionalUnitService;

@Service
@Transactional
public class FunctionalUnitServiceImpl implements FunctionalUnitService {

	private final FunctionalunitDaoImpl funitDao;

	@Autowired
	public FunctionalUnitServiceImpl(FunctionalunitDaoImpl funitDAO) {
		this.funitDao = funitDAO;
	}

	/**
	 * Retrieves a list of FunctionalUnitdto objects associated with the provided module ID.
	 *
	 * @param modId the ID of the module
	 * @return a list of FunctionalUnitdto objects
	 */
	public List<FunctionalUnitdto> getFunctionalunitByModId(Integer modId) {
		return funitDao.getFunctionalunitByModId(modId);
	}

	/**
	 * Creates a new functional unit using the information provided in the FunctionalUnitinput object.
	 *
	 * @param funitinput the input object containing functional unit information
	 */
	public void createFunit(FunctionalUnitinput funitinput) {
		// Additional business logic, if needed

		FunctionalUnit funit = funitinput.toEntity();
		funitDao.save(funit);
	}
}
