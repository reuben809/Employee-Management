package com.example.Employee_management.service;

import com.example.Employee_management.dto.EmployeePersonalInfoRequest;
import com.example.Employee_management.dto.EmployeeRequest;
import com.example.Employee_management.dto.EmployeeResponse;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeRequest request);
    EmployeeResponse getEmployeeById(UUID id);
    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse updateEmployee(UUID id, EmployeeRequest request);
    void deleteEmployee(UUID id);

    EmployeeResponse updateEmployeePersonalInfo(UUID employeeId, EmployeePersonalInfoRequest request);
}