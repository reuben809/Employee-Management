package com.example.Employee_management.service;

import com.example.Employee_management.dto.DepartmentRequest;
import com.example.Employee_management.dto.DepartmentResponse;
import com.example.Employee_management.entity.Department;
import com.example.Employee_management.entity.Employee;
import com.example.Employee_management.mapper.DepartmentMapper;
import com.example.Employee_management.repository.DepartmentRepository;
import com.example.Employee_management.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getDepartmentById(UUID id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + id));
        return DepartmentMapper.toResponse(department);
    }

    @Override
    @Transactional
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        Department department = DepartmentMapper.toEntity(request);

        if( request.getManagerId() != null ) {
            Employee manager = employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found with id: " + request.getManagerId()));
            department.setManager(manager);
        }
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentMapper.toResponse(savedDepartment);
    }

    @Override
    @Transactional
    public DepartmentResponse updateDepartment(UUID departmentId, DepartmentRequest request) {
        Department existingDepartmetn = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + departmentId));

        existingDepartmetn.setName(request.getName());

        if( request.getManagerId() != null ) {
            Employee newManager = employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found with id: " + request.getManagerId()));
            existingDepartmetn.setManager(newManager);
        }else{
            existingDepartmetn.setManager(null);
        }
        Department updatedDepartmetn = departmentRepository.save(existingDepartmetn);
        return DepartmentMapper.toResponse(updatedDepartmetn);

    }

    @Override
    @Transactional
    public DepartmentResponse assignManagerToDepartment(UUID departmentId, UUID managerId) {
        // 1. Find the department you want to update.
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + departmentId));

        // 2. Find the employee you want to assign as the manager.
        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Manager (Employee) not found with ID: " + managerId));

        // 3. Set the relationship in both directions.
        department.setManager(manager); // This updates the department table
        manager.setDepartment(department); // <-- CHANGE: This updates the employee table

        // 4. Save the changes. Because of the @Transactional annotation,
        // JPA will automatically save the changes to both entities.
        Department savedDepartment = departmentRepository.save(department);

        // 5. Return the updated department info.
        return DepartmentMapper.toResponse(savedDepartment);
    }

    @Override
    @Transactional
    public void deleteDepartment(UUID departmentId) {
        // 1. Check if the department exists before trying to delete it
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Department not found with ID: " + departmentId);
        }
        // 2. Delete the department
        departmentRepository.deleteById(departmentId);
    }
    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(DepartmentMapper::toResponse)
                .collect(Collectors.toList()); // 4. Collect results into a list
    }
}
