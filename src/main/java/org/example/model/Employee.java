package org.example.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Employee {
    private int id;
    private String name;
    private String lastName;
    private Date birthdate; // Asegúrate de que birthdate esté declarado como un campo

}
