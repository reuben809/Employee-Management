package com.example.Employee_management.entity;

import com.example.Employee_management.entity.embedded.Address;
import com.example.Employee_management.entity.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Min(value = 0, message = "Salary cannot be negative")
    private BigDecimal salary;

    // This represents the department this employee MANAGES (if they are a manager).
    @OneToOne(mappedBy = "manager")
    private Department managedDepartment;

    // This represents the direct manager this employee REPORTS TO.
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    // <-- CHANGE: This is the direct link to the department the employee BELONGS TO.
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "assignedTo")
    private Set<Task> tasksAssignedTo = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(email, employee.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}