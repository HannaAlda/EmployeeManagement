package org.example.service;

import org.example.dao.EmployeeDAO;
import org.example.model.Employee;
import org.example.model.EmployeeWorkedHours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    public String createEmployee(Employee employee) {
        if (employeeDAO.existsByNameAndLastName(employee.getName(), employee.getLastName())) {
            return "Employee with the same name and last name already exists.";
        }

        if (calculateAge(employee.getBirthdate()) < 18) {
            return "Employee must be at least 18 years old.";
        }

        if (!employeeDAO.existsGender(employee.getGenderId())) {
            return "Invalid gender ID.";
        }

        if (!employeeDAO.existsJob(employee.getJobId())) {
            return "Invalid job ID.";
        }

        boolean created = employeeDAO.createEmployee(employee);
        return created ? "success" : "Error creating employee.";
    }

    public String registerWorkHours(EmployeeWorkedHours workedHours) {
        if (!employeeDAO.existsById(workedHours.getEmployeeId())) {
            return "Employee does not exist.";
        }

        if (workedHours.getWorkedHours() > 20) {
            return "Worked hours cannot exceed 20.";
        }

        if (workedHours.getWorkedDate().after(new Date(System.currentTimeMillis()))) {
            return "Worked date cannot be in the future.";
        }

        if (employeeDAO.existsWorkHoursByDate(workedHours.getEmployeeId(), workedHours.getWorkedDate())) {
            return "Work hours for this date already exist.";
        }

        boolean registered = employeeDAO.registerWorkHours(workedHours);
        return registered ? "success" : "Error registering work hours.";
    }

    public List<Employee> getEmployeesByJob(int jobId) {
        if (!employeeDAO.existsJob(jobId)) {
            return null;
        }
        return employeeDAO.getEmployeesByJob(jobId);
    }

    public String calculateWorkedHours(int employeeId, Date startDate, Date endDate) {
        if (!employeeDAO.existsById(employeeId)) {
            return "error";
        }
        if (startDate.after(endDate)) {
            return "error";
        }
        return String.valueOf(employeeDAO.calculateWorkedHours(employeeId, startDate, endDate));
    }

    public String calculatePay(int employeeId, Date startDate, Date endDate) {
        if (!employeeDAO.existsById(employeeId)) {
            return "error";
        }
        if (startDate.after(endDate)) {
            return "error";
        }
        return String.valueOf(employeeDAO.calculatePay(employeeId, startDate, endDate));
    }

    public List<Employee> getEmployeesBySalary(double minSalary, double maxSalary, String order, int size) {
        if (!order.equalsIgnoreCase("asc") && !order.equalsIgnoreCase("desc")) {
            return null;
        }
        return employeeDAO.getEmployeesBySalary(minSalary, maxSalary, order, size);
    }

    public int calculateAge(Date birthdate) {
        LocalDate today = LocalDate.now();
        LocalDate birthDateLocal = birthdate.toLocalDate();
        return Period.between(birthDateLocal, today).getYears();
    }
}
