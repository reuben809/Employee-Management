package com.example.Employee_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmployeeSearchCriteria {

    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal salaryFrom;
    private BigDecimal salaryTo;
    private String departmentName;
    private String city;
    private String state;
    private String country;

}
