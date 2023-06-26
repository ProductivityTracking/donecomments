package com.pennant.prodmtr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pennant.prodmtr.model.Dto.FunctionalUnitdto;
import com.pennant.prodmtr.model.Input.FunctionalUnitinput;
import com.pennant.prodmtr.service.Interface.FunctionalUnitService;

@Controller
public class FunctionalUnitController {
	
	private final FunctionalUnitService funitService;

	@Autowired
	public FunctionalUnitController(FunctionalUnitService funitService) {
		this.funitService = funitService;
	}

	/**
	 * Retrieves the details of functional units by the provided module ID and adds them to the model.
	 *
	 * @param modId the ID of the module
	 * @param model the model object to add the functional unit details
	 * @return the view name for displaying the functional units
	 */
	@RequestMapping(value = "/funitsbymodlId", method = RequestMethod.GET)
	public String getModuleDetailsByProjId(@RequestParam("modId") Integer modId, Model model) {
		System.out.println("Functional unit jsp called");
		System.out.println("funitid " + modId);
		List<FunctionalUnitdto> funits = funitService.getFunctionalunitByModId(modId);
		System.out.println("funity data" + funits);
		model.addAttribute("funitdto", funits);
		return "funitsbymodlId";
	}

	/**
	 * Displays the form for creating a new functional unit.
	 *
	 * @return the view name for the create functional unit form
	 */
	@RequestMapping(value = "/createfunit", method = RequestMethod.GET)
	public String createnewFunit() {
		return "createfunit";
	}

	/**
	 * Handles the submission of the create functional unit form.
	 *
	 * @param Funitinput the input object containing the data for the new functional unit
	 * @param model the model object
	 * @return a redirect to the functional units view for the associated module
	 */
	@RequestMapping(value = "/createFunitsuccess", method = RequestMethod.POST)
	public String Createmodulesuccess(@Validated FunctionalUnitinput Funitinput, Model model) {
		System.out.println("createModule jsp called");
		System.out.println(Funitinput);
		funitService.createFunit(Funitinput);
		Integer modId = Funitinput.getModlId();
		return "redirect:/funitsbymodlId?modId=" + modId;
	}
}
