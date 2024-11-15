package org.example.service;

import org.example.dao.EmployeeDAO;
import org.example.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    public boolean createEmployee(Employee employee) {
        return employeeDAO.createEmployee(employee);
    }

    public boolean registerWorkHours(int employeeId, int workedHours, Date workedDate) {
        return employeeDAO.registerWorkHours(employeeId, workedHours, workedDate);
    }

    public int calculateWorkedHours(int employeeId, Date startDate, Date endDate) {
        return employeeDAO.calculateWorkedHours(employeeId, startDate, endDate);
    }

    public double calculatePay(int employeeId, Date startDate, Date endDate) {
        return employeeDAO.calculatePay(employeeId, startDate, endDate);
    }

    public List<Employee> getEmployeesBySalary(double minSalary, double maxSalary, String order, int size) {
        return employeeDAO.getEmployeesBySalary(minSalary, maxSalary, order, size);
    }

    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }
}
