package com.example.Employee_management.service;

import com.example.Employee_management.dto.ProjectRequest;
import com.example.Employee_management.dto.ProjectResponse;
import java.util.UUID;

public interface ProjectService {
    ProjectResponse createProject(ProjectRequest request);
    ProjectResponse getProjectById(UUID id);
    ProjectResponse addEmployeeToProject(UUID projectId, UUID employeeId);
    ProjectResponse removeEmployeeFromProject(UUID projectId, UUID employeeId);
}