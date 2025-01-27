# Backend Documentation for the Triple Helix Project

## Overview

The backend development of the Triple Helix Project was focused on creating a robust, scalable, and maintainable architecture. The primary goals of the project included streamlining data management and providing a seamless interface for both administrators and end-users. The system aims to solve challenges related to data consistency, efficient retrieval, and integration with external tools. It is tailored to meet the needs of organizations requiring a centralized, reliable, and user-friendly backend system. The team, composed of Lorenzo Castiello, Samuele Gallo, and Alessio Suppa, implemented this part using modern Java-based frameworks and tools. This document details the backend structure, technologies, and methodologies employed.

## Technologies Used

- **Java**: The primary programming language for backend development.
- **Spring Boot**: Framework used for creating a RESTful API, offering features like dependency injection, simplified configuration, and seamless integration with database layers.
- **MySQL**: Database management system utilized for persistent storage, modeled according to the provided entity-relationship schema.
- **Thunder Client**: Tool used for testing API endpoints via GET, POST, PUT, and DELETE requests.
- **Postman**: Additionally used for testing and validating API requests and responses.

## Backend Architecture

The backend architecture adheres to a layered structure, ensuring separation of concerns and ease of maintainability. The key layers include:

### Controller Layer:

- Exposes RESTful endpoints.
- Handles HTTP methods like GET, POST, PUT, and DELETE.
- Delegates processing to the service layer.

### Service Layer:

- Contains business logic.
- Mediates between controllers and the data access layer.

### Entity Layer:

- Represents the core data structures in the system.
- Includes classes annotated with JPA annotations such as `@Entity`, `@Table`, `@Id`, etc., for mapping objects to database tables.
- Facilitates clear, reusable, and maintainable models for persistence.

### Data Access Layer (Repository):

- Manages database interactions.
- Uses JPA (Java Persistence API) and Hibernate for ORM (Object-Relational Mapping).

### Database:

- Designed based on the entity-relationship model.
- MySQL was used to implement normalized tables and relationships.

## API Endpoints

The API was built to allow interaction with the system’s core functionalities. Below are examples of the implemented endpoints based on the provided controllers:

### Authentication Controller Endpoints

1. **POST /pub/auth/register**: Register new user.
  - **Request Body**:
    ```json
    {
      "userName": "Mario",
      "userSurname": "Rossi",
      "userEmail": "mario.rossi@example.com",
      "userPassword": "securePassword123"
    }
    ```
    - **Response**: A message that indicates if the registration went well.
      - **Status**: 201 Created: "User registered successfully!".
      - **Status**: 409 Conflict: "Email already in use!".
      - **Status**: 500 Internal Server Errore: "Registration failed".

2. **POST /pub/auth/login**: Login new user.
  - **Request Body**:
    ```json
    {
      "userEmail": "mario.rossi@example.com",
      "userPassword": "securePassword123"
    }
    ```
    - **Response**: A message that indicates if the login went well.
      - **Status**: 200 OK: "User registered successfully!".
      - **Status**: 403 Forbidden: "User is already logged in".
      - **Status**: 401 Unauthorized: "User is already logged in".
      - **Status**: 401 Unauthorized:"User not found or incorrect credentials".

3. **POST /pub/auth/logout**: Logout new user.
    - **Response**: A message that indicates if the logout went well.
      - **Status**: 200 OK: "Logout successful".
      - **Status**: 401 Unauthorized: "User is not logged in".

4. **GET /pub/auth/status**: Authentication status check.
    - **Response**: A message that indicates the authentication status.
      - **Status**: 200 OK: "User is logged in: mario.rossi@example.com".
      - **Status**: 401 Unauthorized: "User is not logged in".  

5. **GET /pub/auth/is-logged**: Check if a user is authenticated.
    - **Response**: A message that indicates the authentication status.
      - **Status**: 200 OK: "True" (if authenticated) or "False" (if not authenticated).

6. **GET /pub/auth/user-info**: Retrieving authenticated user information.
    - **Response**: A message that indicates the authentication status.
      - **Status**: 200 OK:
      ```json
      {
        "userId": 42,
        "userName": "Mario",
        "userSurname": "Rossi",
        "userEmail": "mario.rossi@example.com",
        "userPassword": "$2a$10$3oVbVfidv4P49BcKtqEcJu/pwI7eUTAOMzKiqAatPnL7JBrqlVYl2",
        "resetToken": null,
        "createdAt": "2025-01-27T17:26:35",
        "updatedAt": "2025-01-27T17:26:35",
        "role": {
          "roleId": 2,
          "roleName": "USER",
          "authority": "ROLE_USER"
        },
        "enabled": true,
        "password": "$2a$10$3oVbVfidv4P49BcKtqEcJu/pwI7eUTAOMzKiqAatPnL7JBrqlVYl2",
        "username": "mario.rossi@example.com",
        "authorities": [
          {
            "roleId": 2,
            "roleName": "USER",
            "authority": "ROLE_USER"
          }
        ],
        "accountNonExpired": true,
        "credentialsNonExpired": true,
        "accountNonLocked": true
      }
      ```
      - **Status**: 401 Unauthorized: "User is not logged in".


### Booking Controller Endpoints

- **GET /api/bookings**: Fetches all bookings.
  - **Response**: List of booking objects.
  - **Status**: 200 OK.

- **GET /api/bookings/user**: Fetches bookings by user email.
  - **Query Parameter**: `email` (string) – The user’s email address.
  - **Response**: Booking object or 404 NOT FOUND if not found.

- **POST /api/bookings/create**: Creates a new booking.
  - **Request Body**:
    ```json
    {
      "userRequest": {
        "user": "userId",
        "institute": "instituteId"
      }
    }
    ```
  - **Response**: The created booking object.
  - **Status**: 201 CREATED.

### Feedback Controller Endpoints

- **GET /api/feedbacks**: Fetches all feedback records.
  - **Response**: List of feedback objects.
  - **Status**: 200 OK.
  
- **GET /api/feedbacks/{id}**: Fetches a specific feedback by ID.
  - **Path Parameter**: `id` (integer) – The ID of the feedback.
  - **Response**: Feedback object or 404 NOT FOUND if not found.
  
- **POST /api/feedbacks**: Creates a new feedback.
  - **Request Body**:
    ```json
    {
      "comment": "User comment",
      "rating": 4,
      "bookingId": 123,
      "date": "2025-01-08T10:30:00Z"
    }
    ```
  - **Response**: The created feedback object.
  - **Status**: 201 CREATED.

- **PUT /api/feedbacks/{id}**: Updates an existing feedback.
  - **Path Parameter**: `id` (integer) – The ID of the feedback.
  - **Request Body**: Updated feedback fields.
    ```json
    {
      "comment": "Updated comment",
      "rating": 5,
      "bookingId": 123,
      "date": "2025-01-08T12:00:00Z"
    }
    ```
  - **Response**: Updated feedback object or 404 NOT FOUND if not found.
  - **Status**: 200 OK or 404 NOT FOUND.

- **DELETE /api/feedbacks/{id}**: Deletes a feedback by ID.
  - **Path Parameter**: `id` (integer).
  - **Response**: No content.
  - **Status**: 204 NO CONTENT or 404 NOT FOUND.

### Information Request Controller Endpoints

- **GET /api/requests**: Fetches all user requests.
  - **Response**: List of user request objects.
  - **Status**: 200 OK.

- **GET /api/requests/{id}**: Fetches a specific user request by ID.
  - **Path Parameter**: `id` (integer).
  - **Response**: User request object or 404 NOT FOUND.

- **POST /api/requests**: Creates a new user request.
  - **Request Body**:
    ```json
    {
      "user": "userId",
      "institute": "instituteId"
    }
    ```
  - **Response**: The created user request object.
  - **Status**: 201 CREATED.

### User Controller Endpoints
## Testing and Validation

- **Thunder Client** was employed for manual testing of API endpoints.
- **Postman** was also used to ensure comprehensive API testing, including automated collections and validation scripts.
- JSON payloads were validated to ensure proper serialization/deserialization.
- Edge cases and error handling were thoroughly tested.

## Conclusion

The backend of the Triple Helix Project represents a well-structured and efficiently implemented solution. Its modular design, combined with rigorous testing and adherence to best practices, ensures high performance and scalability.
