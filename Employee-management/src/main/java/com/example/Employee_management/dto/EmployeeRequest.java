package com.example.Employee_management.dto;

import com.example.Employee_management.entity.embedded.Address;
import com.example.Employee_management.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class EmployeeRequest {
    private String FirstName;
    private String LastName;
    private String Email;
    private Address address;
    private Role role;
    private BigDecimal salary;
    private UUID managerId;

    private UUID departmentId;
    private String departmentName;
}
