package com.example.Employee_management.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class TaskRequest {
    private String name;
    private String description;
    private UUID projectId;
    private UUID assignedToId;
}