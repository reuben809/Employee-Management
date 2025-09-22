package com.example.Employee_management.mapper;

import com.example.Employee_management.dto.DepartmentRequest;
import com.example.Employee_management.dto.DepartmentResponse;
import com.example.Employee_management.dto.ManagerSummary;
import com.example.Employee_management.entity.Department;

public class DepartmentMapper {

    /**
     * Converts a DepartmentRequest DTO to a Department entity.
     * The manager object must be set in the service layer.
     */
    public static Department toEntity(DepartmentRequest request) {
        if (request == null) {
            return null;
        }
        Department department = new Department();
        department.setName(request.getName());
        return department;
    }

    /**
     * Converts a Department entity to a DepartmentResponse DTO.
     */
    public static DepartmentResponse toResponse(Department department) {
        if (department == null) {
            return null;
        }
        DepartmentResponse response = new DepartmentResponse();
        response.setId(department.getId());
        response.setName(department.getName());

        if (department.getManager() != null) {
            ManagerSummary managerSummary = new ManagerSummary();
            managerSummary.setId(department.getManager().getId());
            managerSummary.setFirstName(department.getManager().getFirstName());
            managerSummary.setLastName(department.getManager().getLastName());
            response.setManager(managerSummary);
        }

        return response;
    }
}