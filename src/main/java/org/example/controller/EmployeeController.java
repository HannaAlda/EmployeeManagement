package org.example.controller;

import org.example.model.Employee;
import org.example.model.EmployeeWorkedHours;
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
        String result = employeeService.createEmployee(employee);
        if ("success".equals(result)) {
            return ResponseEntity.ok("{\"success\": true, \"id\": " + employee.getId() + "}");
        }
        return ResponseEntity.badRequest().body("{\"success\": false, \"error\": \"" + result + "\"}");
    }

    @PostMapping("/work-hours")
    public ResponseEntity<?> registerWorkHours(@RequestBody EmployeeWorkedHours workedHours) {
        String result = employeeService.registerWorkHours(workedHours);
        if ("success".equals(result)) {
            return ResponseEntity.ok("{\"success\": true, \"id\": " + workedHours.getId() + "}");
        }
        return ResponseEntity.badRequest().body("{\"success\": false, \"error\": \"" + result + "\"}");
    }

    @GetMapping("/by-job")
    public ResponseEntity<?> getEmployeesByJob(@RequestParam int jobId) {
        List<Employee> employees = employeeService.getEmployeesByJob(jobId);
        if (employees != null) {
            return ResponseEntity.ok("{\"success\": true, \"employees\": " + employees + "}");
        }
        return ResponseEntity.badRequest().body("{\"success\": false, \"error\": \"Job does not exist\"}");
    }

    @GetMapping("/calculate-work-hours")
    public ResponseEntity<?> calculateWorkedHours(@RequestParam int employeeId,
                                                  @RequestParam Date startDate,
                                                  @RequestParam Date endDate) {
        String result = employeeService.calculateWorkedHours(employeeId, startDate, endDate);
        if (!"error".equals(result)) {
            return ResponseEntity.ok("{\"success\": true, \"total_worked_hours\": " + result + "}");
        }
        return ResponseEntity.badRequest().body("{\"success\": false, \"error\": \"Invalid input or employee not found\"}");
    }

    @GetMapping("/calculate-pay")
    public ResponseEntity<?> calculatePay(@RequestParam int employeeId,
                                          @RequestParam Date startDate,
                                          @RequestParam Date endDate) {
        String result = employeeService.calculatePay(employeeId, startDate, endDate);
        if (!"error".equals(result)) {
            return ResponseEntity.ok("{\"success\": true, \"payment\": " + result + "}");
        }
        return ResponseEntity.badRequest().body("{\"success\": false, \"error\": \"Invalid input or employee not found\"}");
    }

    @GetMapping("/employees-by-salary")
    public ResponseEntity<?> getEmployeesBySalary(@RequestParam double minSalary,
                                                  @RequestParam double maxSalary,
                                                  @RequestParam String order,
                                                  @RequestParam int size) {
        List<Employee> employees = employeeService.getEmployeesBySalary(minSalary, maxSalary, order, size);
        if (employees != null) {
            return ResponseEntity.ok("{\"success\": true, \"employees\": " + employees + "}");
        }
        return ResponseEntity.badRequest().body("{\"success\": false, \"error\": \"Invalid input or no results\"}");
    }
}
