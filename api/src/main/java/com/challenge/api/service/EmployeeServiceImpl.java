package com.challenge.api.service;

import com.challenge.api.dto.CreateEmployeeRequest;
import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeImpl;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * In-memory implementation of {@link EmployeeService}.
 * Pre-seeded with mock employee data for demonstration purposes.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Map<UUID, Employee> employeeStore = new ConcurrentHashMap<>();

    /** Seeds the store with a few mock employees on startup. */
    public EmployeeServiceImpl() {
        Employee emp1 = EmployeeImpl.builder()
                .uuid(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .fullName("John Doe")
                .salary(75000)
                .age(30)
                .jobTitle("Software Engineer")
                .email("john.doe@company.com")
                .contractHireDate(Instant.parse("2023-01-15T09:00:00Z"))
                .contractTerminationDate(null)
                .build();

        Employee emp2 = EmployeeImpl.builder()
                .uuid(UUID.randomUUID())
                .firstName("Jane")
                .lastName("Smith")
                .fullName("Jane Smith")
                .salary(85000)
                .age(28)
                .jobTitle("Product Manager")
                .email("jane.smith@company.com")
                .contractHireDate(Instant.parse("2022-06-01T09:00:00Z"))
                .contractTerminationDate(null)
                .build();

        employeeStore.put(emp1.getUuid(), emp1);
        employeeStore.put(emp2.getUuid(), emp2);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeStore.values());
    }

    @Override
    public Employee getEmployeeByUuid(UUID uuid) {
        Employee employee = employeeStore.get(uuid);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with UUID: " + uuid);
        }
        return employee;
    }

    @Override
    public Employee createEmployee(CreateEmployeeRequest request) {
        Employee employee = EmployeeImpl.builder()
                .uuid(UUID.randomUUID())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .fullName(request.getFirstName() + " " + request.getLastName())
                .salary(request.getSalary())
                .age(request.getAge())
                .jobTitle(request.getJobTitle())
                .email(request.getEmail())
                .contractHireDate(Instant.now())
                .contractTerminationDate(null)
                .build();

        employeeStore.put(employee.getUuid(), employee);
        return employee;
    }
}
