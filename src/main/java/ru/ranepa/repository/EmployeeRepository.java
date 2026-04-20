package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EmployeeRepository {
    private final Map<Long, Employee> employees = new HashMap<>();
    private long nextId = 1;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(nextId++);
        }
        employees.put(employee.getId(), employee);
        return employee;
    }

    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(employees.get(id));
    }

    public boolean delete(Long id) {
        if (!employees.containsKey(id)) {
            return false;
        }
        employees.remove(id);
        return true;
    }

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("ID,Name,Position,Salary,HireDate");
            for (Employee emp : employees.values()) {
                writer.printf("%d,%s,%s,%.2f,%s%n",
                        emp.getId(),
                        emp.getName(),
                        emp.getPosition(),
                        emp.getSalary(),
                        emp.getHireDate().format(dateFormatter));
            }
            System.out.println("Data successfully saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }
}