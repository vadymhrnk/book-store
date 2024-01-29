# Online Book Store App

Welcome to the Online Book Store App project! This project is aimed to create a web application for an online book store.  
This README.md file provides an overview of the project, its purpose, and the entities involved.

## Getting Started

To run the project locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/vadymhrnk/book-store
   ```
2. Download [JDK](https://www.oracle.com/java/technologies/downloads/), [Apache Maven](https://maven.apache.org/download.cgi) and [Docker](https://docs.docker.com/get-docker/)
3. Build and run the project using:
   ```bash
   mvn clean spring-boot:run 
   ```

Alternatively, you can use Docker to build and run the project:

1. Use following commands:
   ```bash
   docker compose build
   docker compose up
   ```

## Technologies Used

### Backend Technologies
- **Java 17**: The primary programming language for backend development.
- **Spring Boot**: The framework for building and deploying Java-based applications with ease.
- **Spring Security**: Ensures secure authentication and authorization within the application.
- **Spring Data JPA**: Simplifies the implementation of data access layers using JPA and Hibernate.
- **Swagger**: Used for API documentation, facilitating efficient API exploration and testing.
- **MySQL**: The relational database management system storing and managing application data.
- **Lombok**: A tool to reduce boilerplate code, enhancing code readability and conciseness.
- **MapStruct**: Simplifies the implementation of bean mappings, reducing manual coding effort.

### Testing and Quality Assurance
- **JUnit 5 with Testcontainers**: For writing and running tests, especially for integration testing with containers.
- **AssertJ**: Provides a fluent assertion library for more readable tests.

### Containerization
- **Docker**: Used for containerizing and deploying applications consistently across various environments.

### API Documentation
- **Springdoc OpenAPI**: An alternative to Swagger for generating OpenAPI documentation.

### Security
- **JWT (JSON Web Token)**: Used for secure communication and authorization between parties.

### Database Migration
- **Liquibase**: Enables database schema version control and seamless migration.

### Build and Dependency Management
- **Maven**: The build and project management tool.
- **Maven Checkstyle Plugin**: Enforces coding standards and styles during the build process.
- **Maven Compiler Plugin**: Configured for compiling Java source code and managing dependencies.


## Roles in Application

### Client (User)
Someone who browses books, adds them to the shopping cart, and makes purchases.

### Manager (Admin)
Someone who manages books in the store and monitors sales.

## Client's abilities

- **Authentication controller:**
    - `POST: /api/auth/registration` -> Sign in to the store.
    - `POST: /api/auth/login` -> Log in to get token and continue to work with store.

- **Book controller:**
    - `GET: /api/books/{id}` -> Examine detailed information about a specific book.
    - `GET: /api/books` -> View all available books.

- **Categories controller:**
    - `GET: /api/categories/{id}` -> Get information about singular category.
    - `GET: /api/categories` -> View all categories available.
    - `GET: /api/categories/{id}/books` -> Find all books that have similar categories.

- **Shopping cart controller:**
    - `POST: /api/cart` -> Add a book to the shopping cart.
    - `GET: /api/cart` -> View the contents of the shopping cart.
    - `PUT: /api/cart/cart-items/{id}` -> Update quantity of a book in the cart.
    - `DELETE: /api/cart/cart-items/{id}` -> Remove a book from the shopping cart.

- **Order controller:**
    - `POST: /api/orders` -> Purchase all books in the shopping cart.
    - `GET: /api/orders` -> View past orders.
    - `GET: /api/orders/{order-id}/items` -> Get all books from a specific order.
    - `GET: /api/orders/{order-id}/items/{item-id}` -> Find a particular book in an order.

## Manager's abilities

- **Book controller:**
    - `POST: /api/books` -> Add a new book to the store.
    - `PUT: /api/books/{id}` -> Modify details of a book.
    - `DELETE: /api/books/{id}` -> Remove a book from the store.

- **Categories controller:**
    - `POST: /api/categories` -> Create a new category.
    - `PUT: /api/categories/{id}` -> Modify details of a category.
    - `DELETE: /api/categories/{id}` -> Remove category.

- **Order controller:**
    - `PATCH: /api/orders/{id}` -> Change the status of an order (e.g., "Shipped" or "Delivered").

## Challenges and Solutions

### 1. Integration Testing with External Dependencies:
**Challenge:** Running integration tests that depend on external services, such as databases, can be challenging.

**Solution:** I used tools like Testcontainers to manage external dependencies during testing. This allowed me to create and manage database containers, providing a consistent environment for integration tests.

### 2. Security Implementation:
**Challenge:** Implementing robust security features, including authentication and authorization, can be complex.

**Solution:** I used Spring Security to build a comprehensive security system. In addition, I carefully tested the security configurations and features to identify and eliminate potential vulnerabilities.

### 3. Database Schema Changes:
**Challenge:** Managing database schema changes, especially in a collaborative environment, can lead to versioning and migration issues.

**Solution:** I used Liquibase to manage the versions of the database schema. This enabled a smooth migration of changes to the database and ensured that I always worked with the same database structure.

### 4. Containerization and Deployment:
**Challenge:** Ensuring a smooth deployment process with Docker and managing containerized applications can be challenging.

**Solution:** I implemented Dockerfile and used Docker Compose for the application

### 5. Maintaining API Documentation:
**Challenge:** Keeping API documentation up-to-date can be time-consuming and prone to errors.

**Solution:** I used tools like Swagger to automate the creation of API documentation. I also made sure that the documentation is integrated into the development workflow, which makes it easier to maintain.

## Possible Improvements

Here are some potential improvements for enhancing the functionality and robustness of the project:

1. **Deployment to Cloud Platforms:**
  - Explore deploying the project to cloud platforms like AWS for improved scalability and availability.

2. **Integration with Third-Party APIs:**
  - Integrate third-party APIs like Telegram or Stripe for additional features or payment processing. Research and implement API integrations to extend the functionality of the application.

3. **Comprehensive Exception Handling:**
  - Ensure comprehensive handling of all possible exceptions that could occur during runtime. Review and update the codebase to handle specific exceptions gracefully. Implement robust error logging for troubleshooting and debugging.
