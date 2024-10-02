# SpeedoTransfer - Money Transfer Application

## Overview

**SpeedoTransfer** is a Spring Boot application designed for secure money transfers between accounts. The application includes user authentication with JWT tokens, account management, transaction tracking, and the ability to save favorite recipients for quicker transactions.

## Repo Insight Widget

<a href="https://next.ossinsight.io/widgets/official/compose-recent-active-contributors?repo_id=41986369&limit=30" target="_blank" style="display: block" align="center">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="https://next.ossinsight.io/widgets/official/compose-recent-active-contributors/thumbnail.png?repo_id=41986369&limit=30&image_size=auto&color_scheme=dark" width="655" height="auto">
    <img alt="Active Contributors of pingcap/tidb - Last 28 days" src="https://next.ossinsight.io/widgets/official/compose-recent-active-contributors/thumbnail.png?repo_id=41986369&limit=30&image_size=auto&color_scheme=light" width="655" height="auto">
  </picture>
</a>

## Features

- **User Registration and Login**: Secure user registration and login with JWT authentication.
- **JWT Security**: Authentication and authorization are handled via JWT tokens, ensuring secure access to protected resources.
- **Money Transfer**: Users can transfer money between accounts.
- **Favorite Recipients**: Add and manage favorite recipients for faster future transactions.
- **Account Management**: Manage user accounts securely and efficiently.
- **Transaction Management**: Track and manage transactions.
- **Validation**: Input validation is enforced using `jakarta.validation.constraints`.

## Project Structure

```bash
project-root/
│
├── src/
│   ├── main/
│   │   ├── java/com/company/
│   │   │   ├── config/           # Configuration files (JWT, Security)
│   │   │   ├── controllers/      # Controllers for handling HTTP requests
│   │   │   ├── dtos/             # Data Transfer Objects for request/response mapping
│   │   │   ├── models/           # Entity models like User, Account, Transaction
│   │   │   ├── services/         # Business logic is handled here
│   │   │   └── utils/            # Utility classes (e.g., for security context, token handling)
│   ├── test/                     # Unit tests for services and controllers
│   └── resources/
│       └── application.yml       # Configuration file
├── README.md                     # Project documentation
└── pom.xml                       # Maven dependencies
```

Models
The application has the following main models:

-User: Represents an application user with fields like name, email, password, role, and account information.

-Account: Represents a user's account with fields like account number, balance, and associated user.

-Transaction: Represents a money transfer transaction with fields such as sender account, receiver account, amount, status, and timestamp.

-FavoriteRecipient: Represents a saved recipient for faster future transactions.


Unit Testing
The project includes unit tests for core services:

-UserService: Unit tests for user registration, login, and authentication.

-AccountService: Unit tests for account management, including balance checks and updates.

-TransactionService: Unit tests for handling money transfers between accounts.

-FavoriteRecipientService: Unit tests for adding, retrieving, and managing favorite recipients.


Technologies Used :

-Java: JDK 17
-Spring Boot: Backend framework for building the application.
-Spring Security: For handling security and JWT-based authentication.
-Hibernate: For ORM and database management.
-Jakarta Validation: For validating user input.
-JUnit: For unit testing.
-Maven: Dependency management and build tool.
-PostgreSQL: Database for storing user, account, and transaction data.
-Swagger: API documentation and testing interface.

API Endpoints

Authentication
POST /api/auth/register: Register a new user.
POST /api/auth/login: Login with email and password.

Account
GET /api/accounts/{id}: Get account details by account ID.
POST /api/accounts/transfer: Transfer money from one account to another.

Favorite Recipients
POST /api/favorites: Add a favorite recipient.
GET /api/favorites: Get a list of favorite recipients.

Transactions
POST /api/transactions: Create a new transaction.
GET /api/transactions/{id}: Get transaction details by ID.

Getting Started
Prerequisites
To run this application, you need to have the following installed:

Java 17+
Maven 3.6+
PostgreSQL
Setup

Install dependencies:
mvn clean install

Configure the database settings in application.yml:
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/speedotransfer
    username: postgres
    password: 3006

Run the application:
mvn spring-boot:run

Testing
Run the unit tests using Maven:
mvn test
Contributing
Contributions are welcome! Please fork this repository and submit a pull request with your changes.






