# Employee Management API

Este proyecto es una API RESTful para la gestión de empleados, la cual permite realizar operaciones CRUD (Crear, Leer, Actualizar, Borrar) y cálculos específicos como el pago y las horas trabajadas de los empleados.

## Endpoints

### 1. Crear un empleado
- **URL**: `/api/employees`
- **Método**: `POST`
- **Request Body**:
    ```json
    {
        "name": "John Doe",
        "lastName": "Doe",
        "birthdate": "1990-01-01"
    }
    ```
- **Response**:
    ```json
    {
        "success": true
    }
    ```

### 2. Registrar horas trabajadas
- **URL**: `/api/employees/{employeeId}/work-hours`
- **Método**: `POST`
- **Request Body**:
    ```json
    {
        "workedHours": 8,
        "workedDate": "2024-01-15"
    }
    ```
- **Response**:
    ```json
    {
        "success": true
    }
    ```

### 3. Consultar horas trabajadas
- **URL**: `/api/employees/{employeeId}/calculate-hours`
- **Método**: `GET`
- **Query Params**:
    - `startDate`: Fecha de inicio (YYYY-MM-DD)
    - `endDate`: Fecha de fin (YYYY-MM-DD)
- **Response**:
    ```json
    {
        "total_worked_hours": 100,
        "success": true
    }
    ```

### 4. Calcular pago
- **URL**: `/api/employees/{employeeId}/calculate-pay`
- **Método**: `GET`
- **Query Params**:
    - `startDate`: Fecha de inicio (YYYY-MM-DD)
    - `endDate`: Fecha de fin (YYYY-MM-DD)
- **Response**:
    ```json
    {
        "payment": 1000,
        "success": true
    }
    ```

### 5. Listar empleados por salario
- **URL**: `/api/employees/employees-by-salary`
- **Método**: `GET`
- **Query Params**:
    - `minSalary`: Salario mínimo
    - `maxSalary`: Salario máximo
    - `order`: Orden (asc o desc)
- **Response**:
    ```json
    {
        "employees": [
            {
                "id": 1,
                "name": "John",
                "lastName": "Doe",
                "birthdate": "1990-01-01",
                "salary": 500
            }
        ],
        "success": true
    }
    ```

### 6. Consultar todos los empleados
- **URL**: `/api/employees`
- **Método**: `GET`
- **Response**:
    ```json
    [
        {
            "id": 1,
            "name": "John Doe",
            "lastName": "Doe",
            "birthdate": "1990-01-01"
        }
    ]
    ```

## Requisitos

- Java 17
- Spring Boot 2.7.0
- MySQL

## Configuración

1. Configura MySQL y crea la base de datos `employee_management`.
2. Agrega las credenciales de acceso a MySQL en el archivo `application.properties`.

---


