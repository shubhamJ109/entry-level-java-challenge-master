package com.challenge.api.service;

import com.challenge.api.dto.CreateEmployeeRequest;
import com.challenge.api.model.Employee;
import java.util.List;
import java.util.UUID;

/**
 * Service interface for Employee operations.
 */
public interface EmployeeService {

    /**
     * @return all employees
     */
    List<Employee> getAllEmployees();

    /**
     * @param uuid the employee's unique identifier
     * @return the matching employee
     */
    Employee getEmployeeByUuid(UUID uuid);

    /**
     * @param request the employee data to create
     * @return the newly created employee
     */
    Employee createEmployee(CreateEmployeeRequest request);
}
