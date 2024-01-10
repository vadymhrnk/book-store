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

## Technologies Used

  - Java
  - Docker
  - Spring Boot
  - Spring Security
  - Spring Data JPA
  - Swagger (for API documentation)
  - MySQL

## Roles in Application

### Client (User)
Someone who browses books, adds them to the shopping cart, and makes purchases.

### Manager (Admin)
Someone who manages books in the store and monitors sales.

## Client's abilities

- **Log in/Sign In:**
    - Sign in the store.
    - Log in to find out about the selection of books and make purchases.

- **Browse Books:**
    - View all available books.
    - Examine detailed information about a specific book.

- **Browse categories:**
    - View all categories available.
    - Get information about singular category.
    - Find all books that have similar categories.

- **Use shopping cart:**
    - Add a book to the shopping cart.
    - View the contents of the shopping cart.
    - Update quantity of a book in the cart.
    - Remove a book from the shopping cart.

- **Making orders:**
    - Purchase all books in the shopping cart.
    - View past orders.
    - Get all book from a specific order.
    - Find a particular book in an order.

## Manager's abilities

- **Customize Books:**
    - Add a new book to the store.
    - Modify details of a book.
    - Remove a book from the store.

- **Organize categories:**
    - Create a new category.
    - Modify details of a category.
    - Remove category.

- **Manage orders:**
    - Change the status of an order (e.g., "Shipped" or "Delivered").