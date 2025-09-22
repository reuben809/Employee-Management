package com.example.Employee_management.controller;

import com.example.Employee_management.dto.ProjectRequest;
import com.example.Employee_management.dto.ProjectResponse;
import com.example.Employee_management.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;


    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest request) {
        return new ResponseEntity<>(projectService.createProject(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable UUID id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PostMapping("/{projectId}/employees/{employeeId}")
    public ResponseEntity<ProjectResponse> addEmployeeToProject(@PathVariable UUID projectId, @PathVariable UUID employeeId) {
        return ResponseEntity.ok(projectService.addEmployeeToProject(projectId, employeeId));
    }

    @DeleteMapping("/{projectId}/employees/{employeeId}")
    public ResponseEntity<ProjectResponse> removeEmployeeFromProject(@PathVariable UUID projectId, @PathVariable UUID employeeId) {
        return ResponseEntity.ok(projectService.removeEmployeeFromProject(projectId, employeeId));
    }
}