package com.example.Employee_management.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class EmployeeSummary {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}