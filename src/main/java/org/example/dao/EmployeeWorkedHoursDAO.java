package org.example.dao;

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
public class EmployeeWorkedHoursDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int registerWorkedHours(EmployeeWorkedHours workedHours) {
        String sql = "INSERT INTO employee_worked_hours (employee_id, worked_hours, worked_date) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, workedHours.getEmployeeId(), workedHours.getWorkedHours(), new Date(workedHours.getWorkedDate().getTime()));
    }

    public int getTotalWorkedHours(int employeeId, Date startDate, Date endDate) {
        String sql = "SELECT SUM(worked_hours) FROM employee_worked_hours WHERE employee_id = ? AND worked_date BETWEEN ? AND ?";
        Integer totalHours = jdbcTemplate.queryForObject(sql, new Object[]{employeeId, startDate, endDate}, Integer.class);
        return (totalHours != null) ? totalHours : 0;
    }

    private static class EmployeeWorkedHoursRowMapper implements RowMapper<EmployeeWorkedHours> {
        @Override
        public EmployeeWorkedHours mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmployeeWorkedHours workedHours = new EmployeeWorkedHours();
            workedHours.setId(rs.getInt("id"));
            workedHours.setEmployeeId(rs.getInt("employee_id"));
            workedHours.setWorkedHours(rs.getInt("worked_hours"));
            workedHours.setWorkedDate(rs.getDate("worked_date"));
            return workedHours;
        }
    }
}
