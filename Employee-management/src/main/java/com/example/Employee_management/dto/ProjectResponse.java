package com.example.Employee_management.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ProjectResponse {
    private UUID id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<EmployeeSummary> teamMembers;
}