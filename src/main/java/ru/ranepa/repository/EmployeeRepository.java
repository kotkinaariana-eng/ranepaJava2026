package ru.ranepa.repository;

import ru.ranepa.Employee;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class EmployeeRepository {

    private HashMap<Long, Employee> employees = new HashMap<>();

    public boolean save(Employee employee) {
        long id = employee.getId();
        employees.put(id, employee);
        return true;
    }

    public List<Employee> findAll() {
        return employees.values()
                .stream()
                .toList();
    }

    public Employee findById(long id) {
        if (!employees.containsKey(id)) {
            throw new IllegalArgumentException("Такого сотрудника нет");
        }
        return employees.get(id);
    }

    public boolean delete(long id) {
        if (!employees.containsKey(id)) {
            System.out.println("Такого сотрудника нет");
            return false;

        }
        employees.remove(id);
        return true;
    }
}

