# FurnitureStore

FurnitureStore is a Spring Boot MVC application for managing products in a furniture store.
It supports CRUD operations, soft deletion, and uses PostgreSQL for persistence in production.

## Features

- Product management (create, read, update, soft delete)
- Soft delete (products are marked as deleted, not removed)
- PostgreSQL database for production, H2 for local development
- Spring MVC architecture
- Simple authentication for H2 Console
- Automatic product import on startup (optional)

## Technologies

- Java 21+
- Spring Boot
- Spring MVC
- Spring Data JPA
- PostgreSQL & H2 Database
- JUnit & Mockito (testing)
- Docker & Docker Compose

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven or Gradle
- Docker & Docker Compose (for production setup)

### Running the Application Locally (H2)

1. **Clone the repository**
    ```bash
    git clone https://github.com/Tomykulak/FurnitureStore.git
    cd furniturestore
    ```

2. **Build and run**
    ```bash
    ./mvnw spring-boot:run
    ```
   or
    ```bash
    ./gradlew bootRun
    ```

3. **Access the application**
   - Main app: [http://localhost:8080](http://localhost:8080)
   - H2 Console: [H2 Console](http://localhost:8080/h2-console)
      - **JDBC URL:** `jdbc:h2:file:./data/furnituredb;AUTO_SERVER=TRUE`
      - **Username:** `sa`
      - **Password:** *(leave blank)*
   - Swagger annotations: [Swagger docs](http://localhost:8080/swagger-ui/index.html)

### Running the Application with Docker & PostgreSQL

1. **Start Docker containers**
    ```bash
    docker-compose up --build
    ```

2. **Access the application**
   - Main app: [http://localhost:8080](http://localhost:8080)
   - PostgreSQL runs on `localhost:5432` with credentials:
   - URL: jdbc:postgresql://localhost:5432/furniture
      - **Database:** `furniture`
      - **Username:** `furniture`
      - **Password:** `furniture`
   - Swagger docs: [Swagger docs](http://localhost:8080/swagger-ui/index.html)

### Configuration

The application is configured via `application.properties` and profiles:

- **Dev (H2)**
```properties
spring.profiles.active=dev
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:file:./data/furnituredb;AUTO_SERVER=TRUE
app.import-products-on-startup=true