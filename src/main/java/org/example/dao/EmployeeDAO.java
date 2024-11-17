package org.example.dao;

import org.example.model.Employee;
import org.example.model.EmployeeWorkedHours;
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
        String sql = "INSERT INTO employees (name, last_name, birthdate, gender_id, job_id) VALUES (?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql, employee.getName(), employee.getLastName(),
                employee.getBirthdate(), employee.getGenderId(), employee.getJobId());
        return result > 0;
    }

    public boolean registerWorkHours(EmployeeWorkedHours workedHours) {
        String sql = "INSERT INTO employee_worked_hours (employee_id, worked_hours, worked_date) VALUES (?, ?, ?)";
        int result = jdbcTemplate.update(sql, workedHours.getEmployeeId(), workedHours.getWorkedHours(), workedHours.getWorkedDate());
        return result > 0;
    }

    public boolean existsByNameAndLastName(String name, String lastName) {
        String sql = "SELECT COUNT(*) FROM employees WHERE name = ? AND last_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{name, lastName}, Integer.class);
        return count != null && count > 0;
    }

    public boolean existsById(int employeeId) {
        String sql = "SELECT COUNT(*) FROM employees WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{employeeId}, Integer.class);
        return count != null && count > 0;
    }

    public boolean existsGender(int genderId) {
        String sql = "SELECT COUNT(*) FROM genders WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{genderId}, Integer.class);
        return count != null && count > 0;
    }

    public boolean existsJob(int jobId) {
        String sql = "SELECT COUNT(*) FROM jobs WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{jobId}, Integer.class);
        return count != null && count > 0;
    }

    public boolean existsWorkHoursByDate(int employeeId, Date workedDate) {
        String sql = "SELECT COUNT(*) FROM employee_worked_hours WHERE employee_id = ? AND worked_date = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{employeeId, workedDate}, Integer.class);
        return count != null && count > 0;
    }

    public int calculateWorkedHours(int employeeId, Date startDate, Date endDate) {
        String sql = "SELECT SUM(worked_hours) FROM employee_worked_hours WHERE employee_id = ? AND worked_date BETWEEN ? AND ?";
        Integer totalHours = jdbcTemplate.queryForObject(sql, new Object[]{employeeId, startDate, endDate}, Integer.class);
        return totalHours != null ? totalHours : 0;
    }

    public double calculatePay(int employeeId, Date startDate, Date endDate) {
        String sql = "SELECT jobs.salary FROM employees JOIN jobs ON employees.job_id = jobs.id WHERE employees.id = ?";
        Double hourlyRate = jdbcTemplate.queryForObject(sql, new Object[]{employeeId}, Double.class);
        int totalHours = calculateWorkedHours(employeeId, startDate, endDate);
        return hourlyRate != null ? totalHours * hourlyRate : 0.0;
    }

    public List<Employee> getEmployeesBySalary(double minSalary, double maxSalary, String order, int size) {
        String sql = "SELECT employees.*, jobs.salary, genders.name AS gender_name FROM employees " +
                "JOIN jobs ON employees.job_id = jobs.id " +
                "JOIN genders ON employees.gender_id = genders.id " +
                "WHERE jobs.salary BETWEEN ? AND ? " +
                "ORDER BY jobs.salary " + order + " LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{minSalary, maxSalary, size}, new EmployeeRowMapper());
    }

    public List<Employee> getEmployeesByJob(int jobId) {
        String sql = "SELECT employees.*, jobs.salary, genders.name AS gender_name FROM employees " +
                "JOIN jobs ON employees.job_id = jobs.id " +
                "JOIN genders ON employees.gender_id = genders.id " +
                "WHERE jobs.id = ?";
        return jdbcTemplate.query(sql, new Object[]{jobId}, new EmployeeRowMapper());
    }

    private static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setName(rs.getString("name"));
            employee.setLastName(rs.getString("last_name"));
            employee.setBirthdate(rs.getDate("birthdate"));
            employee.setGenderId(rs.getInt("gender_id"));
            employee.setJobId(rs.getInt("job_id"));
            return employee;
        }
    }
}
