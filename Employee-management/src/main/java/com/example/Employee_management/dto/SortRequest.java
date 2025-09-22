package com.example.Employee_management.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class SortRequest {
    private String field;
    private Sort.Direction direction = Sort.Direction.ASC;

}
