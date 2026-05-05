package com.example.hrm.repository;

import com.example.hrm.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Найти сотрудников по должности (без учета регистра)
    List<Employee> findByPositionIgnoreCase(String position);

    // Найти сотрудников с зарплатой больше или равной указанной
    List<Employee> findBySalaryGreaterThanEqual(BigDecimal salary);
}