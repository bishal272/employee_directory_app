package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.dao.EmployeeRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private final EmployeeRepository employeeRepository;
	

	public EmployeeController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}


	// add mapping for "/list"

	@GetMapping("/list")
	public String listEmployees(Model theModel) {

		List<Employee> theEmployees= employeeRepository.findAllByOrderByLastNameDesc();
		// add to the spring model
		theModel.addAttribute("employees", theEmployees);

		return "employees/list-employees";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		Employee theEmployee = new Employee();
		
		theModel.addAttribute("employee",theEmployee);
		return "employees/employee-form";
	}
	
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {
		employeeRepository.save(theEmployee);
		return "redirect:/employees/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam ("employeeId") int theid, Model theModel) {
		Optional<Employee> theEmployee = employeeRepository.findById(theid);
		Employee temp=null;
		if(theEmployee.isPresent())
			temp=theEmployee.get();
		theModel.addAttribute("employee",temp);
		return "employees/employee-form";

	}
	
	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam ("employeeId") int theid, Model theModel) {
		employeeRepository.deleteById(theid);
		return "redirect:/employees/list";
	}
	

	
}









