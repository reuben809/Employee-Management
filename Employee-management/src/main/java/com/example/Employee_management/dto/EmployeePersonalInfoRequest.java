package com.example.Employee_management.dto;

import com.example.Employee_management.entity.embedded.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeePersonalInfoRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}