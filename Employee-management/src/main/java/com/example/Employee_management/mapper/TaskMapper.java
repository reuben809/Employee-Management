package com.example.Employee_management.mapper;

import com.example.Employee_management.dto.EmployeeSummary;
import com.example.Employee_management.dto.TaskRequest;
import com.example.Employee_management.dto.TaskResponse;
import com.example.Employee_management.entity.Employee;
import com.example.Employee_management.entity.Task;

public class TaskMapper {

    /**
     * Converts a TaskRequest DTO to a Task entity.
     * The project and assignedTo objects must be set in the service layer.
     */
    public static Task toEntity(TaskRequest request) {
        if (request == null) {
            return null;
        }
        Task task = new Task();
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        // The status will use the default value defined in the Task entity.
        return task;
    }

    /**
     * Converts a Task entity to a TaskResponse DTO.
     */
    public static TaskResponse toResponse(Task task) {
        if (task == null) {
            return null;
        }
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setName(task.getName());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());

        if (task.getProject() != null) {
            response.setProjectId(task.getProject().getId());
        }

        if (task.getAssignedTo() != null) {
            response.setAssignedTo(toEmployeeSummary(task.getAssignedTo()));
        }

        return response;
    }

    // Helper method to convert an Employee to a summary DTO
    private static EmployeeSummary toEmployeeSummary(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeSummary summary = new EmployeeSummary();
        summary.setId(employee.getId());
        summary.setFirstName(employee.getFirstName());
        summary.setLastName(employee.getLastName());
        summary.setEmail(employee.getEmail());
        return summary;
    }
}