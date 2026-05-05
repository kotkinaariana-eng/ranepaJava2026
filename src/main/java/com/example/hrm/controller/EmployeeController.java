package com.example.hrm.controller;

import com.example.hrm.dto.EmployeeRequestDto;
import com.example.hrm.dto.EmployeeResponseDto;
import com.example.hrm.dto.EmployeeStatsDto;
import com.example.hrm.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "API для управления сотрудниками")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @Operation(summary = "Get all employees", description = "Returns list of all employees")
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by ID", description = "Returns employee by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping
    @Operation(summary = "Create new employee", description = "Creates a new employee")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeRequestDto employeeDto) {
        EmployeeResponseDto created = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee", description = "Deletes employee by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deleted"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/position/{position}")
    @Operation(summary = "Find by position", description = "Returns employees by position (case insensitive)")
    public ResponseEntity<List<EmployeeResponseDto>> findByPosition(@PathVariable String position) {
        return ResponseEntity.ok(employeeService.findByPosition(position));
    }

    @GetMapping("/stats")
    @Operation(summary = "Get statistics", description = "Returns company statistics")
    public ResponseEntity<EmployeeStatsDto> getStatistics() {
        return ResponseEntity.ok(employeeService.getStatistics());
    }
}