# Employee Management System

## About the Project

This is a simple Employee Management REST API developed using Java Spring Boot and MySQL. It is created as part of the Java Spring Boot internship assessment for Codenzic Innovations.

The application allows users to manage employee details using REST API endpoints.

## Technologies Used

- Java 25
- Spring Boot 4.1.1
- Spring Data JPA
- MySQL
- Maven 3


## Features

- Add a new employee
- View all employees
- View employee details by ID
- Update employee details
- Delete an employee
- Input validation
- Exception handling

## Database Setup

1. Open MySQL Workbench.
2. Create a database using the following query:

```sql
CREATE DATABASE employee_management;
```

3. Update the database username and password in `application.properties`.

## How to Run

1. Clone or download the project.
2. Open the project in IntelliJ IDEA.
3. Configure the MySQL database.
4. Reload the Maven project and wait for the dependencies to download.
5. Run `EmployeeManagementApplication.java`.

The application runs at:

`http://localhost:8080`

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/employees` | Add an employee |
| GET | `/api/employees` | Get all employees |
| GET | `/api/employees/{id}` | Get employee by ID |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |

## Sample Request

To add an employee, send a POST request to:

`http://localhost:8080/api/employees`

```json
{
  "firstName": "Arun",
  "lastName": "Mathew",
  "email": "arun.mathew@example.com",
  "department": "IT",
  "salary": 35000.00,
  "status": "ACTIVE"
}
```

## Testing

The API can be tested using Postman.

## Author

Developed as part of the Codenzic Innovations Java Spring Boot internship assessment.
