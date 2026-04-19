package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeRepository {
    private final Map<Long, Employee> storage = new HashMap<>();
    private long currentId = 1;

    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(currentId++);
        }
        storage.put(employee.getId(), employee);
        return employee;
    }

    public List<Employee> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Employee findById(Long id) {
        return storage.get(id);
    }

    public boolean delete(Long id) {
        return storage.remove(id) != null;
    }
}