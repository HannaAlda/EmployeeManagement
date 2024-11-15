package org.example.dao;

import org.example.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean createEmployee(Employee employee) {
        String sql = "INSERT INTO employees (name, last_name, birthdate) VALUES (?, ?, ?)";
        int result = jdbcTemplate.update(sql, employee.getName(), employee.getLastName(), employee.getBirthdate());
        return result > 0;
    }

    public boolean registerWorkHours(int employeeId, int workedHours, Date workedDate) {
        String sql = "INSERT INTO employee_worked_hours (employee_id, worked_hours, worked_date) VALUES (?, ?, ?)";
        int result = jdbcTemplate.update(sql, employeeId, workedHours, workedDate);
        return result > 0;
    }

    public int calculateWorkedHours(int employeeId, Date startDate, Date endDate) {
        String sql = "SELECT SUM(worked_hours) FROM employee_worked_hours WHERE employee_id = ? AND worked_date BETWEEN ? AND ?";
        Integer totalHours = jdbcTemplate.queryForObject(sql, new Object[]{employeeId, startDate, endDate}, Integer.class);
        return (totalHours != null) ? totalHours : 0;
    }

    public double calculatePay(int employeeId, Date startDate, Date endDate) {
        // Implementación para calcular el pago según las horas trabajadas y la tarifa
        // Ejemplo básico
        double hourlyRate = 20.0; // Tarifa por hora de ejemplo
        int totalHours = calculateWorkedHours(employeeId, startDate, endDate);
        return totalHours * hourlyRate;
    }

    public List<Employee> getEmployeesBySalary(double minSalary, double maxSalary, String order, int size) {
        String sql = "SELECT * FROM employees WHERE salary BETWEEN ? AND ? ORDER BY salary " + order + " LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{minSalary, maxSalary, size}, new EmployeeRowMapper());
    }

    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM employees";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }

    // Clase interna para mapear resultados de la base de datos a objetos Employee
    private static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setName(rs.getString("name"));
            employee.setLastName(rs.getString("last_name"));
            employee.setBirthdate(rs.getDate("birthdate"));
            return employee;
        }
    }
}
