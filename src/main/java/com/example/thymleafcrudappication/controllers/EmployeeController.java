package com.example.thymleafcrudappication.controllers;

import com.example.thymleafcrudappication.models.Employee;
import com.example.thymleafcrudappication.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String viewHomePage(Model model)
    {
         return findPaginated(1,model,"firstName","asc");
    }

    @GetMapping("/ShowNewEmployeeForm")
    public String ShowNewEmployeeForm(Model model)
    {
        //create a model to bind form data
        Employee employee = new Employee();
        model.addAttribute("employee",employee);
        return "new_employee";

    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee)
    {
       //save employee to database
        employeeService.addEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public  String showFormForUpdate(@PathVariable (value = "id") long id, Model model)
    {
       //get employee from the service
       Employee employee = employeeService.getEmployeeById(id);
       model.addAttribute("employee",employee);
       return "update_employee";

    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable (value = "id") long id)
    {
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                Model model,
                                @RequestParam ("sortField") String sortField,
                                @RequestParam ("sortDirection") String sortDirection
                                )
    {
        int pageSize =5;

        Page<Employee> page = employeeService.findPaginated(pageNo,pageSize,sortField,sortDirection);
        List<Employee> listemployees = page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());

        //SORT CODE
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDirection",sortDirection);
        model.addAttribute("reverseSortDir",sortDirection.equals("asc") ? "desc" : "asc");

        model.addAttribute("listEmployees",listemployees);
        return "index";

    }

}
