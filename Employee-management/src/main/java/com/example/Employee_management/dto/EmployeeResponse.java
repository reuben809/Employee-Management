package com.example.Employee_management.dto;


import com.example.Employee_management.entity.embedded.Address;
import com.example.Employee_management.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class EmployeeResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
    private Role role;
    private BigDecimal salary;
    private ManagerSummary manager;

    private DepartmentSummary department;

}
