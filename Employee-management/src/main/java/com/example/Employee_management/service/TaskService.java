package com.example.Employee_management.service;

import com.example.Employee_management.dto.TaskRequest;
import com.example.Employee_management.dto.TaskResponse;
import com.example.Employee_management.entity.enums.TaskStatus;
import java.util.UUID;

public interface TaskService {
    TaskResponse createTask(TaskRequest request);
    TaskResponse getTaskById(UUID id);
    TaskResponse updateTaskStatus(UUID id, TaskStatus status);
}