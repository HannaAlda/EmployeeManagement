package org.example.controller;

import org.example.model.EmployeeWorkedHours;
import org.example.service.EmployeeWorkedHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/worked-hours")
public class EmployeeWorkedHoursController {

    @Autowired
    private EmployeeWorkedHoursService employeeWorkedHoursService;

    @PostMapping("/registerWorkedHours")
    public ResponseEntity<?> registerWorkedHours(@RequestBody EmployeeWorkedHours workedHours) {
        int result = employeeWorkedHoursService.registerWorkedHours(workedHours);
        boolean success = result > 0;
        return ResponseEntity.ok("{\"success\": " + success + "}");
    }

    @GetMapping("/totalWorkedHours")
    public ResponseEntity<?> getTotalWorkedHours(@RequestParam int employeeId,
                                                 @RequestParam Date startDate,
                                                 @RequestParam Date endDate) {
        int totalHours = employeeWorkedHoursService.getTotalWorkedHours(employeeId, startDate, endDate);
        return ResponseEntity.ok("{\"totalWorkedHours\": " + totalHours + "}");
    }
}
