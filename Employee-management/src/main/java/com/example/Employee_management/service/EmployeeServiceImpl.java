package com.example.Employee_management.service;

import com.example.Employee_management.dto.EmployeePersonalInfoRequest;
import com.example.Employee_management.dto.EmployeeRequest;
import com.example.Employee_management.dto.EmployeeResponse;
import com.example.Employee_management.entity.Department;
import com.example.Employee_management.entity.Employee;
import com.example.Employee_management.mapper.EmployeeMapper;
import com.example.Employee_management.repository.DepartmentRepository;
import com.example.Employee_management.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository; // <-- CHANGE: Inject DepartmentRepository

    // <-- CHANGE: Update constructor to accept DepartmentRepository
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Employee employee = EmployeeMapper.toEntity(request);

        // Assign Manager if managerId is provided
        if (request.getManagerId() != null) {
            Employee manager = employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found with ID: " + request.getManagerId()));
            employee.setManager(manager);
        }

        // <-- CHANGE: Find and assign department using the flexible request
        Department department = findDepartment(request.getDepartmentId(), request.getDepartmentName());
        employee.setDepartment(department);

        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.toResponse(savedEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));
        return EmployeeMapper.toResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(UUID id, EmployeeRequest request) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));

        EmployeeMapper.updateFromRequest(request, existingEmployee);

        // Handle manager update
        if (request.getManagerId() != null) {
            Employee manager = employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found with ID: " + request.getManagerId()));
            existingEmployee.setManager(manager);
        } else {
            existingEmployee.setManager(null);
        }

        // <-- CHANGE: Handle department update
        Department department = findDepartment(request.getDepartmentId(), request.getDepartmentName());
        existingEmployee.setDepartment(department);

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return EmployeeMapper.toResponse(updatedEmployee);
    }

    @Override
    @Transactional
    public void deleteEmployee(UUID id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }

    // <-- CHANGE: New method implementation for personal info updates
    @Override
    @Transactional
    public EmployeeResponse updateEmployeePersonalInfo(UUID employeeId, EmployeePersonalInfoRequest request) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));

        EmployeeMapper.updatePersonalInfoFromRequest(request, existingEmployee);

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return EmployeeMapper.toResponse(updatedEmployee);
    }
    private Department findDepartment(UUID id, String name) {
        if (id != null) {
            return departmentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + id));
        }
        if (name != null) {
            return departmentRepository.findByName(name)
                    .orElseThrow(() -> new EntityNotFoundException("Department not found with name: " + name));
        }
        return null; // No department info was provided
    }
}