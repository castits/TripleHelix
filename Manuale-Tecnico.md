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
      - **Status**: 500 Internal Server Error: "Registration failed".

2. **POST /pub/auth/login**: Login new user.
  - **Request Body**:
    ```json
    {
      "userEmail": "mario.rossi@example.com",
      "userPassword": "securePassword123"
    }
    ```
    - **Response**: A message that indicates if the login went well.
      - **Status**: 200 OK: "Login successful".
      - **Status**: 403 Forbidden: "User is already logged in".
      - **Status**: 401 Unauthorized: "Incorrect credentials".
      - **Status**: 401 Unauthorized: "User not found or incorrect credentials".

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

1. **GET /api/bookings**: Fetches all bookings.
  - **Authorization required**: ADMIN role
  - **Response**: List of booking objects.
    - **Status**: 200 OK.
    ```json
    {
      "userName": "Mario",
      "timeSlot": "AFTERNOON",
      "userSurname": "Rossi",
      "participantQuantity": 7,
      "institute": "ITS ICT",
      "userEmail": "mario.rossi@example.com",
      "appointmentDate": "2025-01-28",
      "bookingId": 18,
      "day": "TUESDAY"
    },
    {
      "userName": "Luca",
      "timeSlot": "MORNING",
      "userSurname": "Rossi",
      "participantQuantity": 30,
      "institute": "ITS ICT",
      "userEmail": "luca.rossi@example.com",
      "appointmentDate": "2025-01-31",
      "bookingId": 20,
      "day": "FRIDAY"
    }
    ```

2. **GET /api/bookings/status**: Retrieve bookings based on status.
  - **Authorization required**: ADMIN role
  - **Query Parameter**:
    - `status` (String) – Booking status (REFUSED, PENDING, CONFIRMED).
  - **Response**: List of booking objects.
    - **Status**: 200 OK: List of bookings matching the status.<br>
    ```json
      Example request: "GET /api/bookings/status?status=PENDING"
    ```
    ```json
    {
      "userName": "Mario",
      "timeSlot": "AFTERNOON",
      "userSurname": "Rossi",
      "participantQuantity": 7,
      "institute": "ITS ICT",
      "userEmail": "mario.rossi@example.com",
      "appointmentDate": "2025-01-28",
      "bookingId": 18,
      "day": "TUESDAY"
    }
    ```
    - **Status**: 400 Bad Request: "Invalid status value".
  
3. **GET /api/bookings/user**: Fetches bookings associated with a user's email
  - **Query Parameter**:
    - `email` (string) – The user’s email address.
  - **Response**: List of booking objects.
    - **Status**: 200 OK: List of bookings matching the user's email.<br>
    ```json
      Example request: "GET /api/bookings/user?email=luca.rossi@example.com"
    ```
    ```json
    {
      "userName": "Luca",
      "timeSlot": "AFTERNOON",
      "userSurname": "Rossi",
      "participantQuantity": 7,
      "institute": "ITS ICT",
      "userEmail": "luca.rossi@example.com",
      "appointmentDate": "2025-01-28",
      "bookingId": 18,
      "day": "TUESDAY"
    }
    ```
4. **GET /api/bookings/user-status**: Fetches bookings for a user filtered by status.
  - **Query Parameter**: 
    - `email` (string) – The user’s email address.
    - `status` (String) – Booking status (REFUSED, PENDING, CONFIRMED).
  - **Response**: List of booking objects.
    - **Status**: 200 OK: List of filtered bookings.<br>
    ```json
      Example request: "GET /api/bookings/user-status?email=user@example.com&status=CONFIRMED"
    ```
    ```json
    {
      "userName": "Luca",
      "timeSlot": "AFTERNOON",
      "userSurname": "Rossi",
      "participantQuantity": 7,
      "institute": "ITS ICT",
      "userEmail": "luca.rossi@example.com",
      "appointmentDate": "2025-01-28",
      "bookingId": 18,
      "day": "TUESDAY"
    }
    ```
    - **Status**: 400 Bad Request: Invalid status value.

5. **POST /api/bookings/create**: Creates a new booking.
  - **Request Body**:
    ```json
    {
        "institute": "ITS ICT",
        "participantQuantity": 7,
        "appointmentDate": "2025-01-17",
        "timeSlot": "AFTERNOON",
        "activity": "Visita",
        "bookingInfoReq": "Ciao, questa è una prova"
    }
    ```
  - **Response**: The created booking object.
    - **Status**: 201 CREATED.

6. **POST /api/bookings/update/{id}**: Updates a booking.
  **Path Variable**:
    - `id` (integer) – The ID of the booking
  - **Request Body**:
    ```json
    {
        "participantQuantity": 100,
        "appointmentDate": "2024-01-05",
        "timeSlot": "FULL_DAY"
    }
    ```
  - **Response**: The updated booking object.
    - **Status**: 200 OK: the updated booking.
    - **Status**: 404 Not Found: if the booking is not present.
    - **Status**: 400 Bad Request: if there is no updatable attribute in the body.

7. **POST /api/bookings/change-status/{id}**: Changes a booking status.
  - **Path Variable**:  
    - `id` (integer) – The ID of the booking
  - **Query Parameter**: 
    - `status` (String) – The new booking status (REFUSED, PENDING, CONFIRMED).
  - **Response**: A message.
    - **Status**: 200 OK: "Status updated successfully".
    - **Status**: 404 Not Found: if the booking is not present.
    - **Status**: 400 Bad Request: if the status is not accepted.

7. **POST /api/bookings/delete/{id}**: Deletes a booking.
  - **Path Variable**:  
    - `id` (integer) – The ID of the booking
  - **Response**: A message.
    - **Status**: 200 OK: "Booking deleted successfully".
    - **Status**: 404 Not Found: if the booking is not present.

### Feedback Controller Endpoints

1. **GET /api/feedbacks**: Fetches all feedback records.
  - **Response**: List of feedback objects.
    - **Status**: 200 OK: List of feedbacks.
  
2. **GET /api/feedbacks/{id}**: Fetches a specific feedback by ID.
  - **Path Variable**:
    - `id` (integer) – The ID of the feedback.
  - **Response**:
    - **Status**: 200 OK: The feedback found.
    - **Status**: 404 NOT FOUND: if no feedback is found.
  
3. **POST /api/feedbacks/add**: Creates a new feedback.
  - **Response**: The created feedback object.
  - **Status**: 201 CREATED.

4. **DELETE /api/feedbacks/{id}**: Deletes a feedback by ID.
  - **Path Parameter**:
   - `id` (integer) - The feedback ID.
  - **Response**: A message.
    - **Status**: 200 OK: "Feedback deleted successfully".
    - **Status**: 404 NOT FOUND: if no feedback is found.

### Information Request Controller Endpoints

- **POST /api/information-requests/send**: Send a information request.
  - **Request Body**:
    ```json
    {
      "userName": "Mario",
      "userSurname": "Rossi",
      "userEmail": "mario.rossi@gmail.com",
      "userPhone": "1231231230",
      "informationRequestText": "Ciao, voglio richiedere informazioni per una visita con la mia scuola"
    }
    ```

### User Controller Endpoints

1. **POST /api/users/forgot-password**: Handle forgotten password requests.
  - **Request Body**:
    ```json
    {
      "email": "user@example.com"
    }
    ```
  - **Response**:
    - **Status**: 200 OK: "Password reset email sent".
    - **Status**: 400 Bad Request: "Email is required".
    - **Status**: 404 Not Found: "User not found".

2. **POST /api/users/reset-password**: Handle password reset requests.
  - **Request Body**:
    ```json
    {
      "token": "reset-token",
      "newPassword": "newSecurePassword123"
    }
    ```
  - **Response**:
    - **Status**: 200 OK: "Password updated successfully".
    - **Status**: 400 Bad Request: "Token and new password are required".
    - **Status**: 404 Not Found: "Invalid token".

3. **GET /api/users/auth-role**: Get the role ID of the authenticated user.
  - **Response**: The role ID.
    - **Status**: 200 OK: Role ID of the authenticated user.
    - **Status**: 401 Unauthorized: If no authenticated user is found.

4. **DELETE /api/users/delete/{id}**: Delete a user by ID.
  - **Path Variable**:
    - `id` (integer) - The user ID to delete.
  - **Response**:
    - **Status**: 200 OK: "User deleted successfully".
    - **Status**: 404 Not Found: If no user is found with the given ID.

5. **POST /api/users/create-admin**: Create a new admin user.
  - **Request Body**:
    ```json
    {
      "userName": "Admin",
      "userSurname": "User",
      "userEmail": "admin@example.com",
      "userPassword": "securePassword123"
    }
    ```
  - **Authorization required**: ADMIN role.
  - **Response**:
    - **Status**: 201 Created: The newly created admin object.
    - **Status**: 409 Conflict: "Email already in use!".

## Testing and Validation

- **Thunder Client** was employed for manual testing of API endpoints.
- **Postman** was also used to ensure comprehensive API testing, including automated collections and validation scripts.
- JSON payloads were validated to ensure proper serialization/deserialization.
- Edge cases and error handling were thoroughly tested.