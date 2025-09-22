package com.example.Employee_management.mapper;

import com.example.Employee_management.dto.*;
import com.example.Employee_management.entity.Department;
import com.example.Employee_management.entity.Employee;

public class EmployeeMapper {

    /**
     * Converts an EmployeeRequest DTO to an Employee entity.
     * Note: The manager and department objects are not set here. They must be fetched
     * and set in the service layer using the IDs/names from the request.
     */
    public static Employee toEntity(EmployeeRequest request) {
        if (request == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setAddress(request.getAddress());
        employee.setRole(request.getRole());
        employee.setSalary(request.getSalary());

        return employee;
    }

    /**
     * Converts an Employee entity to an EmployeeResponse DTO.
     */
    public static EmployeeResponse toResponse(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setEmail(employee.getEmail());
        response.setAddress(employee.getAddress());
        response.setRole(employee.getRole());
        response.setSalary(employee.getSalary());

        // Handle nested manager object
        if (employee.getManager() != null) {
            ManagerSummary managerSummary = new ManagerSummary();
            managerSummary.setId(employee.getManager().getId());
            managerSummary.setFirstName(employee.getManager().getFirstName());
            managerSummary.setLastName(employee.getManager().getLastName());
            response.setManager(managerSummary);
        }

        // <-- CHANGE: Handle nested department object
        if (employee.getDepartment() != null) {
            DepartmentSummary departmentSummary = new DepartmentSummary();
            departmentSummary.setId(employee.getDepartment().getId());
            departmentSummary.setName(employee.getDepartment().getName());
            response.setDepartment(departmentSummary);
        }

        return response;
    }

    /**
     * Updates an existing Employee entity from a full EmployeeRequest DTO.
     */
    public static void updateFromRequest(EmployeeRequest request, Employee employee) {
        if (request == null || employee == null) {
            return;
        }

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setAddress(request.getAddress());
        employee.setRole(request.getRole());
        employee.setSalary(request.getSalary());
    }

    /**
     * Updates an existing Employee entity from an EmployeePersonalInfoRequest DTO.
     */
    public static void updatePersonalInfoFromRequest(EmployeePersonalInfoRequest request, Employee employee) {
        if (request == null || employee == null) {
            return;
        }

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setAddress(request.getAddress());
    }
}