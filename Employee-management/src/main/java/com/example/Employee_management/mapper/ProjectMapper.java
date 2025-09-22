package com.example.Employee_management.mapper;

import com.example.Employee_management.dto.EmployeeSummary;
import com.example.Employee_management.dto.ProjectRequest;
import com.example.Employee_management.dto.ProjectResponse;
import com.example.Employee_management.entity.Employee;
import com.example.Employee_management.entity.Project;

import java.util.stream.Collectors;

public class ProjectMapper {

    public static Project toEntity(ProjectRequest request) {
        if (request == null) {
            return null;
        }
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        return project;
    }

    public static ProjectResponse toResponse(Project project) {
        if (project == null) {
            return null;
        }
        ProjectResponse response = new ProjectResponse();
        response.setId(project.getId());
        response.setName(project.getName());
        response.setDescription(project.getDescription());
        response.setStartDate(project.getStartDate());
        response.setEndDate(project.getEndDate());

        if (project.getTeamMembers() != null) {
            response.setTeamMembers(project.getTeamMembers().stream()
                    .map(ProjectMapper::toEmployeeSummary)
                    .collect(Collectors.toSet()));
        }

        return response;
    }

    // Helper method to convert an Employee to a summary DTO
    private static EmployeeSummary toEmployeeSummary(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeSummary summary = new EmployeeSummary();
        summary.setId(employee.getId());
        summary.setFirstName(employee.getFirstName());
        summary.setLastName(employee.getLastName());
        summary.setEmail(employee.getEmail());
        return summary;
    }
}