package com.example.Employee_management.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class DepartmentRequest {
    private String name;
    private UUID managerId;
}