package com.example.Employee_management.dto;

import com.example.Employee_management.entity.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class TaskResponse {
    private UUID id;
    private String name;
    private String description;
    private TaskStatus status;
    private UUID projectId;
    private EmployeeSummary assignedTo;
}