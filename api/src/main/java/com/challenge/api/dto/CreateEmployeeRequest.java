package com.challenge.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request body for creating a new Employee.
 * Contains only the fields a client should provide; server-generated fields
 * (uuid, fullName, contractHireDate) are excluded.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeRequest {

    private String firstName;
    private String lastName;
    private Integer salary;
    private Integer age;
    private String jobTitle;
    private String email;
}
