package com.example.Employee_management.repository.specification;


import com.example.Employee_management.dto.EmployeeSearchCriteria;
import com.example.Employee_management.entity.Department;
import com.example.Employee_management.entity.Employee;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class EmployeeSpecification {

    public Specification<Employee> searchCriteria(EmployeeSearchCriteria criteria) {
        return (root, criteriaQuery, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getFirstName() != null &&  !criteria.getFirstName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + criteria.getFirstName().toLowerCase() + "%"));
            }
            if (criteria.getLastName() != null && !criteria.getLastName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), criteria.getLastName().toLowerCase() + "%"));
            }
            if (criteria.getEmail() != null && !criteria.getEmail().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + criteria.getEmail().toLowerCase() + "%"));
            }

            // Salary Range
            if (criteria.getSalaryFrom() != null && criteria.getSalaryTo() != null) {
                predicates.add(criteriaBuilder.between(root.get("salaryFrom"), criteria.getSalaryFrom(), criteria.getSalaryTo()));
            } else if (criteria.getSalaryFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("salaryFrom"), criteria.getSalaryFrom()));
            } else if (criteria.getSalaryTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("salaryTo"), criteria.getSalaryTo()));
            }

            // join criteria
            if (criteria.getDepartmentName() != null && criteria.getDepartmentName().isEmpty()) {
                Join<Employee, Department> departmentJoin = root.join("department");
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(departmentJoin.get("name")), "%" + criteria.getDepartmentName().toLowerCase() + "%"));
            }

            // Embedded filed address
            if(criteria.getCity() != null && !criteria.getCity().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), criteria.getCity().toLowerCase() + "%"));
            }
            if(criteria.getState() != null && !criteria.getState().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("state")), criteria.getState().toLowerCase() + "%"));
            }
            if (criteria.getCountry() != null && !criteria.getCountry().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("country")), criteria.getCountry().toLowerCase() + "%"));
            }

            return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };
    }
}
