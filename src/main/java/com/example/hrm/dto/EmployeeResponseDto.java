package com.example.hrm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeResponseDto {
    private Long id;
    private String name;
    private String position;
    private BigDecimal salary;
    private LocalDate hireDate;

    public EmployeeResponseDto(Long id, String name, String position, BigDecimal salary, LocalDate hireDate) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public BigDecimal getSalary() { return salary; }
    public LocalDate getHireDate() { return hireDate; }
}