package org.example.model;

import lombok.Data;
import java.util.Date;

@Data
public class EmployeeWorkedHours {
    private int id;
    private int employeeId;
    private int workedHours;
    private Date workedDate;
}


