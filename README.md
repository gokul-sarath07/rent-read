# RentRead

## Problem Statement
RentRead is a RESTful API service built using Spring Boot that allows users to rent and manage books online. The service uses MySQL for data persistence and provides a secure system for managing users, books, and rentals, with proper authentication and authorization.

## Key Features

- **Authentication & Authorization**:
    - The service uses **Basic Authentication**.
    - There are two roles: `USER` and `ADMIN`.
    - Only registered users can access most endpoints (authenticated).
    - Some endpoints are restricted to specific roles (`USER` or `ADMIN`).

- **Public Endpoints**:
    - Open to everyone (e.g., **Registration**, **Login**).

- **Private Endpoints**:
    - Accessible only by authenticated users (e.g., **Rent a book**).
    - `USER` can rent and return books.
    - `ADMIN` can create, update, and delete books, as well as manage users and their roles.

### Role-based Access Control
- **USER**: Can browse and rent books.
- **ADMIN**: Has full control over book management (create, update, delete) and user management (assign roles).

## API Features

### User Registration and Login
- Users can register by providing:
    - **Email**, **Password**, **First Name**, **Last Name**.
    - If not specified, the **role** defaults to `USER`.
- Passwords are securely stored using **BCrypt** hashing.
- Registered users must log in with their **email** and **password**.

### Book Management
- **Fields**: Title, Author, Genre, Availability Status (indicating whether the book is available for rent).
- **ADMIN** can:
    - Create new books.
- **USER** and **ADMIN** can:
    - View the list of available books.
    - View individual book details.

### Rental Management
- Users can rent books through the service, with the following conditions:
    - A user can have up to **two active rentals** at any time. If a user tries to rent a third book, an error is returned.
    - Users must return books before renting more.
- The system automatically updates book availability when books are rented or returned.

## Additional Requirements

- **Logging**: The service logs important actions (like book creation or rentals) and error messages (like invalid credentials).
- **Error Handling**: The system handles common errors gracefully, returning appropriate HTTP status codes (e.g., 404 for "User not found").
- **Unit Testing**: Includes unit tests using **MockMvc** and **Mockito**. At least three unit tests are recommended.
- **GitHub Repository**: The project should be published to a public repository with meaningful commit messages.
- **Build**: Instructions to generate a JAR file and run the application should be included.

## API Endpoints

### Public Endpoints:
- **POST /api/v1/users/register**: Register a new user.
- **POST /api/v1/users/login**: Login for users.

### Private Endpoints (USER and ADMIN):
- **GET /api/v1/books/all**: Get a list of all books (available to all users).
- **GET /api/v1/books/{bookId}**: Get details of a specific book by its ID.
- **POST /api/v1/books/{bookId}/rent**: Rent a book (requires `USER` or `ADMIN` role).
- **PUT /api/v1/books/{bookId}/return**: Return a rented book (requires `USER` or `ADMIN` role).

### Admin-only Endpoints:
- **POST /api/v1/books/register**: Create a new book (requires `ADMIN` role).
- **PUT /api/v1/users/{userId}/role**: Update a user's role (requires `ADMIN` role).
- **GET /api/v1/users/all**: View all users (requires `ADMIN` role).

## Security Overview

The system is secured using **Basic Authentication**, where each user must provide a valid username (email) and password for access. Specific actions (like registering or logging in) are open to all, but most endpoints require users to be logged in with appropriate roles.

### Role-based Authorization

- The following role-based access control is enforced:
    - `ADMIN` can create, update, delete books, and manage users.
    - `USER` can view books, rent and return them.

- The system checks role-based permissions using the following policies:
    - Public actions like registration are accessible without login.
    - Book creation, deletion, and role updates are restricted to `ADMIN`.
    - Both `USER` and `ADMIN` can rent and return books.

## Running the Application

### Steps to Build & Run

1. **Clone the Repository**: Clone the repository to your local machine.
    ```bash
    git clone https://github.com/gokul-sarath07/rent-read.git
    cd rentread
    ```

2. **Configure the Database**:
    - Update the `application.properties` or `application.yml` file with your MySQL database credentials.

3. **Build the Project**:
    - Using Gradle:
      ```bash
      ./gradlew build
      ```

4. **Run the Application**:
   After building the project, run the JAR file:
   ```bash
   java -jar build/libs/rentRead-0.0.1-SNAPSHOT.jar

### Create Admin user:
1. **Create normal user**: 
    Create a normal user using the ```/api/v1/users/register``` endpoint.
2. **Update MySQL database**: 
    Go to MySQL database and update the created user with below query
   ```bash
    UPDATE users SET role = 'ADMIN' WHERE user_id = <USER_ID>;
    