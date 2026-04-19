package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HRMService {
    private final EmployeeRepository employeeRepository;
    public HRMService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee addEmployee(String name, String position, double salary, LocalDate hireDate) {
        Employee employee = new Employee(null, name, position, salary, hireDate);
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public boolean deleteEmployee(Long id) {
        return employeeRepository.delete(id);
    }

    public double calculateAverageSalary() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Employee employee : employees) {
            sum += employee.getSalary();
        }
        return sum / employees.size();
    }

    public Employee findTopPaidEmployee() {
        return employeeRepository.findAll()
                .stream()
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElse(null);
    }

    public List<Employee> findByPosition(String position) {
        return employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getPosition().equalsIgnoreCase(position))
                .collect(Collectors.toList());
    }

    public List<Employee> saveToTXT() {
        List<Employee> employees = employeeRepository.findAll();

        return employees;
    }


    public List<Employee> saveToCSV() {
        List<Employee> employees = employeeRepository.findAll();

        return employees;
    }
}