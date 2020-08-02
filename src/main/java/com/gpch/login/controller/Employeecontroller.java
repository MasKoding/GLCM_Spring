package com.gpch.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gpch.login.model.EmployeeModel;
import com.gpch.login.service.EmployeeService;

@Controller
@RequestMapping()
public class Employeecontroller {
	@Autowired
	private EmployeeService employeeService;
	@RequestMapping(value = "/employee",method = RequestMethod.GET)
	public ModelAndView index() {
		 
		Map<String,Object> list =(Map<String,Object>) employeeService.getAll();
		ModelAndView mv = new ModelAndView();
		mv.addObject("list",list.get("data"));
		mv.setViewName("admin/employee");
		return mv;
	}
	@RequestMapping(value = "employee/add",method = RequestMethod.GET)
	public ModelAndView formAdd() {
		 
		ModelAndView mv = new ModelAndView();
		mv.addObject("list",new EmployeeModel());
		mv.setViewName("admin/form");
		return mv;
	}
	@RequestMapping(value = "employee/save",method = RequestMethod.POST)
	public String save(EmployeeModel employee,RedirectAttributes ra) {
		 Object save = null;
		if(employee.getId() !=null) {
			save =employeeService.edit(employee);
		}else {
			save =employeeService.save(employee);
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("employee",new EmployeeModel());
		ra.addFlashAttribute("successFlash","success for save data");
		return "redirect:/employee";
	}
	@RequestMapping(value = "/employee/edit/{id}",method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable long id) {
		 
		Map<String,Object> list =(Map<String,Object>) employeeService.findId(id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("list",list.get("data"));
		mv.setViewName("admin/form");
		return mv;
	}
	
	@RequestMapping(value = "/employee/delete/{id}",method = RequestMethod.GET)
	public String delete(@PathVariable long id,RedirectAttributes ra) {
		employeeService.delete(id);
		ra.addFlashAttribute("successFlash","Success delete data..");
		return "redirect:/employee";
	}
	
	
}
