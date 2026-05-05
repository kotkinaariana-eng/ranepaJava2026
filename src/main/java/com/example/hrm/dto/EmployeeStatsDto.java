package com.example.hrm.dto;

import java.math.BigDecimal;

public class EmployeeStatsDto {
    private long totalEmployees;
    private BigDecimal averageSalary;
    private BigDecimal maxSalary;
    private String highestPaidEmployeeName;

    public EmployeeStatsDto(long totalEmployees, BigDecimal averageSalary,
                            BigDecimal maxSalary, String highestPaidEmployeeName) {
        this.totalEmployees = totalEmployees;
        this.averageSalary = averageSalary;
        this.maxSalary = maxSalary;
        this.highestPaidEmployeeName = highestPaidEmployeeName;
    }

    // Getters
    public long getTotalEmployees() { return totalEmployees; }
    public BigDecimal getAverageSalary() { return averageSalary; }
    public BigDecimal getMaxSalary() { return maxSalary; }
    public String getHighestPaidEmployeeName() { return highestPaidEmployeeName; }
}