# Organisation Management System

## Overview

The Organisation Management System is designed to handle the management of employees, their departments, and other associated administrative tasks. It supports employee registration, profile updates, department management, and the processing of department removal requests. The system also includes validation of employees based on employee code and date of birth.

## Features
- **Employee Management**: Add, update, search, and delete employees.
- **Department Management**: Create, update, delete departments, and assign employees to departments.
- **Employee Validation**: Validate employees using their employee code and date of birth.
- **Department Removal Requests**: Employee can submit and Admin can handle requests for removing a department from an employee's dashboard.

## Technology Stack
- **Backend**: Java (Spring Boot)
- **Database**: MySQL
- **Authentication**: Spring Security
- **Frontend**: (Not specified, but can be integrated with React or any other frontend framework)
- **Unit Testing**: JUnit, Mockito

---

## Setup Instructions

### Prerequisites

Before you begin, ensure you have the following installed:
- **Java 17** (or higher)
- **Maven** (for building the project)
- **MySQL** (or any relational database)

### 1. Clone the repository

```bash
git clone https://github.com/syncwithrohit/orgMgmtSys
cd orgMgmtSys
```

### 2. Configure the database

Ensure that you have MySQL installed and running. Create a new database, e.g., `employee_management`, and configure the `application.properties` file with your database details.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ofc_mgmt
spring.datasource.username=yourUser
spring.datasource.password=yourPass
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Build and run the project

You can build and run the project using Maven.

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080` by default.

### 4. Swagger UI

After the application starts, you can access the Swagger UI documentation for API endpoints at:

```
http://localhost:8080/swagger-ui.html
```

---

## API Endpoints

### Authentication

- **POST /auth/login**
  - Description: Login using employee code and date of birth.
  - Request Body:
    ```json
    {
      "empCode": "RRS202504241",
      "dob": "1990-01-01"
    }
    ```
  - Response:
    ```json
    {
      "message": "User logged in successfully."
    }
    ```

### Employee Management

- **POST /emp/{empCode}/update-profile**
  - Description: Update employee details using the employee code.
  - Request Body:
    ```json
    {
      "empCode": "RRS202504241",
      "name": "Rohit Sharma",
      "email": "syncwithrohit@gmail.com"
    }
    ```
  - Response:
    ```json
    {
      "message": "Details updated successfully for Employee Code: EMP001"
    }
    ```

- **GET /emp/{empCode}/depts**
  - Description: Get the list of departments assigned to the employee.
  - Response:
    ```json
    [
      {
        "deptId": 1,
        "deptName": "HR"
      },
      {
        "deptId": 2,
        "deptName": "Engineering"
      }
    ]
    ```

- **POST /emp/{empCode}/req-dept-removal**
  - Description: Submit a department removal request for the employee.
  - Request Body:
    ```json
    {
      "deptId": 1,
      "reason": "Relocation"
    }
    ```
  - Response:
    ```json
    {
      "message": "Your department removal request has been submitted successfully for review."
    }
    ```

- **POST /emp/validate**
  - Description: Validate employee using employee code and date of birth.
  - Request Body:
    ```json
    {
      "empCode": "RRS202504241",
      "dob": "1990-01-01"
    }
    ```
  - Response:
    ```json
    {
      "empCode": "RRS202504241",
      "name": "Rohit Sharma"
    }
    ```

### Department Management

- **POST /admin/add-dept**
  - Description: Add a new department.
  - Request Body:
    ```json
    {
      "name": "Sales",
      "description": "Sales department",
      "num_of_emps": 5
    }
    ```
  - Response:
    ```json
    {
      "deptId": 3,
      "name": "Sales",
      "description": "Sales department",
      "num_of_emps": 5
    }
    ```

- **POST /admin/emps/{empId}/assign-depts**
  - Description: Assign departments to an employee.
  - Request Body:
    ```json
    [1, 2]
    ```
  - Response:
    ```json
    {
      "message": "Departments assigned successfully to Employee Id: 1"
    }
    ```

---

## Testing

The project includes unit tests for service methods and controllers.

To run tests, use the following Maven command:

```bash
mvn test
```

### Note:
- The API paths for employee management, department management, and authentication are grouped by role (e.g., `/emp/{empCode}` for employee-related actions, `/admin` for admin actions).
- Swagger UI provides an easy-to-use interface for testing the APIs interactively.
