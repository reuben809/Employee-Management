package com.example.Employee_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeSearchRequest {
    private EmployeeSearchCriteria criteria;
    private SortRequest sort;
}

