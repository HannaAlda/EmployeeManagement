package org.example.service;

import org.example.dao.EmployeeWorkedHoursDAO;
import org.example.model.EmployeeWorkedHours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmployeeWorkedHoursService {

    @Autowired
    private EmployeeWorkedHoursDAO workedHoursDAO;

    public int registerWorkedHours(EmployeeWorkedHours workedHours) {
        return workedHoursDAO.registerWorkedHours(workedHours);
    }

    public int getTotalWorkedHours(int employeeId, Date startDate, Date endDate) {
        return workedHoursDAO.getTotalWorkedHours(employeeId, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
    }
}
