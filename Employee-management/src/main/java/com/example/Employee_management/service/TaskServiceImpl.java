package com.example.Employee_management.service;

import com.example.Employee_management.dto.TaskRequest;
import com.example.Employee_management.dto.TaskResponse;
import com.example.Employee_management.entity.Employee;
import com.example.Employee_management.entity.Project;
import com.example.Employee_management.entity.Task;
import com.example.Employee_management.entity.enums.TaskStatus;
import com.example.Employee_management.mapper.TaskMapper;
import com.example.Employee_management.repository.EmployeeRepository;
import com.example.Employee_management.repository.ProjectRepository;
import com.example.Employee_management.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;


    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, EmployeeRepository employeeRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + request.getProjectId()));
        Employee assignedTo = employeeRepository.findById(request.getAssignedToId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + request.getAssignedToId()));
        Task task = TaskMapper.toEntity(request);
        task.setProject(project);
        task.setAssignedTo(assignedTo);
        Task savedTask = taskRepository.save(task);
        return TaskMapper.toResponse(savedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));
        return TaskMapper.toResponse(task);
    }

    @Override
    @Transactional
    public TaskResponse updateTaskStatus(UUID id, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));
        task.setStatus(status);
        Task updatedTask = taskRepository.save(task);
        return TaskMapper.toResponse(updatedTask);
    }
}