package org.example.controller;

import org.example.model.Employee;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        boolean success = employeeService.createEmployee(employee);
        return ResponseEntity.ok("{\"success\": " + success + "}");
    }

    @PostMapping("/work-hours")
    public ResponseEntity<?> registerWorkHours(@RequestParam int employeeId,
                                               @RequestParam int workedHours,
                                               @RequestParam Date workedDate) {
        boolean success = employeeService.registerWorkHours(employeeId, workedHours, workedDate);
        return ResponseEntity.ok("{\"success\": " + success + "}");
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/calculate-work-hours")
    public ResponseEntity<?> calculateWorkedHours(@RequestParam int employeeId,
                                                  @RequestParam Date startDate,
                                                  @RequestParam Date endDate) {
        int totalWorkedHours = employeeService.calculateWorkedHours(employeeId, startDate, endDate);
        return ResponseEntity.ok("{\"total_worked_hours\": " + totalWorkedHours + "}");
    }

    @GetMapping("/calculate-pay")
    public ResponseEntity<?> calculatePay(@RequestParam int employeeId,
                                          @RequestParam Date startDate,
                                          @RequestParam Date endDate) {
        double pay = employeeService.calculatePay(employeeId, startDate, endDate);
        return ResponseEntity.ok("{\"payment\": " + pay + "}");
    }

    @GetMapping("/employees-by-salary")
    public ResponseEntity<List<Employee>> getEmployeesBySalary(
            @RequestParam double minSalary, @RequestParam double maxSalary,
            @RequestParam String order, @RequestParam int size) {
        List<Employee> employees = employeeService.getEmployeesBySalary(minSalary, maxSalary, order, size);
        return ResponseEntity.ok(employees);
    }
}
