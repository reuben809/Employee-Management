package com.example.Employee_management.service;

import com.example.Employee_management.dto.ProjectRequest;
import com.example.Employee_management.dto.ProjectResponse;
import com.example.Employee_management.entity.Employee;
import com.example.Employee_management.entity.Project;
import com.example.Employee_management.mapper.ProjectMapper;
import com.example.Employee_management.repository.EmployeeRepository;
import com.example.Employee_management.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private EmployeeRepository employeeRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest request) {
        Project project = ProjectMapper.toEntity(request);
        Project savedProject = projectRepository.save(project);
        return ProjectMapper.toResponse(savedProject);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + id));
        return ProjectMapper.toResponse(project);
    }

    @Override
    @Transactional
    public ProjectResponse addEmployeeToProject(UUID projectId, UUID employeeId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + projectId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));
        project.getTeamMembers().add(employee);
        employee.getProjects().add(project);
        projectRepository.save(project);
        return ProjectMapper.toResponse(project);
    }

    @Override
    @Transactional
    public ProjectResponse removeEmployeeFromProject(UUID projectId, UUID employeeId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + projectId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));
        project.getTeamMembers().remove(employee);
        employee.getProjects().remove(project);
        projectRepository.save(project);
        return ProjectMapper.toResponse(project);
    }
}
