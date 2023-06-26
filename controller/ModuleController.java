package com.pennant.prodmtr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pennant.prodmtr.model.Dto.ModuleDTO;
import com.pennant.prodmtr.model.Input.ModuleInput;
import com.pennant.prodmtr.service.Interface.FunctionalUnitService;
import com.pennant.prodmtr.service.Interface.ModuleService;

@Controller
public class ModuleController {

	private final ModuleService moduleService;
	private final FunctionalUnitService funitService;

	@Autowired
	public ModuleController(ModuleService moduleService, FunctionalUnitService funitService) {
		this.moduleService = moduleService;
		this.funitService = funitService;
	}

	/**
	 * Displays the module page.
	 *
	 * @param m the Model object
	 * @return the view name for the module page
	 */
	@RequestMapping(value = "/module", method = RequestMethod.GET)
	public String getModule(Model m) {
		System.out.println("modules page returnss");
		return "module";
	}

	/**
	 * Displays the form for creating a new module.
	 *
	 * @param model the Model object
	 * @return the view name for the create module form
	 */
	@RequestMapping(value = "/createModule", method = RequestMethod.GET)
	public String createModule(Model model) {
		return "Addmodule";
	}

	/**
	 * Handles the submission of the create module form.
	 *
	 * @param moduleinput the input object containing the data for the new module
	 * @param model the Model object
	 * @return a redirect to the module details view for the associated project
	 */
	@RequestMapping(value = "/createModulesuccess", method = RequestMethod.POST)
	public String Createmodulesuccess(@Validated ModuleInput moduleinput, Model model) {
		System.out.println("createModule jsp called");
		System.out.println(moduleinput);
		moduleService.createModule(moduleinput);
		Integer projectId = moduleinput.getModule_proj_id();
		return "redirect:/moduleDetailsByProjId?projectId=" + projectId;
	}

	/**
	 * Retrieves the details of modules by the provided project ID and adds them to the model.
	 *
	 * @param projectId the ID of the project
	 * @param model the Model object
	 * @return the view name for displaying the module details
	 */
	@RequestMapping(value = "/moduleDetailsByProjId", method = RequestMethod.GET)
	public String getModuleDetailsByProjId(@RequestParam("projectId") Integer projectId, Model model) {
		System.out.println("moduleDetailsByProjId jsp called");
		System.out.println("projid " + projectId);
		List<ModuleDTO> modules = moduleService.getModuleByProjId(projectId);
		System.out.println("module data " + modules);
		model.addAttribute("moduleDTO", modules);
		return "moduleDetailsbyProjId";
	}
}
