package com.example.Employee_management.service;

import com.example.Employee_management.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeRequest request);
    EmployeeResponse getEmployeeById(UUID id);
    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse updateEmployee(UUID id, EmployeeRequest request);
    void deleteEmployee(UUID id);

    EmployeeResponse updateEmployeePersonalInfo(UUID employeeId, EmployeePersonalInfoRequest request);

    @Transactional(readOnly = true)
    List<EmployeeResponse> searchEmployees(EmployeeSearchRequest request);
}