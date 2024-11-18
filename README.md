
# Examen Práctico Java

> Proyecto desarrollado en **Spring Boot** para la gestión de empleados, registro de horas trabajadas y consultas avanzadas con validaciones y acceso a base de datos.

---

## 📋 Requisitos del Sistema

- **Java 17 o superior**
- **Maven 3.6+**
- **Base de datos MySQL u Oracle**
- **Postman** (opcional para pruebas de la API)

---

## 🛠️ Configuración del Proyecto

### 1. Configuración de la Base de Datos
1. Crea una base de datos en tu sistema gestor (MySQL u Oracle).
2. Ejecuta el script SQL proporcionado en la carpeta `DBExamen` para crear las siguientes tablas:
  - **employees**
  - **employee_worked_hours**
  - **jobs**
  - **genders**

### 2. Configuración de `application.properties`
Edita el archivo de configuración con los datos de tu base de datos:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tu_base_datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 🚀 Ejecución del Proyecto

### 1. Clonar el repositorio
```bash
git clone https://github.com/tu-repositorio/examen-practico-java.git
cd examen-practico-java
```

### 2. Construir el proyecto con Maven
```bash
mvn clean install
```

### 3. Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La API estará disponible en:  
`http://localhost:8080`

---

## 📚 Endpoints de la API

### **1. Crear un empleado**
- **URL:** `POST /api/employees`
- **Descripción:** Crea un nuevo empleado validando nombre, apellido, género, puesto y edad.
- **Body de Ejemplo:**
  ```json
  {
      "name": "Juan",
      "lastName": "Pérez",
      "birthdate": "1990-05-15",
      "genderId": 1,
      "jobId": 1
  }
  ```
- **Respuesta Exitosa:**
  ```json
  {
      "success": true,
      "id": 10
  }
  ```

---

### **2. Registrar horas trabajadas**
- **URL:** `POST /api/employees/work-hours`
- **Descripción:** Registra las horas trabajadas validando duplicados, límite de horas y fechas.
- **Body de Ejemplo:**
  ```json
  {
      "employeeId": 10,
      "workedHours": 8,
      "workedDate": "2024-11-15"
  }
  ```
- **Respuesta Exitosa:**
  ```json
  {
      "success": true,
      "id": 100
  }
  ```

---

### **3. Consultar empleados por puesto**
- **URL:** `GET /api/employees/by-job`
- **Descripción:** Devuelve los empleados que tienen un puesto específico.
- **Parámetro de Consulta:** `jobId` (ID del puesto)
- **Ejemplo:**  
  `GET /api/employees/by-job?jobId=1`
- **Respuesta Exitosa:**
  ```json
  {
      "success": true,
      "employees": [
          {
              "id": 10,
              "name": "Juan",
              "lastName": "Pérez",
              "birthdate": "1990-05-15",
              "genderId": 1,
              "jobId": 1
          }
      ]
  }
  ```

---

### **4. Calcular horas trabajadas**
- **URL:** `GET /api/employees/calculate-work-hours`
- **Descripción:** Calcula el total de horas trabajadas en un rango de fechas.
- **Parámetros de Consulta:**
  - `employeeId`: ID del empleado.
  - `startDate`: Fecha de inicio (YYYY-MM-DD).
  - `endDate`: Fecha de fin (YYYY-MM-DD).
- **Ejemplo:**  
  `GET /api/employees/calculate-work-hours?employeeId=10&startDate=2024-11-01&endDate=2024-11-15`
- **Respuesta Exitosa:**
  ```json
  {
      "success": true,
      "total_worked_hours": 40
  }
  ```

---

### **5. Calcular pago**
- **URL:** `GET /api/employees/calculate-pay`
- **Descripción:** Calcula el pago total en un rango de fechas.
- **Parámetros de Consulta:** Igual que en el cálculo de horas trabajadas.
- **Respuesta Exitosa:**
  ```json
  {
      "success": true,
      "payment": 800
  }
  ```

---

### **6. Consultar empleados por rango de salarios**
- **URL:** `GET /api/employees/employees-by-salary`
- **Descripción:** Filtra empleados según salario mínimo, máximo y orden.
- **Parámetros de Consulta:**
  - `minSalary`: Salario mínimo.
  - `maxSalary`: Salario máximo.
  - `order`: `asc` o `desc`.
  - `size`: Número máximo de resultados.
- **Ejemplo:**  
  `GET /api/employees/employees-by-salary?minSalary=1000&maxSalary=5000&order=asc&size=10`
- **Respuesta Exitosa:**
  ```json
  {
      "success": true,
      "employees": [
          {
              "id": 10,
              "name": "Juan",
              "lastName": "Pérez",
              "birthdate": "1990-05-15",
              "job": {
                  "id": 1,
                  "name": "Gerente",
                  "salary": 1500
              },
              "gender": {
                  "id": 1,
                  "name": "Hombre"
              }
          }
      ]
  }
  ```

---

## 🧪 Pruebas con Postman

1. Importa el archivo `postman_collection.json` que se encuentra en la carpeta raíz.
2. Realiza pruebas para cada uno de los endpoints de manera rápida y organizada.

---

## 📌 Notas Adicionales

- **Validador de Datos:** Cada endpoint realiza validaciones específicas para asegurar la integridad de la información.
- **Errores Detallados:** Las respuestas de error incluyen mensajes claros para facilitar la depuración.
- **Flexibilidad:** El proyecto soporta tanto MySQL como Oracle con ligeros ajustes en el `application.properties`.

---

## 📄 Licencia

Este proyecto es de uso académico y está desarrollado exclusivamente como parte de un examen práctico.  
**Todos los derechos reservados.**
