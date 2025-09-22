package com.example.Employee_management.dto;

import com.example.Employee_management.entity.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ManagerSummary {
    private UUID id;
    private String firstName;
    private String lastName;
}
