package com.codenzic.employee_management.service;

import com.codenzic.employee_management.dto.EmployeeRequest;
import com.codenzic.employee_management.dto.EmployeeResponse;
import com.codenzic.employee_management.entity.Employee;
import com.codenzic.employee_management.exception.DuplicateResourceException;
import com.codenzic.employee_management.exception.ResourceNotFoundException;
import com.codenzic.employee_management.repository.EmployeeRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeResponse createEmployee(EmployeeRequest request) {
        String email = normalizeEmail(request.getEmail());

        if (employeeRepository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateResourceException(
                    "An employee with this email already exists");
        }

        Employee employee = new Employee();
        copyRequestToEntity(request, employee);
        employee.setEmail(email);

        try {
            Employee saved = employeeRepository.saveAndFlush(employee);
            return toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateResourceException(
                    "An employee with this email already exists");
        }
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = findEmployee(id);
        return toResponse(employee);
    }

    public EmployeeResponse updateEmployee(
            Long id, EmployeeRequest request) {

        Employee employee = findEmployee(id);
        String email = normalizeEmail(request.getEmail());

        if (employeeRepository.existsByEmailIgnoreCaseAndIdNot(
                email, id)) {
            throw new DuplicateResourceException(
                    "Another employee already uses this email");
        }

        copyRequestToEntity(request, employee);
        employee.setEmail(email);

        try {
            Employee saved = employeeRepository.saveAndFlush(employee);
            return toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateResourceException(
                    "An employee with this email already exists");
        }
    }

    public void deleteEmployee(Long id) {
        Employee employee = findEmployee(id);
        employeeRepository.delete(employee);
    }

    private Employee findEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with ID: " + id));
    }

    private void copyRequestToEntity(
            EmployeeRequest request, Employee employee) {

        employee.setFirstName(request.getFirstName().trim());
        employee.setLastName(request.getLastName().trim());
        employee.setEmail(normalizeEmail(request.getEmail()));
        employee.setDepartment(request.getDepartment().trim());
        employee.setSalary(request.getSalary());
        employee.setStatus(request.getStatus()
                .trim().toUpperCase(Locale.ROOT));
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary(),
                employee.getStatus()
        );
    }
}
