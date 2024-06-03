# Spring WebFlux - Pet Clinic

This is a simple Java project using Spring WebFlux that provides a reactive web application and uses R2DBC for
relational database integration. The project secures its endpoints with JWT tokens through integration with an OAuth2
Authorization Server.

**Note: This project requires another repository to be set up and running for the OAuth2 Authorization
Server. [OAuth2 Authorization Server Repository](https://github.com/CarinaPorumb/spring-oauth2)**

---

## Features

- Reactive web application using Spring WebFlux
- R2DBC integration for reactive relational database access
- Secured endpoints using OAuth2 and JWT tokens
- Integration with a separate [OAuth2 Authorization Server](https://github.com/CarinaPorumb/spring-oauth2)

## Technologies Used

- Java 21
- Spring Boot 3.3.0
- Spring WebFlux
- Spring Security
- Spring Data R2DBC
- H2 Database
- OAuth2 Authorization Server with JWT

--- 

## Getting Started

### Prerequisites

Make sure you have the following installed on your system:

- Java 21
- Maven
- Postman or any other API testing tool

---

### Installation

- #### 1. Clone and Set Up the OAuth2 Authorization Server Project

This WebFlux project relies on a separate OAuth2 Authorization Server project for authentication and authorization.

Clone and set up the OAuth2 project first:

```bash
git clone https://github.com/CarinaPorumb/spring-oauth2
cd spring-oauth2
```

<br>

Build the project using Maven:

```bash
mvn clean install
```

<br>

You can run the application using your IDE or from the command line:

  ```bash
   mvn spring-boot:run
   ```

Once the application is running, it will be available at http://localhost:9000.

<br>

- #### 2. Clone and Set up the WebFlux Pet Clinic Project

After setting up the OAuth2 Authorization Server, clone and set up the WebFlux Pet Clinic project:

```bash 
git clone https://github.com/CarinaPorumb/pet-clinic-webflux
cd webflux-petclinic
```

<br>

Build the project using Maven:

```bash
mvn clean install
```

<br>

You can run the application using your IDE or from the command line:

  ```bash
   mvn spring-boot:run
   ```

Once the application is running, it will be available at http://localhost:8082.

---

**Ensure the OAuth2 Authorization Server is running on http://localhost:9000 before using this project.**

---