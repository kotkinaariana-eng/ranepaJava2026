package com.example.hrm.service;

import com.example.hrm.dto.EmployeeRequestDto;
import com.example.hrm.dto.EmployeeResponseDto;
import com.example.hrm.dto.EmployeeStatsDto;
import com.example.hrm.exception.EmployeeNotFoundException;
import com.example.hrm.model.Employee;
import com.example.hrm.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return convertToDto(employee);
    }

    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto) {
        Employee employee = new Employee();
        employee.setName(requestDto.getName());
        employee.setPosition(requestDto.getPosition());
        employee.setSalary(requestDto.getSalary());
        employee.setHireDate(requestDto.getHireDate() != null ?
                requestDto.getHireDate() : java.time.LocalDate.now());

        Employee saved = employeeRepository.save(employee);
        return convertToDto(saved);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> findByPosition(String position) {
        return employeeRepository.findByPositionIgnoreCase(position).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmployeeStatsDto getStatistics() {
        List<Employee> employees = employeeRepository.findAll();

        if (employees.isEmpty()) {
            return new EmployeeStatsDto(0, BigDecimal.ZERO, BigDecimal.ZERO, "N/A");
        }

        long totalEmployees = employees.size();

        // Средняя зарплата
        BigDecimal averageSalary = employees.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(totalEmployees), 2, RoundingMode.HALF_UP);

        // Максимальная зарплата и сотрудник
        Employee highestPaid = employees.stream()
                .max((e1, e2) -> e1.getSalary().compareTo(e2.getSalary()))
                .orElse(null);

        return new EmployeeStatsDto(
                totalEmployees,
                averageSalary,
                highestPaid != null ? highestPaid.getSalary() : BigDecimal.ZERO,
                highestPaid != null ? highestPaid.getName() : "N/A"
        );
    }

    private EmployeeResponseDto convertToDto(Employee employee) {
        return new EmployeeResponseDto(
                employee.getId(),
                employee.getName(),
                employee.getPosition(),
                employee.getSalary(),
                employee.getHireDate()
        );
    }
}