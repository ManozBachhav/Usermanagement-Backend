# User Management System – Spring Boot Backend

A scalable and secure **User Management System REST API** built using Spring Boot. This backend application provides complete user lifecycle management with JWT-based authentication and clean layered architecture.

---

##  Overview

This project enables efficient handling of user data with secure authentication and modular architecture. It follows best practices such as DTO usage, global exception handling, and separation of concerns.

---

##  Features

*  JWT-based Authentication & Authorization
*  User Registration & Login System
*  CRUD Operations (Create, Read, Update, Delete)
*  Layered Architecture (Controller, Service, Repository, DTO)
*  Spring Data JPA for database interaction
*  Global Exception Handling
*  Custom API Response Structure
*  API Testing using Postman


##  Tech Stack

| Layer      | Technology                  |
| ---------- | --------------------------- |
| Backend    | Java, Spring Boot           |
| ORM        | Spring Data JPA (Hibernate) |
| Database   | MySQL                       |
| Security   | JWT (JSON Web Token)        |
| Build Tool | Maven                       |
| Testing    | Postman                     |


##  Project Structure

```
src/main/java/com/
│
├── controller/     → Handles API requests
├── service/        → Business logic layer
├── repository/     → Database operations
├── dto/            → Data Transfer Objects
├── entity/         → JPA entities
├── config/         → Security & JWT config
├── exception/      → Global exception handler
```


## 🔐 Authentication Flow

1. User registers via `/api/auth/register`
2. User logs in via `/api/auth/login`
3. JWT token is generated
4. Token is required for accessing protected APIs


##  API Endpoints

###  Authentication APIs

| Method | Endpoint           | Description   |
| ------ | ------------------ | ------------- |
| POST   | /api/auth/register | Register user |
| POST   | /api/auth/login    | Login user    |

### 👤 User APIs

| Method | Endpoint        | Description    |
| ------ | --------------- | -------------- |
| GET    | /api/users      | Get all users  |
| GET    | /api/users/{id} | Get user by ID |
| PUT    | /api/users/{id} | Update user    |
| DELETE | /api/users/{id} | Delete user    |


##  Setup & Installation

### 1️) Clone the Repository

```bash
git clone https://github.com/ManozBachhav/Usermanagement-Backend
cd Usermanagement-Backend
```


### 2️) Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```


### 3️) Run the Application

```bash
mvn spring-boot:run
```


##  Testing

Use Postman to test APIs:

Add JWT token in header:

```
Authorization: Bearer <your_token>
```


##  Sample Response

```json
{
  "status": "success",
  "message": "User fetched successfully",
  "data": {
    "id": 1,
    "name": "Manoj",
    "email": "manoj@example.com"
  }
}
```

##  Error Handling

Handled using global exception handler:

```json
{
  "status": "error",
  "message": "User not found"
}
```

##  Future Enhancements

*  Role-Based Access Control (Admin/User)
*  Pagination & Sorting
*  Email Verification
*  Password Reset System


##  Author

**Manoj Bachhav**
