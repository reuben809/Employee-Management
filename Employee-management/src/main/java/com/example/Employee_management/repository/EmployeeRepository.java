package com.example.Employee_management.repository;

import com.example.Employee_management.entity.Employee;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Collection;
import java.util.UUID;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> ,JpaSpecificationExecutor<Employee>{

}
