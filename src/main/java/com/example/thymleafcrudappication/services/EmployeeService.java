package com.example.thymleafcrudappication.services;

import com.example.thymleafcrudappication.models.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();
    void addEmployee(Employee employee);
    Employee getEmployeeById(long id);
    void deleteEmployee(long id);
    Page<Employee> findPaginated(int pageNo, int pageSize,String sortField, String sortDirection);
}
