package com.example.Employee_management.service;

import com.example.Employee_management.dto.DepartmentRequest;
import com.example.Employee_management.dto.DepartmentResponse;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {
    DepartmentResponse getDepartmentById(UUID id);

    DepartmentResponse createDepartment(DepartmentRequest request);

    DepartmentResponse assignManagerToDepartment(UUID departmentId, UUID managerId);

    // New method for updating a department's details
    DepartmentResponse updateDepartment(UUID departmentId, DepartmentRequest request);

    // New method for deleting a department
    void deleteDepartment(UUID departmentId);
    List<DepartmentResponse> getAllDepartments();
}